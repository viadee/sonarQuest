package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.constants.AdventureStates;
import com.viadee.sonarQuest.constants.QuestStates;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.AdventureRepository;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;
    
    @Autowired
    private DeveloperRepository developerRepository; 

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private GratificationService gratificationService;

    public void updateAdventures(){
        List<Adventure> adventures = adventureRepository.findAll();
        adventures.forEach(adventure -> updateAdventure(adventure));
    }

    public void updateAdventure(Adventure adventure){
        if(adventure!= null){
            List<Quest> quests = adventure.getQuests();
            List<Quest> solvedQuests = questRepository.findByAdventureAndStatus(adventure, QuestStates.SOLVED);
            if (quests.size() == solvedQuests.size()){
                gratificationService.rewardDevelopersForSolvingAdventure(adventure);
                adventure.setStatus(AdventureStates.SOLVED);
                adventureRepository.save(adventure);
            }
        }
    }
    
	public List<List<Adventure>> getAllAdventuresForWorldAndDeveloper(World world, Developer developer) {
    	
    	List<Developer>   developers = new ArrayList<>();
    	List<List<Adventure>> result = new ArrayList<>();
    	
    	developers.add(developer);
        List<Adventure> allAdventuresForDeveloper = this.adventureRepository.findByDevelopers(developers);
        List<Adventure> freeAdventuresforWorld    = this.adventureRepository.findByWorld(world);
        freeAdventuresforWorld.removeAll(allAdventuresForDeveloper);
    
        result.add(allAdventuresForDeveloper);
        result.add(freeAdventuresforWorld);
        
        return result;
    }
	
	
	/**
	 * Removes the developer from adventure
	 * @param adventureId
	 * @param developerId
	 * @return adventure
	 */
	public Adventure removeDeveloperFromAdventure(long adventureId, long developerId){
		
		Adventure adventure = this.adventureRepository.findOne(adventureId);
        Developer developer = this.developerRepository.findOne(developerId);
        if(adventure != null && developer != null){
            List<Developer> developerList = adventure.getDevelopers();
            if (developerList.contains(developer)){
                developerList.remove(developer);
            }
            adventure.setDevelopers(developerList);
            adventure=this.adventureRepository.save(adventure);
        }
        
		return adventure;
		
	}
	
	/**
	 * Add a developer to adventure
	 * @param adventureId
	 * @param developerId
	 * @return adventure
	 */
	public Adventure addDeveloperToAdventure(long adventureId, long developerId) {
		
		Adventure adventure = this.adventureRepository.findOne(adventureId);
        Developer developer = this.developerRepository.findOne(developerId);
        if(adventure != null && developer != null){
            List<Developer> developerList = adventure.getDevelopers();
            if (!developerList.contains(developer)){
                developerList.add(developer);
            }
            adventure.setDevelopers(developerList);
            adventure=this.adventureRepository.save(adventure);
        }
		
		return adventure;
		
	}
    
    
    
    
    
}
