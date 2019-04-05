package com.viadee.sonarquest.skillTree.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.services.ArtefactService;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeDiagramDTO;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeLinksDTO;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeObjectDTO;
import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillGroup;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skillTree.repositories.SkillTreeUserRepository;
import com.viadee.sonarquest.skillTree.repositories.SonarRuleRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillGroupRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillRepositroy;
import com.viadee.sonarquest.skillTree.repositories.UserSkillToSkillTreeUserRepository;

@Service
public class SkillTreeService {

	@Autowired
	private SkillTreeUserRepository skillTreeUserRepository;

	@Autowired
	public UserSkillGroupRepository userSkillGroupRepository;

	@Autowired
	private UserSkillRepositroy userSkillRepository;

	@Autowired
	private SonarRuleRepository sonarRuleRepository;
	
	@Autowired
	private UserSkillService userSkillService; 

	@Autowired
	private UserSkillToSkillTreeUserRepository userSkillToSkillTreeUserRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(ArtefactService.class);

	public SkillTreeDiagramDTO generateGroupSkillTree() {
		List<UserSkillGroup> userSkillGroups = userSkillGroupRepository.findAll();
		SkillTreeDiagramDTO skillTreeDiagramDTO = new SkillTreeDiagramDTO();

		for (UserSkillGroup userSkillGroup : userSkillGroups) {
			skillTreeDiagramDTO.addNode(new SkillTreeObjectDTO(String.valueOf(userSkillGroup.getId()),
					String.valueOf(userSkillGroup.getName())));
			for (UserSkillGroup followingUserSkillGroup : userSkillGroup.getFollowingUserSkillGroups()) {
				skillTreeDiagramDTO.addLine(new SkillTreeLinksDTO(String.valueOf(userSkillGroup.getId()),
						String.valueOf(followingUserSkillGroup.getId())));
			}
		}
		return skillTreeDiagramDTO;

	}
	
	@Transactional
	public SkillTreeDiagramDTO generateSkillTreeForUserByGroupID(Long id, String mail) {
		SkillTreeDiagramDTO skillTreeDiagramDTO = new SkillTreeDiagramDTO();
		if (mail == null || mail.isEmpty()) {
			List<UserSkill> userSkills = userSkillRepository.findUserSkillsByGroup(id);
			for (UserSkill userSkill : userSkills) {
				skillTreeDiagramDTO.addNode(this.buildSkillTreeObject(userSkill, 0));
				for (UserSkill followingUserSkill : userSkill.getFollowingUserSkills()) {
					skillTreeDiagramDTO.addLine(new SkillTreeLinksDTO(String.valueOf(userSkill.getId()),
							String.valueOf(followingUserSkill.getId())));
				}
			}
		} else {
			SkillTreeUser user = skillTreeUserRepository.findByMail(mail);
			if (user == null) {
				LOGGER.info("User with mail: '" + mail + "' does not exist yet - creating it...");
				user = this.createSkillTreeUser(mail);
			}
			//List<UserSkill> skillsFromUser = new ArrayList<UserSkill>();
			//List<UserSkill> userSkills = userSkillRepository.findUserSkillsByGroup(id);
			for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : user.getUserSkillToSkillTreeUser()) {
				if (userSkillToSkillTreeUser.getUserSkill().getUserSkillGroup().getId().equals(id)) {
					//skillsFromUser.add(userSkillToSkillTreeUser.getUserSkill());
					skillTreeDiagramDTO.addNode(this.buildSkillTreeObject(userSkillToSkillTreeUser.getUserSkill(),
							userSkillToSkillTreeUser.getRepeats()));
					for (UserSkill followingUserSkill : userSkillToSkillTreeUser.getUserSkill()
							.getFollowingUserSkills()) {
						skillTreeDiagramDTO.addLine(
								new SkillTreeLinksDTO(String.valueOf(userSkillToSkillTreeUser.getUserSkill().getId()),
										String.valueOf(followingUserSkill.getId())));
					}
				}
			}

//			userSkills.removeAll(skillsFromUser);
//
//			for (UserSkill userSkill : userSkills) {
//				skillTreeDiagramDTO.addNode(this.buildSkillTreeObject(userSkill, 0));
//				for (UserSkill followingUserSkill : userSkill.getFollowingUserSkills()) {
//					skillTreeDiagramDTO.addLine(new SkillTreeLinksDTO(String.valueOf(userSkill.getId()),
//							String.valueOf(followingUserSkill.getId())));
//				}
//			}

		}

