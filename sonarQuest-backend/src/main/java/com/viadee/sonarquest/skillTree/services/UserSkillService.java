package com.viadee.sonarquest.skillTree.services;

import java.sql.Timestamp;
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

import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.RoleName;
import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.UserRepository;
import com.viadee.sonarquest.skillTree.dto.UserSkillDTO;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeObjectDTO;
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
	private UserSkillRepositroy userSkillRepository;

	@Autowired
	private SonarRuleRepository sonarRuleRepository;

	@Autowired
	private SkillTreeUserRepository skillTreeUserRepository;

	@Autowired
	private UserSkillToSkillTreeUserRepository userSkillToSkillTreeUserRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserSkillDtoEntityMapper mapper;

	private UserSkill findById(final Long id) {
		return userSkillRepository.findOne(id);
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
			UserSkill userSkill = userSkillRepository.findUserSkillBySonarRule(rule);
			if (userSkill != null) {
				Double teamScore = null;
				int amountDevelopersInTeam = 0;
				for (String mail : mails) {
					if (mail != null || mail != "" || !mail.equalsIgnoreCase("null")) {
						SkillTreeUser skillTreeUser = skillTreeUserRepository.findByMail(mail);
						if (skillTreeUser != null) {
							if(userRepository.findByMail(mail).getRole().getName().equals(RoleName.DEVELOPER)){
								amountDevelopersInTeam++;
								UserSkillToSkillTreeUser userSkillToSkillTreeUser = userSkillToSkillTreeUserRepository
										.findUserSkillToSkillTreeUserByUserSkillAndUser(userSkill, skillTreeUser);
								if (userSkillToSkillTreeUser != null) {
									if (userSkillToSkillTreeUser.getScore() != null) {
										if (teamScore == null) {
											teamScore = 0.0;
										}
										teamScore = teamScore + userSkillToSkillTreeUser.getScore();
										// memeberWithSkill++;
									}

								}	
							}
							
						}
					}
				}
				// return teamScore / memeberWithSkill;
				if (teamScore != null) {
					return teamScore / amountDevelopersInTeam;
				}
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

		return userSkillRepository.save(userSkill);
	}

	public boolean delete(final Long userId) {
		final UserSkill skill = findById(userId);
		if (skill != null) {
			userSkillRepository.delete(skill);
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

	@Transactional
	public UserSkill updateUserSkill(UserSkill userSkill) {
		UserSkill oldUserSkill = userSkillRepository.findOne(userSkill.getId());
		if (oldUserSkill.getRequiredRepetitions() != userSkill.getRequiredRepetitions()) {
			List<UserSkillToSkillTreeUser> userSkillToSkillTreeUsers = userSkillToSkillTreeUserRepository
					.findUserSkillToSkillTreeUsersByUserSkill(userSkill);
			for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : userSkillToSkillTreeUsers) {
				if (userSkillToSkillTreeUser.getRepeats() >= userSkill.getRequiredRepetitions()) {
					userSkillToSkillTreeUser.setLearnedOn(new Timestamp(System.currentTimeMillis()));
					userSkillToSkillTreeUserRepository.save(userSkillToSkillTreeUser);
				} else if (userSkillToSkillTreeUser.getLearnedOn() != null
						&& userSkillToSkillTreeUser.getRepeats() < userSkill.getRequiredRepetitions()) {
					userSkillToSkillTreeUser.setLearnedOn(null);
					userSkillToSkillTreeUserRepository.save(userSkillToSkillTreeUser);
				}
			}
			oldUserSkill.setRequiredRepetitions(userSkill.getRequiredRepetitions());
			userSkill = oldUserSkill;

		}
		UserSkill newUserSkill = userSkillRepository.save(userSkill);
		recalculateWholeUserSkillScore();
		LOGGER.info("UserSkill with ID: {} has been updated", userSkill.getId());
		return newUserSkill;
	}

	public void recalculateWholeUserSkillScore() {
		List<UserSkillToSkillTreeUser> userSkillToSkillTreeUsers = userSkillToSkillTreeUserRepository.findAll();
		for (UserSkillToSkillTreeUser skillToSkillTreeUser : userSkillToSkillTreeUsers) {
			skillToSkillTreeUser.setScore(calculateUserSkillScore(skillToSkillTreeUser.getUserSkill(),
					skillToSkillTreeUser.getSkillTreeUser()));
			userSkillToSkillTreeUserRepository.save(skillToSkillTreeUser);
		}
	}

	@Transactional
	public SkillTreeObjectDTO learnSkill(String mail, String key) {
		SkillTreeUser user = skillTreeUserRepository.findByMail(mail);
		SkillTreeObjectDTO skillTreeObjectDTO = new SkillTreeObjectDTO();
		if (user == null) {
			LOGGER.info("User with mail: {}  does not exist yet - creating it...", mail);
			user = this.createSkillTreeUser(mail);
		}

		outter: for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : user.getUserSkillToSkillTreeUser()) {
			for (SonarRule rule : userSkillToSkillTreeUser.getUserSkill().getSonarRules()) {
				if (rule.getKey().equals(key)) {
					userSkillToSkillTreeUser.setRepeats(userSkillToSkillTreeUser.getRepeats() + 1);
					if (userSkillToSkillTreeUser.getRepeats() >= userSkillToSkillTreeUser.getUserSkill()
							.getRequiredRepetitions() && userSkillToSkillTreeUser.getLearnedOn() == null) {
						userSkillToSkillTreeUser.setLearnedOn(new Timestamp(System.currentTimeMillis()));
						LOGGER.info("User with mail: {} has learned UserSkill '{}'", mail,
								userSkillToSkillTreeUser.getUserSkill().getName());
					}
					skillTreeObjectDTO.setId(String.valueOf(userSkillToSkillTreeUser.getUserSkill().getId()));
					skillTreeObjectDTO.setLabel(userSkillToSkillTreeUser.getUserSkill().getName());
					skillTreeObjectDTO.setRepeats(userSkillToSkillTreeUser.getRepeats());
					skillTreeObjectDTO
							.setRequiredRepetitions(userSkillToSkillTreeUser.getUserSkill().getRequiredRepetitions());
					break outter;
				}
			}
		}
		updateSkillTreeScoring(user);
		skillTreeUserRepository.save(user);
		return skillTreeObjectDTO;
	}

	@Transactional
	public SkillTreeUser createSkillTreeUser(String mail) {
		SkillTreeUser user = skillTreeUserRepository.save(new SkillTreeUser(mail));
		List<UserSkill> userSkills = userSkillRepository.findAll();
		for (UserSkill userSkill : userSkills) {
			UserSkillToSkillTreeUser userSkillToSkillTreeUser = userSkillToSkillTreeUserRepository
					.save(new UserSkillToSkillTreeUser(null, 0, userSkill, user, null));
			user.addUserSkillToSkillTreeUser(userSkillToSkillTreeUser);
		}
		skillTreeUserRepository.save(user);
		return user;
	}

	@Transactional
	private void updateSkillTreeScoring(SkillTreeUser skillTreeUser) {
		for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : skillTreeUser.getUserSkillToSkillTreeUser()) {
			userSkillToSkillTreeUser
					.setScore(calculateUserSkillScore(userSkillToSkillTreeUser.getUserSkill(), skillTreeUser));
			userSkillToSkillTreeUserRepository.save(userSkillToSkillTreeUser);
		}
	}

	public void learnUserSkillFromTask(StandardTask task) {
		final Participation participation = task.getParticipation();
		if (participation != null) {
			String mail = participation.getUser().getMail();
			if (mail != null) {
				learnSkill(mail, task.getIssueRule());
			}
		} else {
			LOGGER.info("No SQUser participations found for task {}, so no skills are learned", task.getKey());
		}
	}

	// TODO löschen
	public void calculateSkillTreeByMail(String mail) {
		SkillTreeUser user = skillTreeUserRepository.findByMail(mail);
		updateSkillTreeScoring(user);
	}

}
