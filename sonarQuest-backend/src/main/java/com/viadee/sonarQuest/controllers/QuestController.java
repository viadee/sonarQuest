package com.viadee.sonarQuest.controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.constants.QuestStates;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.AdventureRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.rules.SonarQuestStatus;
import com.viadee.sonarQuest.services.AdventureService;
import com.viadee.sonarQuest.services.GratificationService;
import com.viadee.sonarQuest.services.QuestService;
import com.viadee.sonarQuest.services.UserService;

@RestController
@RequestMapping("/quest")
public class QuestController {

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

    @RequestMapping(method = RequestMethod.GET)
    public List<Quest> getAllQuests() {
        return questRepository.findAll();
    }

    @RequestMapping(value = "/world/{id}", method = RequestMethod.GET)
    public List<Quest> getAllQuestsForWorld(@PathVariable(value = "id") final Long world_id) {
        final World w = worldRepository.findOne(world_id);
        return questRepository.findByWorld(w);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Quest getQuestById(@PathVariable(value = "id") final Long id) {
        return questRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Quest createQuest(@RequestBody final Quest questDto) {
        return questRepository.save(questDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Quest updateQuest(@PathVariable(value = "id") final Long id, @RequestBody final Quest data) {
        Quest quest = questRepository.findOne(id);
        if (quest != null) {
            quest.setTitle(data.getTitle());
            quest.setGold(data.getGold());
            quest.setXp(data.getXp());
            quest.setStory(data.getStory());
            quest.setImage(data.getImage());
            quest = questRepository.save(quest);
        }
        return quest;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteQuest(@PathVariable(value = "id") final Long id) {
        final Quest quest = questRepository.findOne(id);
        if (quest != null) {
            final List<Task> tasks = quest.getTasks();
            tasks.forEach(task -> task.setStatus(SonarQuestStatus.CREATED.getText()));
            questRepository.delete(quest);
        }
    }

    @RequestMapping(value = "/{questId}/solveQuest/", method = RequestMethod.PUT)
    public void solveQuest(@PathVariable(value = "questId") final Long questId) {
        final Quest quest = questRepository.findOne(questId);
        if (quest != null) {
            quest.setStatus(QuestStates.SOLVED);
            questRepository.save(quest);
            gratificationService.rewardUsersForSolvingQuest(quest);
            adventureService.updateAdventure(quest.getAdventure());
        }
    }

    @RequestMapping(value = "/{questId}/addWorld/{worldId}", method = RequestMethod.POST)
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

    @RequestMapping(value = "/{questId}/addAdventure/{adventureId}", method = RequestMethod.POST)
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

    @RequestMapping(value = "/{questId}/deleteWorld", method = RequestMethod.DELETE)
    public void deleteWorld(@PathVariable(value = "questId") final Long questId) {
        final Quest quest = questRepository.findOne(questId);
        if (quest != null) {
            quest.setWorld(null);
            questRepository.save(quest);
        }
    }

    @RequestMapping(value = "/{questId}/removeAdventure", method = RequestMethod.DELETE)
    public void deleteAdventure(@PathVariable(value = "questId") final Long questId) {
        final Quest quest = questRepository.findOne(questId);
        if (quest != null) {
            quest.setAdventure(null);
            questRepository.save(quest);
        }
    }

    @RequestMapping(value = "/suggestTasksForQuestByGoldAmount/{worldId}/{goldAmount}", method = RequestMethod.GET)
    public List<Task> suggestTasksForQuestByGoldAmount(@PathVariable("worldId") final Long worldId,
            @PathVariable("goldAmount") final Long goldAmount) {
        final World world = worldRepository.findOne(worldId);
        List<Task> suggestedTasks = null;
        if (world != null) {
            suggestedTasks = questService.suggestTasksWithApproxGoldAmount(world, goldAmount);
        }
        return suggestedTasks;

    }

    @RequestMapping(value = "/suggestTasksForQuestByXpAmount/{worldId}/{xpAmount}", method = RequestMethod.GET)
    public List<Task> suggestTasksForQuestByXpAmount(@PathVariable("worldId") final Long worldId,
            @PathVariable("xpAmount") final Long xpAmount) {
        final World world = worldRepository.findOne(worldId);
        List<Task> suggestedTasks = null;
        if (world != null) {
            suggestedTasks = questService.suggestTasksWithApproxXpAmount(world, xpAmount);
        }
        return suggestedTasks;

    }

    @RequestMapping(value = "/getAllFreeForWorld/{worldId}", method = RequestMethod.GET)
    public List<Quest> getAllFreeQuestsForWorld(@PathVariable(value = "worldId") final Long worldId) {
        final World world = worldRepository.findOne(worldId);
        List<Quest> freeQuests = null;
        if (world != null) {
            freeQuests = questRepository.findByWorldAndAdventure(world, null);
        }
        return freeQuests;
    }

    @RequestMapping(value = "/getAllQuestsForWorldAndUser/{worldId}", method = RequestMethod.GET)
    public List<List<Quest>> getAllQuestsForWorldAndUser(final Principal principal,
            @PathVariable(value = "worldId") final Long worldId) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        final World world = worldRepository.findOne(worldId);
        List<List<Quest>> quests = null;
        if (world != null && user != null) {
            final List<List<Quest>> allQuestsForWorldAndDeveloper = questService
                    .getAllQuestsForWorldAndUser(world, user);

            quests = allQuestsForWorldAndDeveloper.stream()
                    .map(questlist -> questlist.stream().collect(Collectors.toList()))
                    .collect(Collectors.toList());
        }
        return quests;
    }

}
