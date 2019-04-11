package com.viadee.sonarquest.skillTree.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeDiagramDTO;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeLinksDTO;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeObjectDTO;
import com.viadee.sonarquest.skillTree.entities.UserSkillGroup;
import com.viadee.sonarquest.skillTree.repositories.UserSkillGroupRepository;

@RestController
@RequestMapping("/userskillgroup")
public class UserSkillGroupController {

	@Autowired
	public UserSkillGroupRepository userSkillGroupRepository;

	@GetMapping(value = "/tree/")
	public SkillTreeDiagramDTO getGroupSkillTree() {
		List<UserSkillGroup> userSkillGroups = userSkillGroupRepository.findAll();
		SkillTreeDiagramDTO skillTreeDiagramDTO = new SkillTreeDiagramDTO();

		for (UserSkillGroup userSkillGroup : userSkillGroups) {
			skillTreeDiagramDTO.addNode(new SkillTreeObjectDTO(String.valueOf(userSkillGroup.getId()), String.valueOf(userSkillGroup.getName())));
			for (UserSkillGroup followingUserSkillGroup : userSkillGroup.getFollowingUserSkillGroups()) {
				skillTreeDiagramDTO
						.addLine(new SkillTreeLinksDTO(String.valueOf(userSkillGroup.getId()), String.valueOf(followingUserSkillGroup.getId())));
			}
		}
		return skillTreeDiagramDTO;

	}

}
