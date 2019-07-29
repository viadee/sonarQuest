package com.viadee.sonarquest.skilltree.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.skilltree.dto.SonarRuleDTO;
import com.viadee.sonarquest.skilltree.services.SonarRuleService;

@RestController
@RequestMapping("/sonarrule")
public class SonerRuleController {

	@Autowired
	private SonarRuleService sonarRuleService;
	
	@GetMapping(value="/unassignedRules")
	public List<SonarRuleDTO> getUnassignedRules() {
		return this.sonarRuleService.getUnassignedRules();
	}

}
