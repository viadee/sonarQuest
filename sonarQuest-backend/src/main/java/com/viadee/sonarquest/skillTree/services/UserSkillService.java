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
					for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : skillTreeUser .getUserSkillToSkillTreeUser()) {
						UserSkillDTO userSkillDTO = mapper.entityToDto(userSkillToSkillTreeUser.getUserSkill());
						userSkillDTO.setScore(userSkillToSkillTreeUser.getScore());
						if(userSkills.stream().filter(dto -> userSkillDTO.getId() == dto.getId()).findAny().orElse(null) == null) {
							userSkills.add(userSkillDTO);

						}
					}
					
				}
			}
		}

		return userSkills;
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
				.getRepeats() == userSkill.getRequiredRepetitions()) {
			return score;
		}
		UserSkillToSkillTreeUser userSkillToSkillTreeUserFollwing = getDistanzFollowing(
				userSkill.getFollowingUserSkills(), skillTreeUser);
		distanzFollowing = recursionCount;
		recursionCount = 0;
		UserSkillToSkillTreeUser userSkillToSkillTreeUserPrevious = getDistanzFollowing(
				userSkill.getPreviousUserSkills(), skillTreeUser);
		distanzPrevious = recursionCount;
		recursionCount = 0;

		if (distanzFollowing > distanzPrevious) {
			if (userSkillToSkillTreeUserFollwing == null) {
				return null;
			} else {
				return distanzFollowing / (Double.valueOf(userSkillToSkillTreeUserFollwing.getRepeats())
						/ Double.valueOf(userSkillToSkillTreeUserFollwing.getUserSkill().getRequiredRepetitions()));
			}

		} else {
			if (userSkillToSkillTreeUserPrevious == null) {
				return null;
			} else {
				return distanzPrevious / (Double.valueOf(userSkillToSkillTreeUserPrevious.getRepeats())
						/ Double.valueOf(userSkillToSkillTreeUserPrevious.getUserSkill().getRequiredRepetitions()));
			}

		}
	}

	public UserSkillToSkillTreeUser getDistanzFollowing(List<UserSkill> userSkills, SkillTreeUser skillTreeUser) {
		recursionCount++;
		for (UserSkill userSkill : userSkills) {
			UserSkillToSkillTreeUser userSkillToSkillTreeUser = userSkillToSkillTreeUserRepository
					.findUserSkillToSkillTreeUserByUserSkillAndUser(userSkill, skillTreeUser);
			if (userSkillToSkillTreeUser != null) {
				if (userSkillToSkillTreeUser.getRepeats() >= 1) {
					return userSkillToSkillTreeUser;
				}
			}

			return getDistanzFollowing(userSkill.getFollowingUserSkills(), skillTreeUser);

		}

		return null;
	}

	public UserSkillToSkillTreeUser getDistanzPrevious(List<UserSkill> userSkills, SkillTreeUser skillTreeUser) {
		recursionCount++;
		for (UserSkill userSkill : userSkills) {
			UserSkillToSkillTreeUser userSkillToSkillTreeUser = userSkillToSkillTreeUserRepository
					.findUserSkillToSkillTreeUserByUserSkillAndUser(userSkill, skillTreeUser);
			if (userSkillToSkillTreeUser != null) {
				if (userSkillToSkillTreeUser.getRepeats() >= 1) {
					return userSkillToSkillTreeUser;
				}
			}
			return getDistanzFollowing(userSkill.getPreviousUserSkills(), skillTreeUser);

		}

		return null;
	}

}
