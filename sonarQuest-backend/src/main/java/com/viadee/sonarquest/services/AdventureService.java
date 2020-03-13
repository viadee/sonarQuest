package com.viadee.sonarquest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.constants.AdventureState;
import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.AdventureRepository;
import com.viadee.sonarquest.repositories.QuestRepository;

@Service
public class AdventureService {

    private final AdventureRepository adventureRepository;

    private final QuestRepository questRepository;

    private final GratificationService gratificationService;

    private final UserService userService;

    public AdventureService(final AdventureRepository adventureRepository, final QuestRepository questRepository,
            final GratificationService gratificationService, final UserService userService) {
        this.adventureRepository = adventureRepository;
        this.questRepository = questRepository;
        this.gratificationService = gratificationService;
        this.userService = userService;
    }

    public void updateAdventures() {
        final List<Adventure> adventures = adventureRepository.findAll();
        adventures.forEach(this::updateAdventure);
    }

    @Transactional // Adventure updates are not to be mixed
    public synchronized void updateAdventure(final Adventure adventure) {
        if (adventure != null) {
            final List<Quest> quests = adventure.getQuests();
            final List<Quest> solvedQuests = questRepository.findByAdventureAndStatus(adventure, QuestState.SOLVED);
            if (quests.size() == solvedQuests.size()) {
                gratificationService.rewardUsersForSolvingAdventure(adventure);
                adventure.setStatus(AdventureState.SOLVED);
                adventureRepository.save(adventure);
            }
        }
    }

    public Adventure findById(final Long adventureId) {
        return adventureRepository.findById(adventureId).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * expects a developer object and the current world and returns the adventures that the developer has already
     * joined.
     *
     * @param world
     * @param user
     * @return allAdventuresForDeveloper
     */
    public List<Adventure> getJoinedAdventuresForUserInWorld(final World world, final User user) {
        return adventureRepository.findByWorld(world).stream()
                .filter(adventure -> adventure.getUsers().contains(user))
                .collect(Collectors.toList());
    }

    /**
     * expects a developer object and the current world and returns the adventures that the developer can still enter.
     *
     * @param world
     * @param user
     * @return freeAdventuresForDeveloperInWorld
     */
    public List<Adventure> getFreeAdventuresForUserInWorld(final World world, final User user) {
        final List<Adventure> freeAdventuresForDeveloperInWorld = adventureRepository.findByWorld(world);
        freeAdventuresForDeveloperInWorld.removeAll(getJoinedAdventuresForUserInWorld(world, user));

        return freeAdventuresForDeveloperInWorld;
    }

    /**
     * Removes the developer from adventure
     *
     * @param adventureId
     *            The ID of the adventure to which a developer should be added
     * @param userId
     *            The user ID of the developer to be added
     * @return adventure
     */
    public Adventure removeUserFromAdventure(final long adventureId, final long userId)
            throws ResourceNotFoundException {
        final Adventure adventure = adventureRepository.findById(adventureId)
                .orElseThrow(ResourceNotFoundException::new);
        final User user = userService.findById(userId);
        adventure.removeUser(user);
        return adventureRepository.save(adventure);
    }

    /**
     * Add a developer to adventure
     *
     * @param adventureId
     *            The ID of the adventure from which a developer should be removed
     * @param userId
     *            The user ID of the developer to be removed
     * @return adventure
     */
    public Adventure addUserToAdventure(final long adventureId, final long userId) {
        final Adventure adventure = adventureRepository.findById(adventureId)
                .orElseThrow(ResourceNotFoundException::new);
        final User user = userService.findById(userId);
        final List<User> userList = adventure.getUsers();
        if (!userList.contains(user)) {
            adventure.addUser(user);
        }
        return adventureRepository.save(adventure);
    }

}
