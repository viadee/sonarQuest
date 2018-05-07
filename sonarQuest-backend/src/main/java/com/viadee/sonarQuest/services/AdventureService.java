package com.viadee.sonarQuest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarQuest.constants.AdventureStates;
import com.viadee.sonarQuest.constants.QuestStates;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.AdventureRepository;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;

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
        final List<Adventure> adventures = adventureRepository.findAll();
        adventures.forEach(this::updateAdventure);
    }

	@Transactional // Adventure updates are not to be mixed
	public synchronized void updateAdventure(Adventure adventure) {
        if(adventure!= null){
            final List<Quest> quests = adventure.getQuests();
            final List<Quest> solvedQuests = questRepository.findByAdventureAndStatus(adventure, QuestStates.SOLVED);
            if (quests.size() == solvedQuests.size()){
                gratificationService.rewardDevelopersForSolvingAdventure(adventure);
                adventure.setStatus(AdventureStates.SOLVED);
                adventureRepository.save(adventure);
            }
        }
    }



	/**
	 * expects a developer object and the current world and returns the adventures that the developer has already joined.
	 *
	 * @param world
	 * @param developer
	 * @return allAdventuresForDeveloper
	 */
	public List<Adventure> getJoinedAdventuresForDeveloperInWorld(World world, Developer developer) {
    	final List<Developer>   developers = new ArrayList<>();
    	developers.add(developer);

        return adventureRepository.findByDevelopersAndWorld(developers, world);
    }


	/**
	 * expects a developer object and the current world and returns the adventures that the developer can still enter.
	 *
	 * @param world
	 * @param developer
	 * @return freeAdventuresForDeveloperInWorld
	 */
	public List<Adventure> getFreeAdventuresForDeveloperInWorld(World world, Developer developer) {
        final List<Adventure> freeAdventuresForDeveloperInWorld  = adventureRepository.findByWorld(world);
        freeAdventuresForDeveloperInWorld.removeAll(getJoinedAdventuresForDeveloperInWorld(world, developer));

        return freeAdventuresForDeveloperInWorld;
    }


	/**
	 * Removes the developer from adventure
	 * @param adventureId
	 * @param developerId
	 * @return adventure
	 */
	public Adventure removeDeveloperFromAdventure(long adventureId, long developerId){

		Adventure adventure = adventureRepository.findOne(adventureId);
        final Developer developer = developerRepository.findOne(developerId);
        if(adventure != null && developer != null){
            final List<Developer> developerList = adventure.getDevelopers();
            if (developerList.contains(developer)){
                developerList.remove(developer);
            }
            adventure.setDevelopers(developerList);
            adventure=adventureRepository.save(adventure);
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

		Adventure adventure = adventureRepository.findOne(adventureId);
        final Developer developer = developerRepository.findOne(developerId);
        if(adventure != null && developer != null){
            final List<Developer> developerList = adventure.getDevelopers();
            if (!developerList.contains(developer)){
                developerList.add(developer);
            }
            adventure.setDevelopers(developerList);
            adventure=adventureRepository.save(adventure);
        }

		return adventure;

	}





}
