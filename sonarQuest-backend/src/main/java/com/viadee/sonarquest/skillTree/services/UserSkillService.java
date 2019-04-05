package com.viadee.sonarquest.skillTree.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.skillTree.dto.UserSkillDTO;
import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skillTree.repositories.SkillTreeUserRepository;
import com.viadee.sonarquest.skillTree.repositories.SonarRuleRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillRepositroy;
import com.viadee.sonarquest.skillTree.repositories.UserSkillToSkillTreeUserRepository;
import com.viadee.sonarquest.skillTree.utils.mapper.UserSkillDtoEntityMapper;

@Service
public class UserSkillService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserSkillService.class);

	@Autowired
	private UserSkillRepositroy userSkillRepositroy;

	@Autowired
	private SonarRuleRepository sonarRuleRepository;

	@Autowired
	private SkillTreeUserRepository skillTreeUserRepository;

	@Autowired
	private UserSkillToSkillTreeUserRepository userSkillToSkillTreeUserRepository;

	@Autowired
	private UserSkillDtoEntityMapper mapper;

	private UserSkill findById(final Long id) {
		return userSkillRepositroy.findOne(id);
	}

	private int recursionCount = 0;
	private List<Map<String, Object>> resultFollowingCalculateScore = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> resultPreviousCalculateScore = new ArrayList<Map<String, Object>>();

	// TODO Eventuell nicht notwendig
	public List<UserSkillDTO> findUserSkillsFromTeam(List<String> mails) {

		List<UserSkillDTO> userSkills = new ArrayList<UserSkillDTO>();
		for (String mail : mails) {
			if (mail != null || mail != "" || !mail.equalsIgnoreCase("null")) {
				SkillTreeUser skillTreeUser = skillTreeUserRepository.findByMail(mail);
				if (skillTreeUser != null) {
					/*
					 * List<UserSkillToSkillTreeUser> learnedUserSkillsToSkillTreeUsers =
					 * skillTreeUser .getUserSkillToSkillTreeUser().stream() .filter(skillToUser ->
					 * skillToUser.getLearnedOn() != null).collect(Collectors.toList());
					 */
					for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : skillTreeUser
							.getUserSkillToSkillTreeUser()) {
						UserSkillDTO userSkillDTO = mapper.entityToDto(userSkillToSkillTreeUser.getUserSkill());
						userSkillDTO.setScore(userSkillToSkillTreeUser.getScore());
						if (userSkills.stream().filter(dto -> userSkillDTO.getId() == dto.getId()).findAny()
								.orElse(null) == null) {
							userSkills.add(userSkillDTO);

						}
					}

				}
			}
		}

		return userSkills;
	}

	public Double getScoringForRuleFromTeam(String ruleKey, List<String> mails) {
		SonarRule rule = sonarRuleRepository.findSonarRuleByKey(ruleKey);
		if (rule != null) {
			UserSkill userSkill = userSkillRepositroy.findUserSkillBySonarRule(rule);
			if (userSkill != null) {
				double teamScore = 0;
				int memeberWithSkill = 0;
				for (String mail : mails) {
					if (mail != null || mail != "" || !mail.equalsIgnoreCase("null")) {
						SkillTreeUser skillTreeUser = skillTreeUserRepository.findByMail(mail);
						if (skillTreeUser != null) {
							UserSkillToSkillTreeUser userSkillToSkillTreeUser = userSkillToSkillTreeUserRepository
									.findUserSkillToSkillTreeUserByUserSkillAndUser(userSkill, skillTreeUser);
							if (userSkillToSkillTreeUser != null) {
								if (userSkillToSkillTreeUser.getScore() != null) {
									teamScore = teamScore + userSkillToSkillTreeUser.getScore();
									memeberWithSkill++;
								}

							}
						}
					}
				}
				return teamScore / memeberWithSkill;
			}

		}
		return null;
	}

	@Transactional
	public UserSkill createUserSkill(final UserSkill userSkill) {
		LOGGER.info("Creating new userskill " + userSkill.getName());
		// SonarRule rule = new SonarRule("testname","testkey",1);
		// sonarRuleRepository.save(rule);
		// userSkill.addSonarRule(rule);

		return userSkillRepositroy.save(userSkill);
	}

	public boolean delete(final Long userId) {
		final UserSkill skill = findById(userId);
		if (skill != null) {
			userSkillRepositroy.delete(skill);
			return true;
		}

		return false;
	}

	@Transactional
	public Double calculateUserSkillScore(UserSkill userSkill, SkillTreeUser skillTreeUser) {
		double score = 0;
		double distanzFollowing = 0;
		double distanzPrevious = 0;
		if (userSkillToSkillTreeUserRepository.findUserSkillToSkillTreeUserByUserSkillAndUser(userSkill, skillTreeUser)
				.getRepeats() >= 1) {
			return score;
		}
		UserSkillToSkillTreeUser userSkillToSkillTreeUserFollwing = null;
		getDistanzFollowing(userSkill.getFollowingUserSkills(), skillTreeUser);
		for (Map<String, Object> resultFollowing : resultFollowingCalculateScore) {
			if (resultFollowing.get("ustsku") != null) {
				if ((int) resultFollowing.get("recursionCount") == 0) {
					distanzFollowing = 0;
					userSkillToSkillTreeUserFollwing = (UserSkillToSkillTreeUser) resultFollowing.get("ustsku");
					break;
				} else if (distanzFollowing == 0) {
					distanzFollowing = (int) resultFollowing.get("recursionCount");
					userSkillToSkillTreeUserFollwing = (UserSkillToSkillTreeUser) resultFollowing.get("ustsku");
				} else if (distanzFollowing > (int) resultFollowing.get("recursionCount")) {
					distanzFollowing = (int) resultFollowing.get("recursionCount");
					userSkillToSkillTreeUserFollwing = (UserSkillToSkillTreeUser) resultFollowing.get("ustsku");
				}
			}
		}

		UserSkillToSkillTreeUser userSkillToSkillTreeUserPrevious = null;
		getDistanzPrevious(userSkill.getPreviousUserSkills(), skillTreeUser);
		for (Map<String, Object> resultPrevious : resultPreviousCalculateScore) {
			if (resultPrevious.get("ustsku") != null) {
				if ((int) resultPrevious.get("recursionCount") == 0) {
					distanzPrevious = 0;
					userSkillToSkillTreeUserPrevious = (UserSkillToSkillTreeUser) resultPrevious.get("ustsku");
					break;
				} else if (distanzPrevious == 0) {
					distanzPrevious = (int) resultPrevious.get("recursionCount");
					userSkillToSkillTreeUserPrevious = (UserSkillToSkillTreeUser) resultPrevious.get("ustsku");
				} else if (distanzPrevious > (int) resultPrevious.get("recursionCount")) {
					distanzPrevious = (int) resultPrevious.get("recursionCount");
					userSkillToSkillTreeUserPrevious = (UserSkillToSkillTreeUser) resultPrevious.get("ustsku");
				}
			}
		}
		resultFollowingCalculateScore.removeAll(resultFollowingCalculateScore);
		resultPreviousCalculateScore.removeAll(resultPreviousCalculateScore);

		return calculateScore(userSkillToSkillTreeUserFollwing, userSkillToSkillTreeUserPrevious, distanzFollowing,
				distanzPrevious);
	}

	private Double calculateScore(UserSkillToSkillTreeUser userSkillToSkillTreeUserFollwing,
			UserSkillToSkillTreeUser userSkillToSkillTreeUserPrevious, double distanzFollowing,
			double distanzPrevious) {
		if (userSkillToSkillTreeUserFollwing != null && userSkillToSkillTreeUserPrevious == null) {

			return distanzFollowing / (Double.valueOf(userSkillToSkillTreeUserFollwing.getRepeats())
					/ Double.valueOf(userSkillToSkillTreeUserFollwing.getUserSkill().getRequiredRepetitions()));

		} else if (userSkillToSkillTreeUserFollwing == null && userSkillToSkillTreeUserPrevious != null) {
			return distanzPrevious / (Double.valueOf(userSkillToSkillTreeUserPrevious.getRepeats())
					/ Double.valueOf(userSkillToSkillTreeUserPrevious.getUserSkill().getRequiredRepetitions()));
		} else if (userSkillToSkillTreeUserFollwing != null && userSkillToSkillTreeUserPrevious != null) {
			if (distanzFollowing > distanzPrevious) {
				return distanzPrevious / (Double.valueOf(userSkillToSkillTreeUserPrevious.getRepeats())
						/ Double.valueOf(userSkillToSkillTreeUserPrevious.getUserSkill().getRequiredRepetitions()));
			} else if (distanzFollowing < distanzPrevious) {
				return distanzFollowing / (Double.valueOf(userSkillToSkillTreeUserFollwing.getRepeats())
						/ Double.valueOf(userSkillToSkillTreeUserFollwing.getUserSkill().getRequiredRepetitions()));
			} else {
				double weightFollowing = Double.valueOf(userSkillToSkillTreeUserFollwing.getRepeats())
						/ Double.valueOf(userSkillToSkillTreeUserFollwing.getUserSkill().getRequiredRepetitions());
				double weightPrevious = Double.valueOf(userSkillToSkillTreeUserPrevious.getRepeats())
						/ Double.valueOf(userSkillToSkillTreeUserPrevious.getUserSkill().getRequiredRepetitions());
				if (weightFollowing > weightPrevious) {
					return distanzFollowing / (Double.valueOf(userSkillToSkillTreeUserFollwing.getRepeats())
							/ Double.valueOf(userSkillToSkillTreeUserFollwing.getUserSkill().getRequiredRepetitions()));
				} else {
					return distanzPrevious / (Double.valueOf(userSkillToSkillTreeUserPrevious.getRepeats())
							/ Double.valueOf(userSkillToSkillTreeUserPrevious.getUserSkill().getRequiredRepetitions()));
				}
			}
		}
		return null;
	}

	public void getDistanzFollowing(List<UserSkill> userSkills, SkillTreeUser skillTreeUser) {
		recursionCount++;
		Map<String, Object> result;
		for (UserSkill userSkill : userSkills) {

			// Necessary due to the recursion. It may be that the first recursion count ++
			// is not triggered
			if (recursionCount == 0) {
				recursionCount++;
			}
			UserSkillToSkillTreeUser userSkillToSkillTreeUser = userSkillToSkillTreeUserRepository
					.findUserSkillToSkillTreeUserByUserSkillAndUser(userSkill, skillTreeUser);
			if (userSkillToSkillTreeUser != null) {
				if (userSkillToSkillTreeUser.getRepeats() >= 1) {
					result = new HashMap<String, Object>();
					result.put("ustsku", userSkillToSkillTreeUser);
					result.put("recursionCount", recursionCount);
					recursionCount = 0;
					resultFollowingCalculateScore.add(result);

				}
			}

			getDistanzFollowing(userSkill.getFollowingUserSkills(), skillTreeUser);

		}
		result = new HashMap<String, Object>();
		result.put("ustsku", null);
		result.put("recursionCount", 0);
		recursionCount = 0;
		resultFollowingCalculateScore.add(result);
	}

	public void getDistanzPrevious(List<UserSkill> userSkills, SkillTreeUser skillTreeUser) {
		recursionCount++;
		Map<String, Object> result;
		for (UserSkill userSkill : userSkills) {
			// Necessary due to the recursion. It may be that the first recursion count ++
			// is not triggered
			if (recursionCount == 0) {
				recursionCount++;
			}
			UserSkillToSkillTreeUser userSkillToSkillTreeUser = userSkillToSkillTreeUserRepository
					.findUserSkillToSkillTreeUserByUserSkillAndUser(userSkill, skillTreeUser);

			if (userSkillToSkillTreeUser != null) {
				if (userSkillToSkillTreeUser.getRepeats() >= 1) {
					result = new HashMap<String, Object>();
					result.put("ustsku", userSkillToSkillTreeUser);
					result.put("recursionCount", recursionCount);
					recursionCount = 0;
					resultPreviousCalculateScore.add(result);
				}
			}
			getDistanzPrevious(userSkill.getPreviousUserSkills(), skillTreeUser);

		}

		result = new HashMap<String, Object>();
		result.put("ustsku", null);
		result.put("recursionCount", 0);
		recursionCount = 0;
		resultPreviousCalculateScore.add(result);
	}

}
