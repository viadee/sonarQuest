package com.viadee.sonarquest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.RoleName;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.services.WorldService;
import com.viadee.sonarquest.skilltree.dto.skillTreeDiagram.SkillTreeDiagramDTO;
import com.viadee.sonarquest.skilltree.services.SkillTreeService;

@RestController
@RequestMapping("/sqskilltree")
public class SQSkillTreeController {

	@Autowired
	private SkillTreeService skillTreeService;

	@Autowired
	private WorldService worldService;

	@GetMapping(value = "/fromgroup/team/")
	public SkillTreeDiagramDTO getSkillTreeForUserByGroupID(@RequestParam(value = "id") final Long id,
			@RequestParam(value = "worldID") final Long worldID) {
		World world = worldService.findById(worldID);
		List<String> mails = null;
		if(world != null) {
			mails = world.getUsers().stream().filter(user-> user.getMail()!= null && user.getRole().getName().equals(RoleName.DEVELOPER)).map(user-> user.getMail()).collect(Collectors.toList());
		}
		return skillTreeService.generateSkillTreeForTeamByGroupID(id, mails);

	}

}
