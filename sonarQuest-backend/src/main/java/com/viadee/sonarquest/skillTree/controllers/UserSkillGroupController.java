package com.viadee.sonarquest.skillTree.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.skillTree.entities.UserSkillGroup;
import com.viadee.sonarquest.skillTree.repositories.UserSkillGroupRepository;

@RestController
@RequestMapping("/userskillgroup")
public class UserSkillGroupController {

	@Autowired
	public UserSkillGroupRepository userSkillGroupRepository;

	@GetMapping(value = "/roots/")
	public List<UserSkillGroup> getAllRootUserSkillGroups() {
		return userSkillGroupRepository.findAllRootUserSkillGroups(true);
	}

}
