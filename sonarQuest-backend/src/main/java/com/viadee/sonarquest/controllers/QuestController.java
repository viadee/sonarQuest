package com.viadee.sonarquest.controllers;

import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.AdventureRepository;
import com.viadee.sonarquest.repositories.QuestRepository;
import com.viadee.sonarquest.repositories.WorldRepository;
import com.viadee.sonarquest.rules.SonarQuestStatus;
import com.viadee.sonarquest.services.AdventureService;
import com.viadee.sonarquest.services.EventService;
import com.viadee.sonarquest.services.GratificationService;
import com.viadee.sonarquest.services.QuestService;
import com.viadee.sonarquest.services.UserService;

@RestController
@RequestMapping("/quest")
public class QuestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestController.class);

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private QuestService questService;

    @Autowired
    private GratificationService gratificationService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Quest> getAllQuests() {
        return questRepository.findAll();
    }

    @GetMapping(value = "/world/{id}")
    public List<Quest> getAllQuestsForWorld(@PathVariable(value = "id") final Long world_id) {
        final World w = worldRepository.findOne(world_id);
        return questRepository.findByWorld(w);
    }

    @GetMapping(value = "/{id}")
    public Quest getQuestById(@PathVariable(value = "id") final Long id) {
        return questRepository.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Quest createQuest(final Principal principal, @RequestBody final Quest questDto) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        questDto.setStartdate(new Date(System.currentTimeMillis()));
        questDto.setStatus(QuestState.OPEN);
        questDto.setCreatorName(user.getUsername());
        eventService.createEventForCreatedQuest(questDto, principal);
        return questRepository.save(questDto);
    }

    @PutMapping(value = "/{id}")
    public Quest updateQuest(@PathVariable(value = "id") final Long id, @RequestBody final Quest data) {
        Quest quest = questRepository.findOne(id);
        if (quest != null) {
            quest.setTitle(data.getTitle());
            quest.setGold(data.getGold());
            quest.setXp(data.getXp());
            quest.setStory(data.getStory());
            quest.setImage(data.getImage());
            quest.setVisible(data.getVisible());
            quest.setCreatorName(data.getCreatorName());
            quest = questRepository.save(quest);
        }
        return quest;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteQuest(@PathVariable(value = "id") final Long id) {
        final Quest quest = questRepository.findOne(id);
        if (quest != null) {
            final List<Task> tasks = quest.getTasks();
            tasks.forEach(task -> task.setStatus(SonarQuestStatus.OPEN));
            questRepository.delete(quest);
            LOGGER.info("Deleted quest with id {}", id);
        }
    }

    @PutMapping(value = "/{questId}/solveQuest/")
    public void solveQuest(final Principal principal, @PathVariable(value = "questId") final Long questId) {
        final Quest quest = questRepository.findOne(questId);
        if (quest != null) {
            gratificationService.rewardUsersForSolvingQuest(quest);
            adventureService.updateAdventure(quest.getAdventure());
            quest.setEnddate(new Date(System.currentTimeMillis()));
            quest.setStatus(QuestState.SOLVED);
            questRepository.save(quest);
            eventService.createEventForSolvedQuest(quest, principal);
        }
    }
    
    @PutMapping(value = "/{questId}/solveQuestDummy")
    @ResponseStatus(HttpStatus.CREATED)
    public Quest solveQuestDummy(final Principal principal, @PathVariable(value = "questId") final Long questId) {
        Quest quest = questRepository.findOne(questId);
        if (quest != null) {
            quest.setEnddate(new Date(System.currentTimeMillis()));
            quest.setStatus(QuestState.SOLVED);
            eventService.createEventForSolvedQuest(quest, principal);
        }
        return quest;
    }

    @PostMapping(value = "/{questId}/addWorld/{worldId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Quest addWorld(@PathVariable(value = "questId") final Long questId,
            @PathVariable(value = "worldId") final Long worldId) {
        Quest quest = questRepository.findOne(questId);
        if (quest != null) {
            final World world = worldRepository.findOne(worldId);
            quest.setWorld(world);
            quest = questRepository.save(quest);
        }
        return quest;
    }

    @PostMapping(value = "/{questId}/addAdventure/{adventureId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Quest addAdventure(@PathVariable(value = "questId") final Long questId,
            @PathVariable(value = "adventureId") final Long adventureId) {
        Quest quest = questRepository.findOne(questId);
        if (quest != null) {
            final Adventure adventure = adventureRepository.findOne(adventureId);
            quest.setAdventure(adventure);
            quest = questRepository.save(quest);
        }
        return quest;
    }

    @DeleteMapping(value = "/{questId}/deleteWorld")
    public void deleteWorld(@PathVariable(value = "questId") final Long questId) {
        final Quest quest = questRepository.findOne(questId);
        if (quest != null) {
            quest.setWorld(null);
            questRepository.save(quest);
        }
    }

    @DeleteMapping(value = "/{questId}/removeAdventure")
    public void deleteAdventure(@PathVariable(value = "questId") final Long questId) {
        final Quest quest = questRepository.findOne(questId);
        if (quest != null) {
            quest.setAdventure(null);
            questRepository.save(quest);
        }
    }

    @GetMapping(value = "/suggestTasksForQuestByGoldAmount/{worldId}/{goldAmount}")
    public List<Task> suggestTasksForQuestByGoldAmount(@PathVariable("worldId") final Long worldId,
            @PathVariable("goldAmount") final Long goldAmount) {
        final World world = worldRepository.findOne(worldId);
        List<Task> suggestedTasks = null;
        if (world != null) {
            suggestedTasks = questService.suggestTasksWithApproxGoldAmount(world, goldAmount);
        }
        return suggestedTasks;

    }

    @GetMapping(value = "/suggestTasksForQuestByXpAmount/{worldId}/{xpAmount}")
    public List<Task> suggestTasksForQuestByXpAmount(@PathVariable("worldId") final Long worldId,
            @PathVariable("xpAmount") final Long xpAmount) {
        final World world = worldRepository.findOne(worldId);
        List<Task> suggestedTasks = null;
        if (world != null) {
            suggestedTasks = questService.suggestTasksWithApproxXpAmount(world, xpAmount);
        }
        return suggestedTasks;

    }

    @GetMapping(value = "/getAllFreeForWorld/{worldId}")
    public List<Quest> getAllFreeQuestsForWorld(@PathVariable(value = "worldId") final Long worldId) {
        final World world = worldRepository.findOne(worldId);
        List<Quest> freeQuests = null;
        if (world != null) {
            freeQuests = questRepository.findByWorldAndAdventure(world, null);
            freeQuests.removeIf(q -> q.getStatus() == QuestState.SOLVED);
        }
        return freeQuests;
    }

    @GetMapping(value = "/getAllQuestsForWorldAndUser/{worldId}")
    public List<List<Quest>> getAllQuestsForWorldAndUser(final Principal principal,
            @PathVariable(value = "worldId") final Long worldId) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        final World world = worldRepository.findOne(worldId);
        List<List<Quest>> quests = null;
        if (world != null && user != null) {
            final List<List<Quest>> allQuestsForWorldAndDeveloper = questService
                    .getAllQuestsForWorldAndUser(world, user);

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
                        .map(questlist -> questlist.stream()
                                .collect(Collectors.toList()))
                    .collect(Collectors.toList());
            }
        }
        return quests;
    }

}
