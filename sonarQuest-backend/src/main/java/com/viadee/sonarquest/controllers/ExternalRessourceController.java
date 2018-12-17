package com.viadee.sonarquest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.externalressources.SonarQubeProject;
import com.viadee.sonarquest.services.ExternalRessourceService;

@RestController
@RequestMapping("/externalRessource")
public class ExternalRessourceController {

    @Autowired
    private ExternalRessourceService externalRessourceService;

    @GetMapping(value = "/project")
    public List<SonarQubeProject> getAllSonarQubeProjects() {
        return this.externalRessourceService.getSonarQubeProjects();
    }







}
