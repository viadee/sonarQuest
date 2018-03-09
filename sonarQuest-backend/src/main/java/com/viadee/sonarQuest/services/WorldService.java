package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.WorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorldService {

    @Autowired
    ExternalRessourceService externalRessourceService;

    @Autowired
    WorldRepository worldRepository;

    public void updateWorlds(){
        List<World> externalWorlds =externalRessourceService.generateWorldsFromSonarQubeProjects();
        externalWorlds.forEach(world -> updateWorld(world));
    }

    private void updateWorld(World externalWorld) {
       World internalWorld= worldRepository.findByProject(externalWorld.getProject());
       if(internalWorld==null){
           worldRepository.save(externalWorld);
       }
       return;
    }

    public void setExternalRessourceService(ExternalRessourceService externalRessourceService) {
        this.externalRessourceService = externalRessourceService;
    }
}
