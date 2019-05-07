package com.viadee.sonarquest.skillTree.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.viadee.sonarquest.skillTree.dto.SonarRuleDTO;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.services.SonarRuleService;

@RestController
@RequestMapping("/sonarrule")
public class SonerRuleController {

	@Autowired
	private SonarRuleService sonarRuleService;

	@GetMapping(value = "/update/")
	public List<SonarRule> updateSonarRulesByLanguage(@RequestParam(value = "language") final String language) {
		return sonarRuleService.update(language);
	}

	@GetMapping(value = "/all/")
	public List<SonarRule> getAll() {
		return sonarRuleService.findAll();
	}
	
	@GetMapping(value="/unassignedRules")
	public List<SonarRuleDTO> getUnassignedRules() {
		return this.sonarRuleService.getUnassignedRules();
	}
	
	//TODO Remove
	@PostMapping
	public void createSonarRule() {
		this.sonarRuleService.createSonarRule("test", "test");
	}
	
	//TODO Remove
		@DeleteMapping
		public void deleteSonarRule(@RequestParam(value = "id") final Long id) {
			this.sonarRuleService.deleteSonarRule(id);
		}

}
