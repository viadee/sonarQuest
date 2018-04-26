package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.dtos.WorldDto;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.viadee.sonarQuest.dtos.WorldDto.toWorldDto;

@RestController
@RequestMapping("/world")
public class WorldController {

    @Autowired
    private WorldRepository worldRepository;
    
    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private WorldService worldService;

    
    @RequestMapping(method = RequestMethod.GET)
    public List<WorldDto> getAllWorlds() {
        return this.worldRepository.findAll().stream().map(WorldDto::toWorldDto).collect(Collectors.toList());
    }
    

    @CrossOrigin
    @RequestMapping(value = "/developer/{developer_id}", method = RequestMethod.GET)
    public WorldDto getCurrentWorld(@PathVariable(value = "developer_id") Long developer_id) {
    	Developer d = this.developerRepository.findById(developer_id);
    	World w = this.worldService.getCurrentWorld(d);
    	return toWorldDto(w);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WorldDto getWorldById(@PathVariable(value = "id") Long id) {
        World world = this.worldRepository.findOne(id);
        return toWorldDto(world);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public WorldDto updateWorld(@PathVariable(value = "id") Long id, @RequestBody WorldDto worldDto) {
        World world = this.worldRepository.findOne(id);
        if (world != null) {
            world.setName(worldDto.getName());
            world.setActive(worldDto.getActive());
            world = this.worldRepository.save(world);
        }
        return toWorldDto(world);
    }

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public List<WorldDto> generateWorlds() {
        worldService.updateWorlds();
        List<World> worlds= worldRepository.findAll();
        return worlds.stream().map(WorldDto::toWorldDto).collect(Collectors.toList());

    }


}

