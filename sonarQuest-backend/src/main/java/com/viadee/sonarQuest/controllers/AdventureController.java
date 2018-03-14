package com.viadee.sonarQuest.controllers;


import com.viadee.sonarQuest.constants.AdventureStates;
import com.viadee.sonarQuest.dtos.AdventureDto;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.repositories.AdventureRepository;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.AdventureService;
import com.viadee.sonarQuest.services.GratificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.viadee.sonarQuest.dtos.AdventureDto.toAdventureDto;

@RestController
@RequestMapping("/adventure")
public class AdventureController {

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private GratificationService gratificationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<AdventureDto> getAllAdventures(){
        return this.adventureRepository.findAll().stream().map(adventure -> toAdventureDto(adventure)).collect(Collectors.toList());
    }
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AdventureDto getAdventureById(@PathVariable(value = "id") Long id) {
        Adventure adventure = this.adventureRepository.findOne(id);
        return toAdventureDto(adventure);
    }

    @RequestMapping(value = "/{developer_id}/{world_id}", method = RequestMethod.GET)
    public List<List<AdventureDto>> getAdventureByDeveloperAndWorld(@PathVariable(value = "developer_id") Long developer_id, @PathVariable(value = "world_id") Long world_id) {
    	List<List<Adventure>> adventures = this.adventureService.getAllAdventuresForWorldAndDeveloper(worldRepository.findOne(world_id), developerRepository.findOne(developer_id));
        return AdventureDto.toAdventuresDto(adventures);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Adventure createAdventure(@RequestBody AdventureDto adventureDto) {
        return this.adventureRepository.save(new Adventure(adventureDto.getTitle(),adventureDto.getStory(), AdventureStates.OPEN,adventureDto.getGold(),adventureDto.getXp()));
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Adventure updateAdventure(@PathVariable(value = "id") Long id, @RequestBody AdventureDto adventureDto) {
        Adventure adventure = this.adventureRepository.findOne(id);
        if (adventure != null) {
            adventure.setTitle(adventureDto.getTitle());
            adventure.setGold(adventureDto.getGold());
            adventure.setXp(adventureDto.getXp());
            adventure.setStory(adventureDto.getStory());
            adventure = this.adventureRepository.save(adventure);
        }
        return adventure;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAdventure(@PathVariable(value = "id") Long id) {
        Adventure adventure = this.adventureRepository.findOne(id);
        if (adventure != null) {
            this.adventureRepository.delete(adventure);
        }
    }

    @RequestMapping(value = "/{adventureId}/solveAdventure/",method = RequestMethod.PUT)
    public void solveAdventure(@PathVariable(value = "adventureId") Long adventureId) {
        Adventure adventure = this.adventureRepository.findOne(adventureId);
        if (adventure != null) {
            adventure.setStatus(AdventureStates.SOLVED);
            adventureRepository.save(adventure);
            gratificationService.rewardDevelopersForSolvingAdventure(adventure);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/{adventureId}/addQuest/{questId}",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AdventureDto addQuest(@PathVariable(value = "adventureId") Long adventureId, @PathVariable(value = "questId") Long questId) {
        Adventure adventure = this.adventureRepository.findOne(adventureId);
        if(adventure != null){
            Quest quest = this.questRepository.findOne(questId);
            quest.setAdventure(adventure);
            this.questRepository.save(quest);
            if(adventure.getWorld() == null){
                adventure.setWorld(quest.getWorld());
                this.adventureRepository.save(adventure);
            }
            adventure = this.adventureRepository.findOne(adventureId);
        }
        return toAdventureDto(adventure);
    }

    @RequestMapping(value = "/{adventureId}/addDeveloper/{developerId}",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AdventureDto addDeveloper(@PathVariable(value = "adventureId") Long adventureId, @PathVariable(value = "developerId") Long developerId) {
        Adventure adventure = adventureService.addDeveloperToAdventure(adventureId, developerId);
    			
        return toAdventureDto(adventure);
    }

    

    @RequestMapping(value = "/{adventureId}/addDeveloperAndGetFullList/{developerId}",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public List<List<AdventureDto>> addDeveloperAndGetFullList(@PathVariable(value = "adventureId") Long adventureId, @PathVariable(value = "developerId") Long developerId) {
        Adventure adventure = adventureService.addDeveloperToAdventure(adventureId, developerId);
    	List<List<Adventure>> adventures = this.adventureService.getAllAdventuresForWorldAndDeveloper(worldRepository.findOne(adventure.getWorld().getId()), developerRepository.findOne(developerId));
        
        return AdventureDto.toAdventuresDto(adventures);
    }

    
    
    /**
     *     
     * @param adventureId The id of the adventure
     * @param developerId The id of the developer to remove
     * @return Gives the adventure where the Developer was removed
     */
    @RequestMapping(value = "/{adventureId}/deleteDeveloper/{developerId}",method = RequestMethod.DELETE)
    public AdventureDto deleteDeveloper(@PathVariable(value = "adventureId") Long adventureId, @PathVariable(value = "developerId") Long developerId) {
    	Adventure adventure = adventureService.removeDeveloperFromAdventure(adventureId, developerId);
        
        return toAdventureDto(adventure);
    }
    
    /**
     *     
     * @param adventureId The id of the adventure
     * @param developerId The id of the developer to remove
     * @return Gives all adventures for Developer and World
     */
    @RequestMapping(value = "/{adventureId}/deleteDeveloperAndGetFullList/{developerId}", method = RequestMethod.POST)
    public List<List<AdventureDto>> deleteDeveloperAndGetFullList(@PathVariable(value = "adventureId") Long adventureId, @PathVariable(value = "developerId") Long developerId) {
    	Adventure adventure = adventureService.removeDeveloperFromAdventure(adventureId, developerId);
        List<List<Adventure>> adventures = this.adventureService.getAllAdventuresForWorldAndDeveloper(worldRepository.findOne(adventure.getWorld().getId()), developerRepository.findOne(developerId));
        
        return AdventureDto.toAdventuresDto(adventures);
    }
    

    
    
}
