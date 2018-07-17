package com.viadee.sonarQuest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.World;
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

    @RequestMapping(value = "/project",method = RequestMethod.GET)
    public List<SonarQubeProject> getAllSonarQubeProjects() {
        return this.externalRessourceService.getSonarQubeProjects();
    }

    @RequestMapping(value = "/updateScores/{worldId}", method = RequestMethod.POST)
    public void updateScores(@PathVariable("worldId") final Long worldId) {
        final World world = worldRepository.findOne(worldId);
        if (world != null) {
            standardTaskService.updateAllScores(world, externalRessourceService.getStandardTaskScores(world));
        }
    }
}
