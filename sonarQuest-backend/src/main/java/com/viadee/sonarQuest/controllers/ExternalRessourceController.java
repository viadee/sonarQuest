package com.viadee.sonarQuest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.ExternalRessourceService;
import com.viadee.sonarQuest.services.StandardTaskService;

@RestController
@RequestMapping("/externalRessource")
public class ExternalRessourceController {

    @Autowired
    private ExternalRessourceService externalRessourceService;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private StandardTaskService standardTaskService;

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public List<SonarQubeProject> getAllSonarQubeProjects() {
        return externalRessourceService.getSonarQubeProjects();
    }

    @PreAuthorize("hasAuthority('ACTIVE_WORLD_ACCESS')")
    @RequestMapping(value = "/updateScores/{worldId}", method = RequestMethod.POST)
    public ResponseEntity<Void> updateScores(@PathVariable("worldId") final Long worldId) {
        try {
            standardTaskService.updateAllScores(worldRepository.findOne(worldId), externalRessourceService.getStandardTaskScores(worldRepository.findOne(worldId)));
            return ResponseEntity.ok().build();
        } catch (RestClientException e) {
            if (e.getMessage().contains("429"))
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            throw e;
        }
    }
}
