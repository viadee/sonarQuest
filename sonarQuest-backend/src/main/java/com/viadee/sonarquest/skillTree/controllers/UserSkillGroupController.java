package com.viadee.sonarquest.skillTree.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.skillTree.repositories.UserSkillGroupRepository;

@RestController
@RequestMapping("/userskillgroup")
public class UserSkillGroupController {

	@Autowired
	public UserSkillGroupRepository userSkillGroupRepository;

	//TODO Ueberfluessig
	/*
	 * @GetMapping(value = "/tree/") public SkillTreeDiagramDTO getGroupSkillTree()
	 * { List<UserSkillGroup> userSkillGroups = userSkillGroupRepository.findAll();
	 * SkillTreeDiagramDTO skillTreeDiagramDTO = new SkillTreeDiagramDTO();
	 * 
	 * for (UserSkillGroup userSkillGroup : userSkillGroups) {
	 * skillTreeDiagramDTO.addNode(new
	 * SkillTreeObjectDTO(String.valueOf(userSkillGroup.getId()),
	 * String.valueOf(userSkillGroup.getName()))); for (UserSkillGroup
	 * followingUserSkillGroup : userSkillGroup.getFollowingUserSkillGroups()) {
	 * skillTreeDiagramDTO .addLine(new
	 * SkillTreeLinksDTO(String.valueOf(userSkillGroup.getId()),
	 * String.valueOf(followingUserSkillGroup.getId()))); } } return
	 * skillTreeDiagramDTO;
	 * 
	 * }
	 */

}
