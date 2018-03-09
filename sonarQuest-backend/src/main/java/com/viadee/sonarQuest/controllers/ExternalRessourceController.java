package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import com.viadee.sonarQuest.services.ExternalRessourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/externalRessource")
public class ExternalRessourceController {

    @Autowired
    private ExternalRessourceService externalRessourceService;

    @RequestMapping(value = "/project",method = RequestMethod.GET)
    public List<SonarQubeProject> getAllSonarQubeProjects() {
        return this.externalRessourceService.getSonarQubeProjects();
    }







}
