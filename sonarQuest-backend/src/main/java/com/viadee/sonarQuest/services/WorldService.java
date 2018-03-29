package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.constants.AdventureStates;
import com.viadee.sonarQuest.dtos.AdventureDto;
import com.viadee.sonarQuest.dtos.DeveloperDto;
import com.viadee.sonarQuest.dtos.WorldDto;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.WorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    }

    public void setExternalRessourceService(ExternalRessourceService externalRessourceService) {
        this.externalRessourceService = externalRessourceService;
    }

	public World getCurrentWorld(Developer d) {
		return d.getWorld();
	}
	
	
    
    public World createWorld(WorldDto worldDto) {
    	return this.worldRepository.save(new World(
    			worldDto.getName(), 
    			worldDto.getProject(), 
    			worldDto.getActive()
    			));
	}
	
}
