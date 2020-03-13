package com.viadee.sonarquest.services;

import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.*;
import com.viadee.sonarquest.interfaces.QuestSuggestion;
import com.viadee.sonarquest.repositories.ParticipationRepository;
import com.viadee.sonarquest.repositories.QuestRepository;
import com.viadee.sonarquest.repositories.TaskRepository;
import com.viadee.sonarquest.rules.SonarQuestTaskStatus;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuestService implements QuestSuggestion {

    private final QuestRepository questRepository;

    private final TaskRepository taskRepository;

    private final GratificationService gratificationService;

    private final ParticipationRepository participationRepository;

    private WorldService worldService;

    private UserService userService;

    private AdventureService adventureService;
    
    private final Random random = new Random();


    public QuestService(QuestRepository questRepository, TaskRepository taskRepository, GratificationService gratificationService, ParticipationRepository participationRepository, WorldService worldService, UserService userService, AdventureService adventureService) {
        this.questRepository = questRepository;
        this.taskRepository = taskRepository;
        this.gratificationService = gratificationService;
        this.participationRepository = participationRepository;
        this.worldService = worldService;
        this.userService = userService;
        this.adventureService = adventureService;
    }

    public Quest findById(final Long questId) {
        return questRepository.findById(questId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Task> suggestTasksWithApproxGoldAmount(final World world, final Long goldApprox) {
        final List<Task> freeTasks = taskRepository.findByWorldAndStatus(world, SonarQuestTaskStatus.OPEN);
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
        final List<Task> freeTasks = taskRepository.findByWorldAndStatus(world, SonarQuestTaskStatus.OPEN);
        final List<Task> suggestedTasks = new ArrayList<>();
        while ((totalXpAmountOfTaskList(suggestedTasks) < xpApprox) && (!freeTasks.isEmpty())) {
            final Task selectedTask = selectRandomTask(freeTasks);
            suggestedTasks.add(selectedTask);
            freeTasks.remove(selectedTask);
        }
        return suggestedTasks;
    }

    private Task selectRandomTask(final List<Task> taskList) {

        final int randomIndex = random.nextInt(taskList.size());
        return taskList.get(randomIndex);
    }

    private Long totalGoldAmountOfTaskList(final List<Task> taskList) {
        return taskList.stream().mapToLong(Task::getGold).sum();
    }

    private Long totalXpAmountOfTaskList(final List<Task> taskList) {
        return taskList.stream().mapToLong(Task::getXp).sum();
    }

    @Transactional
    public void checkAllQuestsAndCloseItWhenAllOfItsTasksAreClosed() {
        final List<Quest> quests = questRepository.findAll();
        quests.forEach(this::closeQuestWhenAllOfItsTasksAreClosed);
    }

    @Transactional // Quest updates are not to be mixed
    public synchronized void closeQuestWhenAllOfItsTasksAreClosed(final Quest quest) {
        final List<Task> tasks = quest.getTasks();
        final List<Task> solvedTasks = taskRepository.findByQuestAndStatus(quest, SonarQuestTaskStatus.SOLVED);
        final List<Task> closedTasks = taskRepository.findByQuestAndStatus(quest, SonarQuestTaskStatus.CLOSED);
        if (tasks.size() == (solvedTasks.size() + closedTasks.size())) {
            gratificationService.rewardUsersForSolvingQuest(quest);
            quest.setStatus(QuestState.SOLVED);
            questRepository.save(quest);
        }
    }

    @Transactional
    public List<List<Quest>> getAllQuestsForWorldAndUser(final Long worldId, final User user) {
        final World world = worldService.findById(worldId);
        final List<Participation> participations = participationRepository.findByUser(user);
        final List<Quest> participatedQuests = participations.stream().map(Participation::getQuest)
                .filter(quest -> quest.getWorld().equals(world)).collect(Collectors.toList());
        final List<Quest> allQuestsForWorld = questRepository.findByWorld(world);
        final List<List<Quest>> result = new ArrayList<>();
        allQuestsForWorld.removeAll(participatedQuests);
        result.add(participatedQuests);
        result.add(allQuestsForWorld);
        return result;
    }

    @Transactional
    public List<Quest> getAllQuests() {
        return questRepository.findAll();
    }

    public List<Quest> getAllQuestsForWorld(final Long worldId) {
        final World world = worldService.findById(worldId);
        return questRepository.findByWorld(world);
    }

    public Quest createQuest(Quest quest, String username) {
        final User user = userService.findByUsername(username);
        quest.setStartdate(new Date(System.currentTimeMillis()));
        quest.setStatus(QuestState.OPEN);
        quest.setCreatorName(user.getUsername());
        return questRepository.save(quest);
    }

    public Quest updateQuest(Long questId, Quest questInput) {
        Quest quest = questRepository.findById(questId).orElseThrow(ResourceNotFoundException::new);
        quest.setTitle(questInput.getTitle());
        quest.setGold(questInput.getGold());
        quest.setXp(questInput.getXp());
        quest.setStory(questInput.getStory());
        quest.setImage(questInput.getImage());
        quest.setVisible(questInput.getVisible());
        quest.setCreatorName(questInput.getCreatorName());
        return questRepository.save(quest);
    }

    public void deleteById(Long questId) {
        questRepository.deleteById(questId);
    }

    public Quest solveQuest(Long questId) {
        Quest quest = questRepository.findById(questId).orElseThrow(ResourceNotFoundException::new);
        quest.setEnddate(new Date(System.currentTimeMillis()));
        quest.setStatus(QuestState.SOLVED);
        return questRepository.save(quest);
    }

    public Quest addQuestToWorld(final Long questId, final Long worldId) {
        final Quest quest = findById(questId);
        final World world = worldService.findById(worldId);
        quest.setWorld(world);
        return questRepository.save(quest);
    }

    public void removeQuestFromWorld(final Long questId) {
        Quest quest = findById(questId);
        quest.setWorld(null);
    }

    public Quest addQuestToAdventure(final Long questId, final Long adventureId) {
        final Quest quest = findById(questId);
        final Adventure adventure = adventureService.findById(adventureId);
        quest.setAdventure(adventure);
        return questRepository.save(quest);
    }

    public void removeQuestFromAdventure(final Long questId) {
        Quest quest = findById(questId);
        quest.setAdventure(null);
    }
}
