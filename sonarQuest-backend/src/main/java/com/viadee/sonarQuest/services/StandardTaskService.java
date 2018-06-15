package com.viadee.sonarQuest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void updateStandardTasks(final World world) {
        final List<StandardTask> externalStandardTasks = externalRessourceService
                .generateStandardTasksFromSonarQubeIssuesForWorld(world);
        externalStandardTasks.forEach(this::updateStandardTask);
        questService.updateQuests();
        adventureService.updateAdventures();
    }

    public StandardTask updateStandardTask(final StandardTask task) {
        final StandardTask lastState = standardTaskRepository.findByKey(task.getKey());
        if (lastState != null) {
            final SonarQuestStatus newStatus = SonarQuestStatus.fromStatusText(task.getStatus());
            final SonarQuestStatus oldStatus = SonarQuestStatus.fromStatusText(lastState.getStatus());
            if (oldStatus != SonarQuestStatus.CREATED) {
                lastState.setStatus(newStatus.getText());
                standardTaskRepository.saveAndFlush(lastState);
            }
            if (newStatus == SonarQuestStatus.SOLVED && !(oldStatus == SonarQuestStatus.SOLVED)) {
                gratificationService.rewardUserForSolvingTask(lastState);
            }
        } else {
            task.setStatus(SonarQuestStatus.CREATED.getText());
            standardTaskRepository.saveAndFlush(task);
        }
        return task;
    }

    public void setExternalRessourceService(final ExternalRessourceService externalRessourceService) {
        this.externalRessourceService = externalRessourceService;
    }

    public void save(final StandardTask standardTask) {

        final World world = worldRepository.findByProject(standardTask.getWorld().getProject());

        final StandardTask st = new StandardTask(
                standardTask.getTitle(),
                SonarQuestStatus.CREATED.getText(),
                standardTask.getGold(),
                standardTask.getXp(),
                standardTask.getQuest(),
                world, null, null, null, null, null, null);

        standardTaskRepository.save(st);
    }

}
