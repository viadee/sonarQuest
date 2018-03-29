package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.dtos.DeveloperDto;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.DeveloperService;
import com.viadee.sonarQuest.services.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/developer")
public class DeveloperController {

    private DeveloperRepository developerRepository;
    
    private WorldRepository worldRepository;

    private LevelService levelService;
    
    private DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperRepository developerRepository, LevelService levelService, DeveloperService developerService, WorldRepository worldRepository) {
        this.developerRepository = developerRepository;
        this.levelService = levelService;
        this.developerService = developerService;
        this.worldRepository = worldRepository;
        
    }

    /**
     * Get All Developers
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<DeveloperDto> getAllDevelopers() {
        return this.developerService.findActiveDevelopers().stream().map(developer -> toDeveloperDto(developer)).collect(Collectors.toList());
    }

    /**
     * Get a Developer by Id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeveloperDto getDeveloperByID(@PathVariable(value = "id") Long id) {
        Developer developer = this.developerRepository.findById(id);
        DeveloperDto developerDto = null;
        if (developer != null) {
            developerDto = this.toDeveloperDto(developer);
        }
        return developerDto;
    }


    /**
     * Creates a Developer from a DTO
     *
     * @param developerDto
     * @return
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public DeveloperDto createDeveloper(@RequestBody DeveloperDto developerDto) {
    	return this.toDeveloperDto(this.developerService.createDeveloper(developerDto));
    }
    
    
    
    /**
     * 
     * @param world_id
     * @param developer_id
     * @return DeveloperDto
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}/updateWorld/{world_id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public DeveloperDto updateWorld(@PathVariable(value = "id") Long developer_id, @PathVariable(value = "world_id") Long  world_id) {
    	World 		w = this.worldRepository.findById(world_id);
    	Developer 	d = this.developerRepository.findById(developer_id);
    	
    	d = this.developerService.setWorld(d,w);

        return this.toDeveloperDto(d);
    }


    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeveloperDto updateDeveloper(@PathVariable(value = "id") Long id, @RequestBody DeveloperDto developerDto) {
        Developer developer = developerRepository.findById(id);
        if (developer != null) {
            developer.setGold((developerDto.getGold()));
            developer.setXp(developerDto.getXp());
            developer.setLevel(this.levelService.getLevelByDeveloperXp(developer.getXp()));
            developer.setPicture(developerDto.getPicture());
            developer.setAboutMe(developerDto.getAboutMe());
            developer.setAvatarClass(developerDto.getAvatarClass());
            developer.setAvatarRace(developerDto.getAvatarRace());
            developer.setArtefacts(developerDto.getArtefacts());
            developer = this.developerRepository.save(developer);
        }
        return this.toDeveloperDto(developer);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteDeveloper(@PathVariable(value = "id") Long id) {
    	Developer developer = developerRepository.findById(id);	
    	developerService.deleteDeveloper(developer);    	      
    }


    /**
     * Convert Developer into DeveloperDTO
     *
     * @param developer
     * @return
     */
    private DeveloperDto toDeveloperDto(Developer developer) {
        DeveloperDto developerDto = null;
        if(developer != null){
            developerDto = new DeveloperDto(developer.getId(), developer.getUsername(), developer.getGold(), developer.getXp(), developer.getLevel(),developer.getPicture(),developer.getAboutMe(), developer.getAvatarClass(),developer.getAvatarRace(),developer.getArtefacts(),developer.getAdventures(),developer.getParticipations(),developer.getWorld());
        }
        return developerDto;
    }
}
