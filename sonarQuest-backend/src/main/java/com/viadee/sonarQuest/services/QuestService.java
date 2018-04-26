package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.constants.QuestStates;
import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.entities.*;
import com.viadee.sonarQuest.interfaces.QuestSuggestion;
import com.viadee.sonarQuest.repositories.ParticipationRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuestService implements QuestSuggestion {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GratificationService gratificationService;

    @Autowired
    private ParticipationRepository participationRepository;

    public List<Task> suggestTasksWithApproxGoldAmount(World world, Long goldApprox) {
        List<Task> freeTasks = taskRepository.findByWorldAndStatus(world, TaskStates.CREATED);
        List<Task> suggestedTasks = new ArrayList<>();
        while ((totalGoldAmountOfTaskList(suggestedTasks) < goldApprox) && (!freeTasks.isEmpty())) {
            Task selectedTask = selectRandomTask(freeTasks);
            suggestedTasks.add(selectedTask);
            freeTasks.remove(selectedTask);
        }
        return suggestedTasks;
    }

    public List<Task> suggestTasksWithApproxXpAmount(World world, Long xpApprox) {
        List<Task> freeTasks = taskRepository.findByWorldAndStatus(world, TaskStates.CREATED);
        List<Task> suggestedTasks = new ArrayList<>();
        while ((totalXpAmountOfTaskList(suggestedTasks) < xpApprox) && (!freeTasks.isEmpty())) {
            Task selectedTask = selectRandomTask(freeTasks);
            suggestedTasks.add(selectedTask);
            freeTasks.remove(selectedTask);
        }
        return suggestedTasks;
    }

    private Task selectRandomTask(List<Task> taskList) {
        Random random = new Random();
        Integer randomIndex = random.nextInt(taskList.size());
        return taskList.get(randomIndex);
    }

    private Long totalGoldAmountOfTaskList(List<Task> taskList) {
        return taskList.stream().mapToLong(Task::getGold).sum();
    }

    private Long totalXpAmountOfTaskList(List<Task> taskList) {
        return taskList.stream().mapToLong(Task::getXp).sum();
    }

    public void updateQuests() {
        List<Quest> quests = questRepository.findAll();
        quests.forEach(this::updateQuest);
    }

    public void updateQuest(Quest quest) {
        List<Task> tasks = quest.getTasks();
        List<Task> solvedTasks = taskRepository.findByQuestAndStatus(quest, TaskStates.SOLVED);
        List<Task> closedTasks = taskRepository.findByQuestAndStatus(quest, TaskStates.CLOSED);
        if (tasks.size() == (solvedTasks.size() + closedTasks.size())) {
            quest.setStatus(QuestStates.SOLVED);
            questRepository.save(quest);
            gratificationService.rewardDevelopersForSolvingQuest(quest);
        }
    }

    public List<List<Quest>> getAllQuestsForWorldAndDeveloper(World world, Developer developer) {
        List<Participation> participations = participationRepository.findByDeveloper(developer);
        List<Quest> participatedQuests = participations.stream().map(Participation::getQuest).filter(quest -> quest.getWorld().equals(world)).collect(Collectors.toList());
        List<Quest> allQuestsForWorld = questRepository.findByWorld(world);
        List<List<Quest>> result = new ArrayList<>();
        List<Quest> freeQuests = allQuestsForWorld;
        freeQuests.removeAll(participatedQuests);
        result.add(participatedQuests);
        result.add(freeQuests);
        return result;
    }
}

