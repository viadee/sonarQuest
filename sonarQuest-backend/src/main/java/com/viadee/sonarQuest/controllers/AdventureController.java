package com.viadee.sonarQuest.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.constants.AdventureState;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.AdventureRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.AdventureService;
import com.viadee.sonarQuest.services.GratificationService;
import com.viadee.sonarQuest.services.UserService;

@RestController
@RequestMapping("/adventure")
public class AdventureController {

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private GratificationService gratificationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Adventure> getAllAdventures() {
        return adventureRepository.findAll();
    }

    @RequestMapping(value = "/world/{id}", method = RequestMethod.GET)
    public List<Adventure> getAllAdventuresForWorld(@PathVariable(value = "id") final Long world_id) {
        final World w = worldRepository.findOne(world_id);
        return adventureRepository.findByWorld(w);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Adventure getAdventureById(@PathVariable(value = "id") final Long id) {
        return adventureRepository.findOne(id);
    }

    @RequestMapping(value = "/getJoined/{world_id}", method = RequestMethod.GET)
    public List<Adventure> getJoinedAdventures(final Principal principal,
            @PathVariable(value = "world_id") final Long world_id) {
        final World w = worldRepository.findOne(world_id);
        final User user = userService.findByUsername(principal.getName());
        return adventureService.getJoinedAdventuresForUserInWorld(w, user);
    }

    @RequestMapping(value = "/getFree/{world_id}", method = RequestMethod.GET)
    public List<Adventure> getFreeAdventures(final Principal principal,
            @PathVariable(value = "world_id") final Long world_id) {
        final World w = worldRepository.findOne(world_id);
        final User user = userService.findByUsername(principal.getName());
        return adventureService.getFreeAdventuresForUserInWorld(w, user);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Adventure createAdventure(@RequestBody final Adventure adventure) {
        return adventureRepository.save(adventure);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Adventure updateAdventure(@PathVariable(value = "id") final Long id,
            @RequestBody final Adventure data) {
        Adventure adventure = adventureRepository.findOne(id);
        if (adventure != null) {
            adventure.setTitle(data.getTitle());
            adventure.setGold(data.getGold());
            adventure.setXp(data.getXp());
            adventure.setStory(data.getStory());
            adventure = adventureRepository.save(adventure);
        }
        return adventure;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAdventure(@PathVariable(value = "id") final Long id) {
        final Adventure adventure = adventureRepository.findOne(id);
        if (adventure != null) {
            adventureRepository.delete(adventure);
        }
    }

    @RequestMapping(value = "/{adventureId}/solveAdventure/", method = RequestMethod.PUT)
    public void solveAdventure(@PathVariable(value = "adventureId") final Long adventureId) {
        final Adventure adventure = adventureRepository.findOne(adventureId);
        if (adventure != null) {
            adventure.setStatus(AdventureState.SOLVED);
            adventureRepository.save(adventure);
            gratificationService.rewardUsersForSolvingAdventure(adventure);
        }
    }

    @RequestMapping(value = "/{adventureId}/addQuest/{questId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Adventure addQuest(@PathVariable(value = "adventureId") final Long adventureId,
            @PathVariable(value = "questId") final Long questId) {
        Adventure adventure = adventureRepository.findOne(adventureId);
        if (adventure != null) {
            final Quest quest = questRepository.findOne(questId);
            quest.setAdventure(adventure);
            questRepository.save(quest);
            if (adventure.getWorld() == null) {
                adventure.setWorld(quest.getWorld());
                adventureRepository.save(adventure);
            }
            adventure = adventureRepository.findOne(adventureId);
        }
        return adventure;
    }

    @RequestMapping(value = "/{adventureId}/join", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Adventure join(final Principal principal, @PathVariable(value = "adventureId") final Long adventureId) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        return adventureService.addUserToAdventure(adventureId, user.getId());
    }

    /**
     * 
     * @param adventureId
     *            The id of the adventure
     * @param developerId
     *            The id of the developer to remove
     * @return Gives the adventure where the Developer was removed
     */
    @RequestMapping(value = "/{adventureId}/leave", method = RequestMethod.POST)
    public Adventure leave(final Principal principal,
            @PathVariable(value = "adventureId") final Long adventureId) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        return adventureService.removeUserFromAdventure(adventureId, user.getId());
    }

}
