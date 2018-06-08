package com.viadee.sonarQuest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarQuest.constants.AdventureStates;
import com.viadee.sonarQuest.constants.QuestStates;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.AdventureRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;

@Service
public class AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private GratificationService gratificationService;

    @Autowired
    private UserService userService;

    public void updateAdventures() {
        final List<Adventure> adventures = adventureRepository.findAll();
        adventures.forEach(this::updateAdventure);
    }

    @Transactional // Adventure updates are not to be mixed
    public synchronized void updateAdventure(final Adventure adventure) {
        if (adventure != null) {
            final List<Quest> quests = adventure.getQuests();
            final List<Quest> solvedQuests = questRepository.findByAdventureAndStatus(adventure, QuestStates.SOLVED);
            if (quests.size() == solvedQuests.size()) {
                gratificationService.rewardUsersForSolvingAdventure(adventure);
                adventure.setStatus(AdventureStates.SOLVED);
                adventureRepository.save(adventure);
            }
        }
    }

    /**
     * expects a developer object and the current world and returns the adventures that the developer has already
     * joined.
     *
     * @param world
     * @param developer
     * @return allAdventuresForDeveloper
     */
    public List<Adventure> getJoinedAdventuresForUserInWorld(final World world, final User developer) {
        final List<User> developers = new ArrayList<>();
        developers.add(developer);

        return adventureRepository.findByUsersAndWorld(developers, world);
    }

    /**
     * expects a developer object and the current world and returns the adventures that the developer can still enter.
     *
     * @param world
     * @param developer
     * @return freeAdventuresForDeveloperInWorld
     */
    public List<Adventure> getFreeAdventuresForUserInWorld(final World world, final User developer) {
        final List<Adventure> freeAdventuresForDeveloperInWorld = adventureRepository.findByWorld(world);
        freeAdventuresForDeveloperInWorld.removeAll(getJoinedAdventuresForUserInWorld(world, developer));

        return freeAdventuresForDeveloperInWorld;
    }

    /**
     * Removes the developer from adventure
     * 
     * @param adventureId
     * @param developerId
     * @return adventure
     */
    public Adventure removeUserFromAdventure(final long adventureId, final long userId) {
        Adventure adventure = adventureRepository.findOne(adventureId);
        final User user = userService.findById(userId);
        if (adventure != null && user != null) {
            final List<User> developerList = adventure.getUsers();
            if (developerList.contains(user)) {
                developerList.remove(user);
            }
            adventure.setUsers(developerList);
            adventure = adventureRepository.save(adventure);
        }

        return adventure;

    }

    /**
     * Add a developer to adventure
     * 
     * @param adventureId
     * @param userId
     * @return adventure
     */
    public Adventure addUserToAdventure(final long adventureId, final long userId) {
        Adventure adventure = adventureRepository.findOne(adventureId);
        final User user = userService.findById(userId);
        if (adventure != null && user != null) {
            final List<User> userList = adventure.getUsers();
            for (final User dev : userList) {
                if (!dev.getId().equals(user.getId())) {
                    userList.add(user);
                }
            }
            adventure.setUsers(userList);
            adventure = adventureRepository.save(adventure);
        }

        return adventure;

    }

}
