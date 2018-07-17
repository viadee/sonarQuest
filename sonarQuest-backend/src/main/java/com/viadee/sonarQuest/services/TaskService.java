package com.viadee.sonarQuest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.TaskRepository;
import com.viadee.sonarQuest.rules.SonarQuestStatus;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GratificationService gratificationService;

    @Autowired
    private QuestService questService;

    @Autowired
    private AdventureService adventureService;

    public List<Task> getFreeTasksForWorld(final World world) {
        return taskRepository.findByWorldAndStatusAndQuestIsNullOrderByScoreDesc(world, SonarQuestStatus.OPEN);
    }

    public Task save(final Task task) {
        return taskRepository.save(task);
    }

    public Task find(final Long id) {
        return taskRepository.findById(id);
    }

    public void delete(final Task task) {
        taskRepository.delete(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAllByOrderByScoreDesc();
    }

    public void solveTaskManually(final Task task) {
        if (task != null && !(SonarQuestStatus.SOLVED.equals(task.getStatus()))) {
            task.setStatus(SonarQuestStatus.SOLVED);
            save(task);
            gratificationService.rewardUserForSolvingTask(task);
            questService.updateQuest(task.getQuest());
            adventureService.updateAdventure(task.getQuest().getAdventure());
        }
    }
}