		return skillTreeDiagramDTO;
	}

	@Transactional
	public SkillTreeDiagramDTO generateSkillTreeForTeamByGroupID(Long id, List<String> mails) {
		SkillTreeDiagramDTO skillTreeDiagramDTO = new SkillTreeDiagramDTO();
		//List<UserSkill> userSkills = userSkillRepository.findUserSkillsByGroup(id);
		//List<UserSkill> skillsFromUser = new ArrayList<UserSkill>();
		for (String mail : mails) {
			if (mail != null || mail != "" || !mail.equalsIgnoreCase("null")) {

				SkillTreeUser user = skillTreeUserRepository.findByMail(mail);
				if (user == null) {
					LOGGER.info("User with mail: '" + mail + "' does not exist yet - creating it...");
					user = this.createSkillTreeUser(mail);
				}

				for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : user.getUserSkillToSkillTreeUser()) {
					if (userSkillToSkillTreeUser.getUserSkill().getUserSkillGroup().getId().equals(id)) {
						//skillsFromUser.add(userSkillToSkillTreeUser.getUserSkill());
						if (skillTreeDiagramDTO
								.getNodes().stream().filter(entry -> String
										.valueOf(userSkillToSkillTreeUser.getUserSkill().getId()).equals(entry.getId()))
								.findAny().orElse(null) == null) {
							skillTreeDiagramDTO.addNode(this.buildSkillTreeObject(
									userSkillToSkillTreeUser.getUserSkill(), userSkillToSkillTreeUser.getRepeats()));
							for (UserSkill followingUserSkill : userSkillToSkillTreeUser.getUserSkill()
									.getFollowingUserSkills()) {
								skillTreeDiagramDTO.addLine(new SkillTreeLinksDTO(
										String.valueOf(userSkillToSkillTreeUser.getUserSkill().getId()),
										String.valueOf(followingUserSkill.getId())));
							}
						}

					}
				}
			}
		}
		/*
		 * userSkills.removeAll(skillsFromUser);
		 * 
		 * for (UserSkill userSkill : userSkills) {
		 * skillTreeDiagramDTO.addNode(this.buildSkillTreeObject(userSkill, 0)); for
		 * (UserSkill followingUserSkill : userSkill.getFollowingUserSkills()) {
		 * skillTreeDiagramDTO.addLine(new
		 * SkillTreeLinksDTO(String.valueOf(userSkill.getId()),
		 * String.valueOf(followingUserSkill.getId()))); } }
		 */
		return skillTreeDiagramDTO;
	}

	private SkillTreeObjectDTO buildSkillTreeObject(UserSkill userSkill, int repetas) {
		SkillTreeObjectDTO skillTreeObjectDTO = new SkillTreeObjectDTO();
		skillTreeObjectDTO.setId(String.valueOf(userSkill.getId()));
		skillTreeObjectDTO.setLabel(String.valueOf(userSkill.getName()));
		skillTreeObjectDTO.setRepeats(repetas);
		skillTreeObjectDTO.setRequiredRepetitions(userSkill.getRequiredRepetitions());
		for (SonarRule rule : userSkill.getSonarRules()) {
			skillTreeObjectDTO.addRuleKey(rule.getKey(), rule.getName());
		}
		return skillTreeObjectDTO;
	}

	@Transactional
	public SkillTreeObjectDTO learnSkill(String mail, String key) {
		SkillTreeUser user = skillTreeUserRepository.findByMail(mail);
		SkillTreeObjectDTO skillTreeObjectDTO = new SkillTreeObjectDTO();
		if (user == null) {
			LOGGER.info("User with mail: '" + mail + "' does not exist yet - creating it...");
			user = this.createSkillTreeUser(mail);
		}
		//boolean ruleNotFound = true;
		outter: for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : user.getUserSkillToSkillTreeUser()) {
			if (userSkillToSkillTreeUser.getLearnedOn() == null) {

				for (SonarRule sonarRule : userSkillToSkillTreeUser.getUserSkill().getSonarRules()) {
					if (sonarRule.getKey().equals(key)) {
						userSkillToSkillTreeUser.setRepeats(userSkillToSkillTreeUser.getRepeats() + 1);
						if (userSkillToSkillTreeUser.getRepeats() == userSkillToSkillTreeUser.getUserSkill()
								.getRequiredRepetitions()) {
							userSkillToSkillTreeUser.setLearnedOn(new Timestamp(System.currentTimeMillis()));
						}
						skillTreeObjectDTO.setId(String.valueOf(userSkillToSkillTreeUser.getUserSkill().getId()));
						skillTreeObjectDTO.setLabel(userSkillToSkillTreeUser.getUserSkill().getName());
						skillTreeObjectDTO.setRepeats(userSkillToSkillTreeUser.getRepeats());
						skillTreeObjectDTO.setRequiredRepetitions(
								userSkillToSkillTreeUser.getUserSkill().getRequiredRepetitions());
						//ruleNotFound = false;
						break outter;
					}
				}
			}
			if (userSkillToSkillTreeUser.getUserSkill().getSonarRules().stream()
					.filter(sonarRule -> key.equals(sonarRule.getKey())).findAny().orElse(null) != null) {
				skillTreeObjectDTO.setId(String.valueOf(userSkillToSkillTreeUser.getUserSkill().getId()));
				skillTreeObjectDTO.setLabel(userSkillToSkillTreeUser.getUserSkill().getName());
				skillTreeObjectDTO.setRepeats(userSkillToSkillTreeUser.getRepeats());
				skillTreeObjectDTO
						.setRequiredRepetitions(userSkillToSkillTreeUser.getUserSkill().getRequiredRepetitions());
				//ruleNotFound = false;
				break outter;
			}
		}
		/*#############
		 * Wahrscheinlich nicht mehr notwendig, da alle Einträge erstellt werden, sobald ein neuer User erstellt wird
		 * #############
		 * if (ruleNotFound) { UserSkillToSkillTreeUser newUserSkillToSkillTreeUser =
		 * new UserSkillToSkillTreeUser(); newUserSkillToSkillTreeUser.setRepeats(1);
		 * newUserSkillToSkillTreeUser.setSkillTreeUser(user);
		 * newUserSkillToSkillTreeUser.setUserSkill(sonarRuleRepository.
		 * findSonarRuleByKey(key).getUserSkill());
		 * userSkillToSkillTreeUserRepository.save(newUserSkillToSkillTreeUser);
		 * user.addUserSkillToSkillTreeUser(newUserSkillToSkillTreeUser);
		 * 
		 * skillTreeObjectDTO.setId(String.valueOf(newUserSkillToSkillTreeUser.
		 * getUserSkill().getId()));
		 * skillTreeObjectDTO.setLabel(newUserSkillToSkillTreeUser.getUserSkill().
		 * getName());
		 * skillTreeObjectDTO.setRepeats(newUserSkillToSkillTreeUser.getRepeats());
		 * skillTreeObjectDTO
		 * .setRequiredRepetitions(newUserSkillToSkillTreeUser.getUserSkill().
		 * getRequiredRepetitions()); }
		 */
		updateSkillTreeScoring(user);
		skillTreeUserRepository.save(user);
		return skillTreeObjectDTO;
	}
	
	@Transactional
	public SkillTreeUser createSkillTreeUser(String mail) {
		SkillTreeUser user = skillTreeUserRepository.save(new SkillTreeUser(mail));
		List<UserSkill> userSkills = userSkillRepository.findAll();
		for(UserSkill userSkill :userSkills) {
			UserSkillToSkillTreeUser userSkillToSkillTreeUser = userSkillToSkillTreeUserRepository.save(new UserSkillToSkillTreeUser(null, 0, userSkill, user,null));
			user.addUserSkillToSkillTreeUser(userSkillToSkillTreeUser);
		}
		skillTreeUserRepository.save(user);
		return user;
	}
	
	@Transactional
	private void updateSkillTreeScoring(SkillTreeUser skillTreeUser) {
		for(UserSkillToSkillTreeUser userSkillToSkillTreeUser :skillTreeUser.getUserSkillToSkillTreeUser()) {
			userSkillToSkillTreeUser.setScore(userSkillService.calculateUserSkillScore(userSkillToSkillTreeUser.getUserSkill(),skillTreeUser));
			userSkillToSkillTreeUserRepository.save(userSkillToSkillTreeUser);
		}
	}
	
	//TODO löschen
	public void calculateSkillTreeByMail(String mail) {
		SkillTreeUser user = skillTreeUserRepository.findByMail(mail);
		updateSkillTreeScoring(user);
	}
	
}
