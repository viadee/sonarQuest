package com.viadee.sonarQuest.controllers;

import static com.viadee.sonarQuest.dtos.TaskDto.toTaskDto;

import java.util.ArrayList;
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

import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.dtos.SpecialTaskDto;
import com.viadee.sonarQuest.dtos.TaskDto;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.SpecialTask;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.SpecialTaskRepository;
import com.viadee.sonarQuest.repositories.StandardTaskRepository;
import com.viadee.sonarQuest.repositories.TaskRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.AdventureService;
import com.viadee.sonarQuest.services.GratificationService;
import com.viadee.sonarQuest.services.ParticipationService;
import com.viadee.sonarQuest.services.QuestService;
import com.viadee.sonarQuest.services.SpecialTaskService;
import com.viadee.sonarQuest.services.StandardTaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StandardTaskRepository standardTaskRepository;

    @Autowired
    private SpecialTaskRepository specialTaskRepository;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private StandardTaskService standardTaskService;

    @Autowired
    private SpecialTaskService specialTaskService;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private QuestService questService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private GratificationService gratificationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<List<TaskDto>> getAllTasks() {
        final List<List<TaskDto>> taskDtos = new ArrayList<>();
        List<TaskDto> specialTaskDtos;
        List<TaskDto> standardTaskDtos;
        specialTaskDtos = this.specialTaskRepository.findAll().stream().map(TaskDto::toTaskDto)
                .collect(Collectors.toList());
        standardTaskDtos = this.standardTaskRepository.findAll().stream().map(TaskDto::toTaskDto)
                .collect(Collectors.toList());
        taskDtos.add(specialTaskDtos);
        taskDtos.add(standardTaskDtos);
        return taskDtos;
    }

    @CrossOrigin
    @RequestMapping(value = "/world/{id}", method = RequestMethod.GET)
    public List<List<TaskDto>> getAllTasksForWorld(@PathVariable(value = "id") final Long world_id) {
    	World w = worldRepository.findOne(world_id);
        final List<List<TaskDto>> taskDtos = new ArrayList<>();
        List<TaskDto> specialTaskDtos;
        List<TaskDto> standardTaskDtos;
        specialTaskDtos = this.specialTaskRepository.findByWorld(w).stream().map(TaskDto::toTaskDto).collect(Collectors.toList());
        standardTaskDtos = this.standardTaskRepository.findByWorld(w).stream().map(TaskDto::toTaskDto).collect(Collectors.toList());
        taskDtos.add(specialTaskDtos);
        taskDtos.add(standardTaskDtos);
        return taskDtos;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TaskDto getTaskById(@PathVariable(value = "id") final Long id) {
        final Task task = this.taskRepository.findById(id);
        return toTaskDto(task);
    }

    @CrossOrigin
    @RequestMapping(value = "/special", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public SpecialTaskDto createTask(@RequestBody SpecialTaskDto specialTaskDto) {
    	this.specialTaskService.saveDto(specialTaskDto);
    	return specialTaskDto;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public TaskDto updateTask(@PathVariable(value = "id") final Long id, @RequestBody final TaskDto taskDto) {
        TaskDto resultTaskDto = null;
        final Task task = this.taskRepository.findById(id);
        if (task != null) {
            task.setTitle(taskDto.getTitle());
            task.setGold(taskDto.getGold());
            task.setXp(taskDto.getXp());
            this.taskRepository.save(task);
            resultTaskDto = toTaskDto(task);
        }
        if (task instanceof SpecialTask) {
            ((SpecialTask) task).setMessage(((SpecialTaskDto) taskDto).getMessage());
            this.taskRepository.save(task);
            resultTaskDto = toTaskDto(task);
        }
        return resultTaskDto;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable(value = "id") final Long id) {
        final Task task = this.taskRepository.findById(id);
        if (task instanceof SpecialTask) {
            this.taskRepository.delete(task);
        }
    }

    @RequestMapping(value = "/getFreeForWorld/{worldId}", method = RequestMethod.GET)
    public List<TaskDto> getFreeTasksForWorld(@PathVariable(value = "worldId") final Long worldId) {
        final World world = worldRepository.findOne(worldId);
        List<TaskDto> freeTasks = null;
        if (world != null) {
            // List<TaskDto> freeSpecialTasks = null;
            freeTasks = this.taskRepository.findByWorldAndStatus(world, TaskStates.CREATED).stream().map(TaskDto::toTaskDto).collect(Collectors.toList());
            
            /* 
             * @Florian - For what?
            freeSpecialTasks = this.specialTaskRepository.findByStatus(TaskStates.CREATED).stream().map(specialTask -> toTaskDto(specialTask)).collect(Collectors.toList());
            freeTasks.addAll(freeSpecialTasks);
             */
        }
        return freeTasks;
    }

    @CrossOrigin
    @RequestMapping(value = "/{taskId}/solveSpecialTask/", method = RequestMethod.PUT)
    public TaskDto solveSpecialTask(@PathVariable(value = "taskId") final Long taskId) {
        Task task = this.taskRepository.findOne(taskId);
        if (task != null && task instanceof SpecialTask) {
            task.setStatus(TaskStates.SOLVED);
            task = this.taskRepository.save(task);
            gratificationService.rewardDeveloperForSolvingTask(task);
            questService.updateQuest(task.getQuest());
            adventureService.updateAdventure(task.getQuest().getAdventure());
        }
        return toTaskDto(task);
    }

    @CrossOrigin
    @RequestMapping(value = "/{taskId}/closeSpecialTask/", method = RequestMethod.PUT)
    public TaskDto closeSpecialTask(@PathVariable(value = "taskId") final Long taskId) {
        Task task = this.taskRepository.findOne(taskId);
        if (task != null && task instanceof SpecialTask) {
            task.setStatus(TaskStates.CLOSED);
            task = this.taskRepository.save(task);
            questService.updateQuest(task.getQuest());
            adventureService.updateAdventure(task.getQuest().getAdventure());
        }
        return toTaskDto(task);
    }

    @CrossOrigin
    @RequestMapping(value = "/{taskId}/addToQuest/{questId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto addToQuest(@PathVariable(value = "taskId") final Long taskId,
            @PathVariable(value = "questId") final Long questId) {
        Task task = this.taskRepository.findOne(taskId);
        if (task != null) {
            final Quest quest = this.questRepository.findOne(questId);
            task.setQuest(quest);
            task.setStatus(TaskStates.OPEN);
            task = this.taskRepository.save(task);
        }
        return toTaskDto(task);
    }

    @CrossOrigin
    @RequestMapping(value = "/{taskId}/deleteFromQuest", method = RequestMethod.DELETE)
    public void deleteFromQuest(@PathVariable(value = "taskId") final Long taskId) {
        final Task task = this.taskRepository.findOne(taskId);
        if (task != null) {
            task.setQuest(null);
            task.setStatus(TaskStates.CREATED);
            this.taskRepository.save(task);
        }
    }

    @RequestMapping(value = "/{taskId}/addParticipation/{questId}/{developerId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto addParticipation(@PathVariable(value = "taskId") final Long taskId,
            @PathVariable(value = "questId") final Long questId,
            @PathVariable(value = "developerId") final Long developerId) {
        Task task = this.taskRepository.findOne(taskId);
        final Participation participation = participationService.findParticipationByQuestIdAndDeveloperId(questId,
                developerId);
        if (task != null && participation != null) {
            task.setParticipation(participation);
            task.setStatus(TaskStates.PROCESSED);
            task = this.taskRepository.save(task);
        }
        return toTaskDto(task);
    }

    @CrossOrigin
    @RequestMapping(value = "/{taskId}/deleteParticipation", method = RequestMethod.DELETE)
    public void deleteParticipation(@PathVariable(value = "taskId") final Long taskId) {
        final Task task = this.taskRepository.findOne(taskId);
        if (task != null) {
            task.setParticipation(null);
            task.setStatus(TaskStates.OPEN);
            this.taskRepository.save(task);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/updateStandardTasks/{worldId}", method = RequestMethod.GET)
    public List<TaskDto> updateStandardTasksForWorld(@PathVariable(value = "worldId") final Long worldId) {
        final World world = worldRepository.findOne(worldId);
        List<TaskDto> taskDtos = null;
        if (world != null) {
            standardTaskService.updateStandardTasks(world);
            final List<Task> savedTasks = taskRepository.findAll();
            taskDtos = savedTasks.stream().map(TaskDto::toTaskDto).collect(Collectors.toList());
        }
        return taskDtos;
    }

}
