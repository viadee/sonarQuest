package com.viadee.sonarQuest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.constants.QuestState;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.interfaces.QuestSuggestion;
import com.viadee.sonarQuest.repositories.ParticipationRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.TaskRepository;
import com.viadee.sonarQuest.rules.SonarQuestStatus;

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

    public Quest findById(final Long questId) {
        return questRepository.findOne(questId);
    }

    @Override
    public List<Task> suggestTasksWithApproxGoldAmount(final World world, final Long goldApprox) {
        final List<Task> freeTasks = taskRepository.findByWorldAndStatus(world, SonarQuestStatus.OPEN);
        final List<Task> suggestedTasks = new ArrayList<>();
        while ((totalGoldAmountOfTaskList(suggestedTasks) < goldApprox) && (!freeTasks.isEmpty())) {
            final Task selectedTask = selectRandomTask(freeTasks);
            suggestedTasks.add(selectedTask);
            freeTasks.remove(selectedTask);
        }
        return suggestedTasks;
    }

    @Override
    public List<Task> suggestTasksWithApproxXpAmount(final World world, final Long xpApprox) {
        final List<Task> freeTasks = taskRepository.findByWorldAndStatus(world, SonarQuestStatus.OPEN);
        final List<Task> suggestedTasks = new ArrayList<>();
        while ((totalXpAmountOfTaskList(suggestedTasks) < xpApprox) && (!freeTasks.isEmpty())) {
            final Task selectedTask = selectRandomTask(freeTasks);
            suggestedTasks.add(selectedTask);
            freeTasks.remove(selectedTask);
        }
        return suggestedTasks;
    }

    private Task selectRandomTask(final List<Task> taskList) {
        final Random random = new Random();
        final Integer randomIndex = random.nextInt(taskList.size());
        return taskList.get(randomIndex);
    }

    private Long totalGoldAmountOfTaskList(final List<Task> taskList) {
        return taskList.stream().mapToLong(Task::getGold).sum();
    }

    private Long totalXpAmountOfTaskList(final List<Task> taskList) {
        return taskList.stream().mapToLong(Task::getXp).sum();
    }

    public void updateQuests() {
        final List<Quest> quests = questRepository.findAll();
        quests.forEach(this::updateQuest);
    }

    public void updateQuest(final Quest quest) {
        final List<Task> tasks = quest.getTasks();
        final List<Task> solvedTasks = taskRepository.findByQuestAndStatus(quest, SonarQuestStatus.SOLVED);
        final List<Task> closedTasks = taskRepository.findByQuestAndStatus(quest, SonarQuestStatus.CLOSED);
        if (tasks.size() == (solvedTasks.size() + closedTasks.size())) {
            quest.setStatus(QuestState.SOLVED);
            questRepository.save(quest);
            gratificationService.rewardUsersForSolvingQuest(quest);
        }
    }

    public List<List<Quest>> getAllQuestsForWorldAndUser(final World world, final User developer) {
        final List<Participation> participations = participationRepository.findByUser(developer);
        final List<Quest> participatedQuests = participations.stream().map(Participation::getQuest)
                .filter(quest -> quest.getWorld().equals(world)).collect(Collectors.toList());
        final List<Quest> allQuestsForWorld = questRepository.findByWorld(world);
        final List<List<Quest>> result = new ArrayList<>();
        final List<Quest> freeQuests = allQuestsForWorld;
        freeQuests.removeAll(participatedQuests);
        result.add(participatedQuests);
        result.add(freeQuests);
        return result;
    }

}
