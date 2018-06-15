package com.viadee.sonarQuest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.WorldRepository;

@Service
public class WorldService {

    @Autowired
    private ExternalRessourceService externalRessourceService;

    @Autowired
    private WorldRepository worldRepository;

    public List<World> findAll() {
        return worldRepository.findAll();
    }

    public void updateWorlds() {
        final List<World> externalWorlds = externalRessourceService.generateWorldsFromSonarQubeProjects();
        externalWorlds.forEach(this::updateWorld);
    }

    private void updateWorld(final World externalWorld) {
        final World internalWorld = worldRepository.findByProject(externalWorld.getProject());
        if (internalWorld == null) {
            worldRepository.save(externalWorld);
        }
    }

    public void setExternalRessourceService(final ExternalRessourceService externalRessourceService) {
        this.externalRessourceService = externalRessourceService;
    }

    public World findById(final Long id) {
        return worldRepository.findById(id);
    }

}
