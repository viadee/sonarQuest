package com.viadee.sonarquest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.RoleName;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.services.WorldService;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeDiagramDTO;
import com.viadee.sonarquest.skillTree.services.SkillTreeService;

@RestController
@RequestMapping("/sqskilltree")
public class SQSkillTreeController {

	@Autowired
	private SkillTreeService skillTreeService;

	@Autowired
	private WorldService worldService;

	@GetMapping(value = "/fromgroup/user/")
	public SkillTreeDiagramDTO getSkillTreeForUserByGroupID(@RequestParam(value = "id") final Long id,
			@RequestParam(value = "worldID") final Long worldID) {
		World world = worldService.findById(worldID);
		List<String> mails = world.getUsers().stream().filter(user-> user.getMail()!= null && user.getRole().getName().equals(RoleName.DEVELOPER)).map(user-> user.getMail()).collect(Collectors.toList());		
		return skillTreeService.generateSkillTreeForTeamByGroupID(id, mails);

	}

}
