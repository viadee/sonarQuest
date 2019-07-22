package com.viadee.sonarquest.skillTree.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.skillTree.dto.SonarRuleDTO;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.services.SonarRuleService;

@RestController
@RequestMapping("/sonarrule")
public class SonerRuleController {

	@Autowired
	private SonarRuleService sonarRuleService;
	
	
//TODO Remove
//	@GetMapping(value = "/update/")
//	public List<SonarRule> updateSonarRulesByLanguage(@RequestParam(value = "language") final String language) {
//		return sonarRuleService.update(language);
//	}
//
//	@GetMapping(value = "/all/")
//	public List<SonarRule> getAll() {
//		return sonarRuleService.findAll();
//	}
	
	@GetMapping(value="/unassignedRules")
	public List<SonarRuleDTO> getUnassignedRules() {
		return this.sonarRuleService.getUnassignedRules();
	}

}
