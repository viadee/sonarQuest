package com.viadee.sonarquest.skilltree.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.skilltree.dto.skillTreeDiagram.SkillTreeDiagramDTO;
import com.viadee.sonarquest.skilltree.services.SkillTreeService;

@RestController
@RequestMapping("/skilltree")
public class SkillTreeController {

	@Autowired
	private SkillTreeService skillTreeService;

	@GetMapping(value = "/fromgroup/user/")
	public SkillTreeDiagramDTO getSkillTreeForUserByGroupID(@RequestParam(value = "id") final Long id,
			@RequestParam(value = "mail", required = false) String mail) {
		mail = mail.replaceAll("[\n|\r|\t]", "");
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
