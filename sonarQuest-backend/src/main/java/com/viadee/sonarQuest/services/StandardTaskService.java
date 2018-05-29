package com.viadee.sonarQuest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.dtos.StandardTaskDto;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.StandardTaskRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.rules.SonarQuestStatus;

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

    @Autowired
    private WorldRepository worldRepository;

    public void updateStandardTasks(World world) {
        List<StandardTask> externalStandardTasks = externalRessourceService
                .generateStandardTasksFromSonarQubeIssuesForWorld(world);
        externalStandardTasks.forEach(this::updateStandardTask);
        questService.updateQuests();
        adventureService.updateAdventures();
    }

    public void updateStandardTask(StandardTask currentState) {
        StandardTask lastState = standardTaskRepository.findByKey(currentState.getKey());
        if (lastState != null) {
            SonarQuestStatus newStatus = SonarQuestStatus.fromStatusText(currentState.getStatus());
            SonarQuestStatus oldStatus = SonarQuestStatus.fromStatusText(lastState.getStatus());
            if (oldStatus != SonarQuestStatus.CREATED) {
                lastState.setStatus(newStatus.getText());
                standardTaskRepository.save(lastState);
            }
            if (newStatus == SonarQuestStatus.SOLVED && !(oldStatus == SonarQuestStatus.SOLVED)) {
                gratificationService.rewardDeveloperForSolvingTask(lastState);
            }
        } else {
            currentState.setStatus(SonarQuestStatus.CREATED.getText());
            standardTaskRepository.save(currentState);
        }
    }

    public void setExternalRessourceService(ExternalRessourceService externalRessourceService) {
        this.externalRessourceService = externalRessourceService;
    }

    public void saveDto(final StandardTaskDto standardTaskDto) {

        final World world = worldRepository.findByProject(standardTaskDto.getWorld().getProject());

        final StandardTask st = new StandardTask(
                standardTaskDto.getTitle(),
                SonarQuestStatus.CREATED.getText(),
                standardTaskDto.getGold(),
                standardTaskDto.getXp(),
                standardTaskDto.getQuest(),
                world, null, null, null, null, null, null);

        standardTaskRepository.save(st);
    }

}
