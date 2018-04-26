package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.StandardTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StandardTaskService {

    @Autowired
    private ExternalRessourceService externalRessourceService;

    @Autowired
    private GratificationService gratificationService;

    @Autowired
    private StandardTaskRepository standardTaskRepository;

    @Autowired
    private QuestService questService;

    @Autowired
    private AdventureService adventureService;


    public void updateStandardTasks(World world) {
        List<StandardTask> externalStandardTasks = externalRessourceService.generateStandardTasksFromSonarQubeIssuesForWorld(world);
        externalStandardTasks.forEach(this::updateStandardTask);
        questService.updateQuests();
        adventureService.updateAdventures();
    }

    public void updateStandardTask(StandardTask externalStandardTask) {
        StandardTask foundInternalStandardTask = standardTaskRepository.findByKey(externalStandardTask.getKey());
        if (foundInternalStandardTask != null) {
            String newStatus = externalStandardTask.getStatus();
            String oldStatus = foundInternalStandardTask.getStatus();
            if(!Objects.equals(TaskStates.CREATED, oldStatus)){
                foundInternalStandardTask.setStatus(newStatus);
                standardTaskRepository.save(foundInternalStandardTask);
            }
            if ((Objects.equals(newStatus, TaskStates.SOLVED)) && (!Objects.equals(oldStatus, TaskStates.SOLVED))) {
                gratificationService.rewardDeveloperForSolvingTask(foundInternalStandardTask);
            }
        } else {
            externalStandardTask.setStatus(TaskStates.CREATED);
            standardTaskRepository.save(externalStandardTask);
        }
    }

    public void setExternalRessourceService(ExternalRessourceService externalRessourceService) {
        this.externalRessourceService = externalRessourceService;
    }
}
