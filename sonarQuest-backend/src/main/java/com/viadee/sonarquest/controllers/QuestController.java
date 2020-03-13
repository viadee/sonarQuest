package com.viadee.sonarquest.controllers;

import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.services.*;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quest")
public class QuestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestController.class);

    private final WorldService worldService;

    private final QuestService questService;

    private final GratificationService gratificationService;

    private final AdventureService adventureService;

    private final UserService userService;

    private final WebSocketController webSocketController;

    public QuestController(WorldService worldService, QuestService questService, GratificationService gratificationService, AdventureService adventureService, UserService userService, WebSocketController webSocketController) {
        this.worldService = worldService;
        this.questService = questService;
        this.gratificationService = gratificationService;
        this.adventureService = adventureService;
        this.userService = userService;
        this.webSocketController = webSocketController;
    }

    @GetMapping
    public List<Quest> getAllQuests() {
        return questService.getAllQuests();
    }

    @GetMapping(value = "/world/{id}")
    public List<Quest> getAllQuestsForWorld(@PathVariable(value = "id") final Long worldId) {
        return questService.getAllQuestsForWorld(worldId);
    }

    @GetMapping(value = "/{id}")
    public Quest getQuestById(@PathVariable(value = "id") final Long questId) {
        return questService.findById(questId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Quest createQuest(final Principal principal, @RequestBody final Quest quest) {
        final String username = principal.getName();
        webSocketController.onCreateQuest(quest, principal);
        return questService.createQuest(quest, username);
    }

    @PutMapping(value = "/{id}")
    public Quest updateQuest(final Principal principal, @PathVariable(value = "id") final Long questId, @RequestBody final Quest questInput) {
        Quest quest = questService.updateQuest(questId, questInput);
        webSocketController.onUpdateQuest(questInput, principal);
        return quest;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteQuest(final Principal principal, @PathVariable(value = "id") final Long questId) {
        questService.deleteById(questId);
        webSocketController.onDeleteQuest(questService.findById(questId), principal);
        LOGGER.info("Deleted quest with id {}", questId);

    }

    @PutMapping(value = "/{questId}/solveQuest/")
    public Quest solveQuest(final Principal principal, @PathVariable(value = "questId") final Long questId) {
        Quest quest = questService.findById(questId);
        gratificationService.rewardUsersForSolvingQuest(quest);
        adventureService.updateAdventure(quest.getAdventure());
        Quest solvedQuest = questService.solveQuest(questId);
        webSocketController.onSolveQuest(quest, principal);
        return solvedQuest;
    }

    @PostMapping(value = "/{questId}/addWorld/{worldId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Quest addWorld(@PathVariable(value = "questId") final Long questId,
                          @PathVariable(value = "worldId") final Long worldId) {
        return questService.addQuestToWorld(questId, worldId);
    }

    @PostMapping(value = "/{questId}/addAdventure/{adventureId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Quest addAdventure(@PathVariable(value = "questId") final Long questId,
                              @PathVariable(value = "adventureId") final Long adventureId) {
        return questService.addQuestToAdventure(questId, adventureId);
    }

    @DeleteMapping(value = "/{questId}/deleteWorld")
    public void removeQuestFromWorld(@PathVariable(value = "questId") final Long questId) {
        questService.removeQuestFromWorld(questId);
    }

    @DeleteMapping(value = "/{questId}/removeAdventure")
    public void removeQuestFromAdventure(@PathVariable(value = "questId") final Long questId) {
        questService.removeQuestFromAdventure(questId);
    }

    @GetMapping(value = "/suggestTasksForQuestByGoldAmount/{worldId}/{goldAmount}")
    public List<Task> suggestTasksForQuestByGoldAmount(@PathVariable("worldId") final Long worldId,
                                                       @PathVariable("goldAmount") final Long goldAmount) {
        final World world = worldService.findById(worldId);
        List<Task> suggestedTasks = null;
        if (world != null) {
            suggestedTasks = questService.suggestTasksWithApproxGoldAmount(world, goldAmount);
        }
        return suggestedTasks;

    }

    @GetMapping(value = "/suggestTasksForQuestByXpAmount/{worldId}/{xpAmount}")
    public List<Task> suggestTasksForQuestByXpAmount(@PathVariable("worldId") final Long worldId,
                                                     @PathVariable("xpAmount") final Long xpAmount) {
        final World world = worldService.findById(worldId);
        List<Task> suggestedTasks = null;
        if (world != null) {
            suggestedTasks = questService.suggestTasksWithApproxXpAmount(world, xpAmount);
        }
        return suggestedTasks;

    }

    @GetMapping(value = "/getAllFreeForWorld/{worldId}")
    public List<Quest> getAllFreeQuestsForWorld(@PathVariable(value = "worldId") final Long worldId) {
        List<Quest> freeQuests = questService.getAllQuestsForWorld(worldId);
        freeQuests.removeIf(q -> q.getStatus() == QuestState.SOLVED);
        return freeQuests;
    }

    @GetMapping(value = "/getAllQuestsForWorldAndUser/{worldId}")
    public List<List<Quest>> getAllQuestsForWorldAndUser(final Principal principal,
                                                         @PathVariable(value = "worldId") final Long worldId) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);

        List<List<Quest>> quests = null;
        if (user != null) {
            final List<List<Quest>> allQuestsForWorldAndDeveloper = questService
                    .getAllQuestsForWorldAndUser(worldId, user);

            // now filter visible quests for the dev
            if (user.isDeveloper()) {
                LOGGER.debug("Returning getAllQuestsForWorldAndUser with visible Quests only (for devs)");
                quests = allQuestsForWorldAndDeveloper.stream()
                        .map(questlist -> questlist.stream()
                                .filter(quest -> BooleanUtils.isTrue(quest.getVisible()))
                                .collect(Collectors.toList()))
                        .collect(Collectors.toList());
            } else {
                LOGGER.debug("Returning getAllQuestsForWorldAndUser with ALL Quests (for gm/admin)");
                quests = allQuestsForWorldAndDeveloper.stream()
                        .map(ArrayList::new)
                        .collect(Collectors.toList());
            }
        }
        return quests;
    }

}
