package com.viadee.sonarquest.skillTree.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeDiagramDTO;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeLinksDTO;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeObjectDTO;
import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillGroup;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skillTree.repositories.SkillTreeUserRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillGroupRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillRepositroy;
import com.viadee.sonarquest.skillTree.services.SkillTreeService;

@RestController
@RequestMapping("/skilltree")
public class SkillTreeController {

	@Autowired
	private SkillTreeService skillTreeService;

	@GetMapping(value = "/fromgroup/user/")
	public SkillTreeDiagramDTO getSkillTreeForUserByGroupID(@RequestParam(value = "id") final Long id,
			@RequestParam(value = "mail", required = false) final String mail) {

		return skillTreeService.generateSkillTreeForUserByGroupID(id, mail);

	}

	@GetMapping(value = "/fromgroup/team/")
	public SkillTreeDiagramDTO getSkillTreeforTeamByGroupID(@RequestParam(value = "id") final Long id,
			@RequestParam(value = "mails") List<String> mails) {
		return skillTreeService.generateSkillTreeForTeamByGroupID(id, mails);
	}

	@GetMapping(value = "/overview/")
	public SkillTreeDiagramDTO getGroupSkillTree() {
		return skillTreeService.generateGroupSkillTree();
	}
	
	
}
