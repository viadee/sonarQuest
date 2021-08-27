package com.viadee.sonarquest.controllers;

import com.viadee.sonarquest.externalressources.SonarQubeProject;
import com.viadee.sonarquest.services.ExternalRessourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/externalRessource")
public class ExternalRessourceController {

    private final ExternalRessourceService externalRessourceService;

    public ExternalRessourceController(ExternalRessourceService externalRessourceService) {
        this.externalRessourceService = externalRessourceService;
    }

    @GetMapping(value = "/project")
    public List<SonarQubeProject> getAllSonarQubeProjects() {
        return externalRessourceService.getSonarQubeProjects();
    }

}
