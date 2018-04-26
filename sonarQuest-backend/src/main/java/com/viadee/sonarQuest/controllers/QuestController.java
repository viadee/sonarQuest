package com.viadee.sonarQuest.controllers;

import static com.viadee.sonarQuest.dtos.QuestDto.toQuestDto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.constants.QuestStates;
import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.dtos.QuestDto;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.AdventureRepository;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.AdventureService;
import com.viadee.sonarQuest.services.GratificationService;
import com.viadee.sonarQuest.services.QuestService;

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
    private DeveloperRepository developerRepository;

    @Autowired
    private QuestService questService;

    @Autowired
    private GratificationService gratificationService;

    @Autowired
    private AdventureService adventureService;

    @RequestMapping(method = RequestMethod.GET)
    public List<QuestDto> getAllQuests() {
        return this.questRepository.findAll().stream().map(QuestDto::toQuestDto).collect(Collectors.toList());
    }

    @CrossOrigin
    @RequestMapping(value = "/world/{id}", method = RequestMethod.GET)
    public List<QuestDto> getAllQuestsForWorld(@PathVariable(value = "id") final Long world_id) {
    	World w = worldRepository.findById(world_id);
        return this.questRepository.findByWorld(w).stream().map(QuestDto::toQuestDto).collect(Collectors.toList());
    }
    
    

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public QuestDto getQuestById(@PathVariable(value = "id") final Long id) {
        final Quest quest = this.questRepository.findOne(id);
        return toQuestDto(quest);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Quest createQuest(@RequestBody final QuestDto questDto) {
        return this.questRepository.save(new Quest(questDto.getTitle(), questDto.getStory(), QuestStates.OPEN,
                questDto.getGold(), questDto.getXp(), questDto.getImage()));
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Quest updateQuest(@PathVariable(value = "id") final Long id, @RequestBody final QuestDto questDto) {
        Quest quest = this.questRepository.findOne(id);
        if (quest != null) {
            quest.setTitle(questDto.getTitle());
            quest.setGold(questDto.getGold());
            quest.setXp(questDto.getXp());
            quest.setStory(questDto.getStory());
            quest.setImage(questDto.getImage());
            quest = this.questRepository.save(quest);
        }
        return quest;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteQuest(@PathVariable(value = "id") final Long id) {
        final Quest quest = this.questRepository.findOne(id);
        if (quest != null) {
            final List<Task> tasks = quest.getTasks();
            tasks.forEach(task -> task.setStatus(TaskStates.CREATED));
            this.questRepository.delete(quest);
        }
    }

    @RequestMapping(value = "/{questId}/solveQuest/", method = RequestMethod.PUT)
    public void solveQuest(@PathVariable(value = "questId") final Long questId) {
        final Quest quest = this.questRepository.findOne(questId);
        if (quest != null) {
            quest.setStatus(QuestStates.SOLVED);
            questRepository.save(quest);
            gratificationService.rewardDevelopersForSolvingQuest(quest);
            adventureService.updateAdventure(quest.getAdventure());
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/{questId}/addWorld/{worldId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public QuestDto addWorld(@PathVariable(value = "questId") final Long questId,
            @PathVariable(value = "worldId") final Long worldId) {
        Quest quest = this.questRepository.findOne(questId);
        if (quest != null) {
            final World world = this.worldRepository.findOne(worldId);
            quest.setWorld(world);
            quest = this.questRepository.save(quest);
        }
        return toQuestDto(quest);
    }
    
    @CrossOrigin
    @RequestMapping(value = "/{questId}/addAdventure/{adventureId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public QuestDto addAdventure(@PathVariable(value = "questId") final Long questId, @PathVariable(value = "adventureId") final Long adventureId) {
        Quest quest = this.questRepository.findOne(questId);
        if (quest != null) {
            final Adventure adventure = this.adventureRepository.findOne(adventureId);
            quest.setAdventure(adventure);
            quest = this.questRepository.save(quest);
        }
        return toQuestDto(quest);
    }

    @RequestMapping(value = "/{questId}/deleteWorld", method = RequestMethod.DELETE)
    public void deleteWorld(@PathVariable(value = "questId") final Long questId) {
        final Quest quest = this.questRepository.findOne(questId);
        if (quest != null) {
            quest.setWorld(null);
            this.questRepository.save(quest);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/{questId}/removeAdventure", method = RequestMethod.DELETE)
    public void deleteAdventure(@PathVariable(value = "questId") final Long questId) {
        final Quest quest = this.questRepository.findOne(questId);
        if (quest != null) {
            quest.setAdventure(null);
            this.questRepository.save(quest);
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
    public List<QuestDto> getAllFreeQuestsForWorld(@PathVariable(value = "worldId") final Long worldId) {
        final World world = worldRepository.findOne(worldId);
        List<QuestDto> freeQuests = null;
        if (world != null) {
            freeQuests = this.questRepository.findByWorldAndAdventure(world, null).stream()
                    .map(QuestDto::toQuestDto).collect(Collectors.toList());
        }
        return freeQuests;
    }

    @RequestMapping(value = "/getAllQuestsForWorldAndDeveloper/{worldId}/{developerId}", method = RequestMethod.GET)
    public List<List<QuestDto>> getAllQuestsForWorldAndUser(@PathVariable(value = "worldId") final Long worldId,
            @PathVariable(value = "developerId") final Long developerId) {
        final World world = worldRepository.findOne(worldId);
        final Developer developer = developerRepository.findById(developerId);
        List<List<QuestDto>> quests = null;
        if (world != null && developer != null) {
            final List<List<Quest>> allQuestsForWorldAndDeveloper = this.questService
                    .getAllQuestsForWorldAndDeveloper(world, developer);

            quests = allQuestsForWorldAndDeveloper.stream()
                    .map(questlist -> questlist.stream().map(QuestDto::toQuestDto).collect(Collectors.toList()))
                    .collect(Collectors.toList());
        }
        return quests;
    }

}
