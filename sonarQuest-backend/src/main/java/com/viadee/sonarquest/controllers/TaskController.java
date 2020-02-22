package com.viadee.sonarquest.controllers;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.SpecialTask;
import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.rules.SonarQuestTaskStatus;
import com.viadee.sonarquest.services.AdventureService;
import com.viadee.sonarquest.services.ParticipationService;
import com.viadee.sonarquest.services.QuestService;
import com.viadee.sonarquest.services.SpecialTaskService;
import com.viadee.sonarquest.services.StandardTaskService;
import com.viadee.sonarquest.services.TaskService;
import com.viadee.sonarquest.services.UserService;
import com.viadee.sonarquest.services.WorldService;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final ParticipationService participationService;

    private final TaskService taskService;

    private final StandardTaskService standardTaskService;

    private final SpecialTaskService specialTaskService;

    private final WorldService worldService;

    private final QuestService questService;

    private final AdventureService adventureService;

    private final UserService userService;

    public TaskController(ParticipationService participationService, TaskService taskService, StandardTaskService standardTaskService, SpecialTaskService specialTaskService, WorldService worldService, QuestService questService, AdventureService adventureService, UserService userService) {
        this.participationService = participationService;
        this.taskService = taskService;
        this.standardTaskService = standardTaskService;
        this.specialTaskService = specialTaskService;
        this.worldService = worldService;
        this.questService = questService;
        this.adventureService = adventureService;
        this.userService = userService;
    }

    @GetMapping
    public List<List<? extends Task>> getAllTasks() {
        final List<List<? extends Task>> taskDtos = new ArrayList<>();
        taskDtos.add(specialTaskService.findAll());
        taskDtos.add(standardTaskService.findAll());
        return taskDtos;
    }

    @GetMapping(value = "/world/{id}")
    public List<List<? extends Task>> getAllTasksForWorld(@PathVariable(value = "id") final Long worldId) {
        final World w = worldService.findById(worldId);
        final List<List<? extends Task>> taskDtos = new ArrayList<>();
        taskDtos.add(specialTaskService.findByWorld(w));
        taskDtos.add(standardTaskService.findByWorld(w));
        return taskDtos;
    }

    @GetMapping(value = "/quest/{id}")
    public List<Task> getTasksForQuest(@PathVariable(value = "id") final Long questId) {
        final Quest quest = questService.findById(questId);
        return quest.getTasks();
    }

    @GetMapping(value = "/special/world/{id}")
    public List<SpecialTask> getSpecialTasksForWorld(@PathVariable(value = "id") final Long worldId) {
        final World w = worldService.findById(worldId);
        return specialTaskService.findByWorld(w);
    }

    @GetMapping(value = "/standard/world/{id}")
    public List<StandardTask> getStandardTasksForWorld(@PathVariable(value = "id") final Long worldId) {
        final World w = worldService.findById(worldId);
        return standardTaskService.findByWorld(w);
    }

    @GetMapping(value = "/{id}")
    public Task getTaskById(@PathVariable(value = "id") final Long id) {
        return taskService.find(id);
    }

    @PostMapping(value = "/special")
    @ResponseStatus(HttpStatus.CREATED)
    public SpecialTask createSpecialTask(@RequestBody final SpecialTask specialTaskDto) {
        specialTaskService.saveDto(specialTaskDto);
        return specialTaskDto;
    }

    @PutMapping(value = "/special")
    public SpecialTask updateSpecialTask(@RequestBody final SpecialTask taskDto) {
        return specialTaskService.updateSpecialTask(taskDto);
    }

    @PutMapping(value = "/standard")
    public StandardTask updateStandardTask(@RequestBody final StandardTask taskDto) {
        return standardTaskService.updateStandardTask(taskDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTask(@PathVariable(value = "id") final Long id) {
        final Task task = taskService.find(id);
        if (task instanceof SpecialTask) {
            taskService.delete(task);
        }
    }

    @GetMapping(value = "/getFreeForWorld/{worldId}")
    public List<Task> getFreeTasksForWorld(@PathVariable(value = "worldId") final Long worldId) {
        final World world = worldService.findById(worldId);
        return taskService.getFreeTasksForWorld(world);
    }

    @PutMapping(value = "/{taskId}/closeSpecialTask/")
    public Task closeSpecialTask(@PathVariable(value = "taskId") final Long taskId) {
        Task task = taskService.find(taskId);
        if (task instanceof SpecialTask) {
            task.setStatus(SonarQuestTaskStatus.CLOSED);
            task = taskService.save(task);
            questService.closeQuestWhenAllOfItsTasksAreClosed(task.getQuest());
            adventureService.updateAdventure(task.getQuest().getAdventure());
        }
        return task;
    }

    @PostMapping(value = "/{taskId}/addToQuest/{questId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Task addToQuest(@PathVariable(value = "taskId") final Long taskId,
            @PathVariable(value = "questId") final Long questId) {
        Task task = taskService.find(taskId);
        if (task != null) {
            final Quest quest = questService.findById(questId);
            task.setQuest(quest);
            task.setStatus(SonarQuestTaskStatus.OPEN);
            task.setStartdate(new Date(System.currentTimeMillis()));
            task = taskService.save(task);
        }
        return task;
    }

    @DeleteMapping(value = "/{taskId}/deleteFromQuest")
    public void deleteFromQuest(@PathVariable(value = "taskId") final Long taskId) {
        final Task task = taskService.find(taskId);
        if (task != null) {
            task.setQuest(null);
            task.setStatus(SonarQuestTaskStatus.OPEN);
            task.setStartdate(null);
            taskService.save(task);
        }
    }

    @PostMapping(value = "/{taskId}/addParticipation/{questId}")
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

    @DeleteMapping(value = "/{taskId}/deleteParticipation")
    public void deleteParticipation(@PathVariable(value = "taskId") final Long taskId) {
        final Task task = taskService.find(taskId);
        if (task != null) {
            task.setParticipation(null);
            task.setStatus(SonarQuestTaskStatus.OPEN);
            taskService.save(task);
        }
    }

    @PutMapping(value = "/{taskId}/solveSpecialTask/")
    public SpecialTask solveSpecialTask(@PathVariable(value = "taskId") final Long taskId) {
        final SpecialTask task = specialTaskService.findById(taskId);
        taskService.solveTaskManually(task);
        return task;
    }

    @PutMapping(value = "/{taskId}/solveManually")
    public Task solveTaskManually(@PathVariable(value = "taskId") final Long taskId) {
        final Task task = taskService.find(taskId);
        taskService.solveTaskManually(task);
        return task;
    }

    @PutMapping(value = "/solveAllTasksInQuest/{questId}")
    public void solveAllTasksInQuest(@PathVariable(value = "questId") final Long questId) {
        final Quest quest = questService.findById(questId);
        taskService.solveAllTasksInQuest(quest);
    }

    @GetMapping(value = "/updateStandardTasks/{worldId}")
    public List<Task> updateStandardTasksForWorld(@PathVariable(value = "worldId") final Long worldId) {
        final World world = worldService.findById(worldId);
        if (world != null) {
            standardTaskService.updateStandardTasks(world);
        }
        return taskService.findAll();
    }

}
