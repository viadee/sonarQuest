package com.viadee.sonarquest.controllers;

import com.viadee.sonarquest.constants.AdventureState;
import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.AdventureRepository;
import com.viadee.sonarquest.repositories.QuestRepository;
import com.viadee.sonarquest.repositories.WorldRepository;
import com.viadee.sonarquest.services.AdventureService;
import com.viadee.sonarquest.services.EventService;
import com.viadee.sonarquest.services.GratificationService;
import com.viadee.sonarquest.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/adventure")
public class AdventureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdventureController.class);

    private final AdventureRepository adventureRepository;

    private final QuestRepository questRepository;

    private final UserService userService;

    private final WorldRepository worldRepository;

    private final AdventureService adventureService;

    private final GratificationService gratificationService;

    private final EventService eventService;

    public AdventureController(AdventureRepository adventureRepository, QuestRepository questRepository, UserService userService, WorldRepository worldRepository, AdventureService adventureService, GratificationService gratificationService, EventService eventService) {
        this.adventureRepository = adventureRepository;
        this.questRepository = questRepository;
        this.userService = userService;
        this.worldRepository = worldRepository;
        this.adventureService = adventureService;
        this.gratificationService = gratificationService;
        this.eventService = eventService;
    }

    @GetMapping
    public List<Adventure> getAllAdventures() {
        return adventureRepository.findAll();
    }

    @GetMapping(value = "/world/{id}")
    public List<Adventure> getAllAdventuresForWorld(@PathVariable(value = "id") final Long worldId) {
        final World w = worldRepository.findById(worldId).orElseThrow(ResourceNotFoundException::new);
        return adventureRepository.findByWorld(w);
    }

    @GetMapping(value = "/{id}")
    public Adventure getAdventureById(@PathVariable(value = "id") final Long adventureId) {
        return adventureRepository.findById(adventureId).orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/getJoined/{world_id}")
    public List<Adventure> getJoinedAdventures(final Principal principal,
            @PathVariable(value = "world_id") final Long worldId) {
        final World w = worldRepository.findById(worldId).orElseThrow(ResourceNotFoundException::new);
        final User user = userService.findByUsername(principal.getName());
        return adventureService.getJoinedAdventuresForUserInWorld(w, user);
    }

    @GetMapping(value = "/getFree/{world_id}")
    public List<Adventure> getFreeAdventures(final Principal principal,
            @PathVariable(value = "world_id") final Long worldId) {
        final World w = worldRepository.findById(worldId).orElseThrow(ResourceNotFoundException::new);
        final User user = userService.findByUsername(principal.getName());
        return adventureService.getFreeAdventuresForUserInWorld(w, user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Adventure createAdventure(final Principal principal, @RequestBody final Adventure adventure) {
        adventure.setStatus(AdventureState.OPEN);
        adventure.setStartdate(new Date(System.currentTimeMillis()));
        eventService.createEventForCreatedAdventure(adventure, principal);
        return adventureRepository.save(adventure);
    }

    @PutMapping(value = "/{id}")
    public Adventure updateAdventure(@PathVariable(value = "id") final Long adventureId, @RequestBody final Adventure data) {
        Adventure adventure = adventureRepository.findById(adventureId).orElseThrow(ResourceNotFoundException::new);

            adventure.setTitle(data.getTitle());
            adventure.setGold(data.getGold());
            adventure.setXp(data.getXp());
            adventure.setStory(data.getStory());
            adventure.setVisible(data.getVisible());
            adventure = adventureRepository.save(adventure);

        return adventure;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAdventure(@PathVariable(value = "id") final Long adventureId) {
        final Adventure adventure = adventureRepository.findById(adventureId).orElseThrow(ResourceNotFoundException::new);
            adventureRepository.delete(adventure);
            LOGGER.info("Deleted adventure with id {}", adventureId);
    }

    @PutMapping(value = "/{adventureId}/solveAdventure")
    public Adventure solveAdventure(final Principal principal,
            @PathVariable(value = "adventureId") final Long adventureId) {
        final Adventure adventure = adventureRepository.findById(adventureId).orElseThrow(ResourceNotFoundException::new);
            adventure.setStatus(AdventureState.SOLVED);
            adventure.setEnddate(new Date(System.currentTimeMillis()));
            adventureRepository.save(adventure);
            gratificationService.rewardUsersForSolvingAdventure(adventure);
            eventService.createEventForSolvedAdventure(adventure, principal);
        return adventure;
    }

    @PostMapping(value = "/{adventureId}/addQuest/{questId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Adventure addQuest(@PathVariable(value = "adventureId") final Long adventureId,
            @PathVariable(value = "questId") final Long questId) {
        Adventure adventure = adventureRepository.findById(adventureId).orElseThrow(ResourceNotFoundException::new);

            final Quest quest = questRepository.findById(questId).orElseThrow(ResourceNotFoundException::new);
            quest.setAdventure(adventure);
            questRepository.save(quest);
            if (adventure.getWorld() == null) {
                adventure.setWorld(quest.getWorld());
                adventureRepository.save(adventure);
            }
        return adventure;
    }

    @PostMapping(value = "/{adventureId}/join")
    @ResponseStatus(HttpStatus.CREATED)
    public Adventure join(final Principal principal, @PathVariable(value = "adventureId") final Long adventureId) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        return adventureService.addUserToAdventure(adventureId, user.getId());
    }

    /**
     * 
     * @param adventureId The id of the adventure
     * @param developerId The id of the developer to remove
     * @return Gives the adventure where the Developer was removed
     */
    @PostMapping(value = "/{adventureId}/leave")
    public Adventure leave(final Principal principal, @PathVariable(value = "adventureId") final Long adventureId) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        return adventureService.removeUserFromAdventure(adventureId, user.getId());
    }

}
