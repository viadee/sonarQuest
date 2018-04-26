package com.viadee.sonarQuest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.dtos.WorldDto;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.WorldRepository;

@Service
public class WorldService {

    @Autowired
    ExternalRessourceService externalRessourceService;

    @Autowired
    WorldRepository worldRepository;

    public void updateWorlds() {
        List<World> externalWorlds = externalRessourceService.generateWorldsFromSonarQubeProjects();
        externalWorlds.forEach(this::updateWorld);
    }

    private void updateWorld(World externalWorld) {
        World internalWorld = worldRepository.findByProject(externalWorld.getProject());
        if (internalWorld == null) {
            worldRepository.save(externalWorld);
        }
    }

    public void setExternalRessourceService(ExternalRessourceService externalRessourceService) {
        this.externalRessourceService = externalRessourceService;
    }

    public World getCurrentWorld(Developer d) {
        return d.getWorld();
    }

    public World createWorld(WorldDto worldDto) {
        return worldRepository.save(new World(
                worldDto.getName(),
                worldDto.getProject(),
                worldDto.getActive()));
    }

}
