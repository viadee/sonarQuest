package com.viadee.sonarquest.skillTree.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.services.ArtefactService;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeDiagramDTO;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeLinksDTO;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeObjectDTO;
import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillGroup;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skillTree.repositories.SkillTreeUserRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillGroupRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillRepositroy;

@Service
public class SkillTreeService {

	@Autowired
	private SkillTreeUserRepository skillTreeUserRepository;

	@Autowired
	public UserSkillGroupRepository userSkillGroupRepository;

	@Autowired
	private UserSkillRepositroy userSkillRepository;

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
				LOGGER.info("User with mail: '" + mail + "' does not exist!");
			} else {
				List<UserSkill> skillsFromUser = new ArrayList<UserSkill>();
				List<UserSkill> userSkills = userSkillRepository.findUserSkillsByGroup(id);
				for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : user.getUserSkillToSkillTreeUser()) {
					if (userSkillToSkillTreeUser.getUserSkill().getUserSkillGroup().getId().equals(id)) {
						skillsFromUser.add(userSkillToSkillTreeUser.getUserSkill());
						skillTreeDiagramDTO.addNode(this.buildSkillTreeObject(userSkillToSkillTreeUser.getUserSkill(),
								userSkillToSkillTreeUser.getRepeats()));
						for (UserSkill followingUserSkill : userSkillToSkillTreeUser.getUserSkill()
								.getFollowingUserSkills()) {
							skillTreeDiagramDTO.addLine(new SkillTreeLinksDTO(
									String.valueOf(userSkillToSkillTreeUser.getUserSkill().getId()),
									String.valueOf(followingUserSkill.getId())));
						}
					}
				}

				userSkills.removeAll(skillsFromUser);

				for (UserSkill userSkill : userSkills) {
					skillTreeDiagramDTO.addNode(this.buildSkillTreeObject(userSkill, 0));
					for (UserSkill followingUserSkill : userSkill.getFollowingUserSkills()) {
						skillTreeDiagramDTO.addLine(new SkillTreeLinksDTO(String.valueOf(userSkill.getId()),
								String.valueOf(followingUserSkill.getId())));
					}
				}
			}
		}

		return skillTreeDiagramDTO;
	}

	private SkillTreeObjectDTO buildSkillTreeObject(UserSkill userSkill, int repetas) {
		SkillTreeObjectDTO skillTreeObjectDTO = new SkillTreeObjectDTO();
		skillTreeObjectDTO.setId(String.valueOf(userSkill.getId()));
		skillTreeObjectDTO.setLabel(String.valueOf(userSkill.getName()));
		skillTreeObjectDTO.setRepeats(repetas);
		skillTreeObjectDTO.setRequiredRepetitions(userSkill.getRequiredRepetitions());
		return skillTreeObjectDTO;
	}

}