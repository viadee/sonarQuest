package com.viadee.sonarQuest.controllers;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.SpecialTask;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.rules.SonarQuestStatus;
import com.viadee.sonarQuest.services.AdventureService;
import com.viadee.sonarQuest.services.ParticipationService;
import com.viadee.sonarQuest.services.QuestService;
import com.viadee.sonarQuest.services.SpecialTaskService;
import com.viadee.sonarQuest.services.StandardTaskService;
import com.viadee.sonarQuest.services.TaskService;
import com.viadee.sonarQuest.services.UserService;
import com.viadee.sonarQuest.services.WorldService;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private StandardTaskService standardTaskService;

    @Autowired
    private SpecialTaskService specialTaskService;

    @Autowired
    private WorldService worldService;

    @Autowired
    private QuestService questService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<List<? extends Task>> getAllTasks() {
        final List<List<? extends Task>> taskDtos = new ArrayList<>();
        taskDtos.add(specialTaskService.findAll());
        taskDtos.add(standardTaskService.findAll());
        return taskDtos;
    }

    @RequestMapping(value = "/world/{id}", method = RequestMethod.GET)
    public List<List<? extends Task>> getAllTasksForWorld(@PathVariable(value = "id") final Long worldId) {
        final World w = worldService.findById(worldId);
        final List<List<? extends Task>> taskDtos = new ArrayList<>();
        taskDtos.add(specialTaskService.findByWorld(w));
        taskDtos.add(standardTaskService.findByWorld(w));
        return taskDtos;
    }

    @RequestMapping(value = "/quest/{id}", method = RequestMethod.GET)
    public List<Task> getTasksForQuest(@PathVariable(value = "id") final Long questId) {
        final Quest quest = questService.findById(questId);
        return quest.getTasks();
    }

    @RequestMapping(value = "/special/world/{id}", method = RequestMethod.GET)
    public List<SpecialTask> getSpecialTasksForWorld(@PathVariable(value = "id") final Long worldId) {
        final World w = worldService.findById(worldId);
        return specialTaskService.findByWorld(w);
    }

    @RequestMapping(value = "/standard/world/{id}", method = RequestMethod.GET)
    public List<StandardTask> getStandardTasksForWorld(@PathVariable(value = "id") final Long worldId) {
        final World w = worldService.findById(worldId);
        return standardTaskService.findByWorld(w);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Task getTaskById(@PathVariable(value = "id") final Long id) {
        final Task task = taskService.find(id);
        return task;
    }

    @RequestMapping(value = "/special", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public SpecialTask createSpecialTask(@RequestBody final SpecialTask specialTaskDto) {
        specialTaskService.saveDto(specialTaskDto);
        return specialTaskDto;
    }

    @RequestMapping(value = "/special", method = RequestMethod.PUT)
    public SpecialTask updateSpecialTask(@RequestBody final SpecialTask taskDto) {
        return specialTaskService.updateSpecialTask(taskDto);
    }

    @RequestMapping(value = "/standard", method = RequestMethod.PUT)
    public StandardTask updateStandardTask(@RequestBody final StandardTask taskDto) {
        return standardTaskService.updateStandardTask(taskDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable(value = "id") final Long id) {
        final Task task = taskService.find(id);
        if (task instanceof SpecialTask) {
            taskService.delete(task);
        }
    }

    @RequestMapping(value = "/getFreeForWorld/{worldId}", method = RequestMethod.GET)
    public List<Task> getFreeTasksForWorld(@PathVariable(value = "worldId") final Long worldId) {
        final World world = worldService.findById(worldId);
        return taskService.getFreeTasksForWorld(world);
    }

    @RequestMapping(value = "/{taskId}/closeSpecialTask/", method = RequestMethod.PUT)
    public Task closeSpecialTask(@PathVariable(value = "taskId") final Long taskId) {
        Task task = taskService.find(taskId);
        if (task != null && task instanceof SpecialTask) {
            task.setStatus(SonarQuestStatus.CLOSED);
            task = taskService.save(task);
            questService.updateQuest(task.getQuest());
            adventureService.updateAdventure(task.getQuest().getAdventure());
        }
        return task;
    }

    @RequestMapping(value = "/{taskId}/addToQuest/{questId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Task addToQuest(@PathVariable(value = "taskId") final Long taskId,
            @PathVariable(value = "questId") final Long questId) {
        Task task = taskService.find(taskId);
        if (task != null) {
            final Quest quest = questService.findById(questId);
            task.setQuest(quest);
            task.setStatus(SonarQuestStatus.OPEN);
            task.setStartdate(new Date(System.currentTimeMillis()));
            task = taskService.save(task);
        }
        return task;
    }

    @RequestMapping(value = "/{taskId}/deleteFromQuest", method = RequestMethod.DELETE)
    public void deleteFromQuest(@PathVariable(value = "taskId") final Long taskId) {
        final Task task = taskService.find(taskId);
        if (task != null) {
            task.setQuest(null);
            task.setStatus(SonarQuestStatus.OPEN);
            task.setStartdate(null);
            taskService.save(task);
        }
    }

    @RequestMapping(value = "/{taskId}/addParticipation/{questId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Task addParticipation(final Principal principal,
            @PathVariable(value = "taskId") final Long taskId,
            @PathVariable(value = "questId") final Long questId) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        Task task = taskService.find(taskId);
        final Participation participation = participationService.findParticipationByQuestIdAndUserId(questId,
                user.getId());
        if (task != null && participation != null) {
            task.setParticipation(participation);
            task = taskService.save(task);
        }
        return task;
    }

    @RequestMapping(value = "/{taskId}/deleteParticipation", method = RequestMethod.DELETE)
    public void deleteParticipation(@PathVariable(value = "taskId") final Long taskId) {
        final Task task = taskService.find(taskId);
        if (task != null) {
            task.setParticipation(null);
            task.setStatus(SonarQuestStatus.OPEN);
            taskService.save(task);
        }
    }

    @RequestMapping(value = "/{taskId}/solveSpecialTask/", method = RequestMethod.PUT)
    public SpecialTask solveSpecialTask(@PathVariable(value = "taskId") final Long taskId) {
        final SpecialTask task = specialTaskService.findById(taskId);
        taskService.solveTaskManually(task);
        return task;
    }

    @RequestMapping(value = "/{taskId}/solveManually", method = RequestMethod.PUT)
    public Task solveTaskManually(@PathVariable(value = "taskId") final Long taskId) {
        final Task task = taskService.find(taskId);
        taskService.solveTaskManually(task);
        return task;
    }

    @RequestMapping(value = "/solveAllTasksInQuest/{questId}", method = RequestMethod.PUT)
    public void solveAllTasksInQuest(@PathVariable(value = "questId") final Long questId) {
        final Quest quest = questService.findById(questId);
        taskService.solveAllTasksInQuest(quest);
    }

    @RequestMapping(value = "/updateStandardTasks/{worldId}", method = RequestMethod.GET)
    public List<Task> updateStandardTasksForWorld(@PathVariable(value = "worldId") final Long worldId) {
        final World world = worldService.findById(worldId);
        if (world != null) {
            standardTaskService.updateStandardTasks(world);
        }
        return taskService.findAll();
    }

}
