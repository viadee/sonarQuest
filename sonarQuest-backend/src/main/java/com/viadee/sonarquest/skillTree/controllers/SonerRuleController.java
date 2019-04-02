package com.viadee.sonarquest.skillTree.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	//hier gehts weiter
	@GetMapping(value = "/export/")
	public void createFile() throws IOException {
		Path newFilePath = Paths.get("src/main/resources/export.txt");
		Files.createFile(newFilePath);
	}

}
