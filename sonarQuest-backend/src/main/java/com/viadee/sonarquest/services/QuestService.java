package com.viadee.sonarquest.services;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.interfaces.QuestSuggestion;
import com.viadee.sonarquest.repositories.ParticipationRepository;
import com.viadee.sonarquest.repositories.QuestRepository;
import com.viadee.sonarquest.repositories.TaskRepository;
import com.viadee.sonarquest.rules.SonarQuestStatus;
import com.viadee.sonarquest.util.ProgressDTO;

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
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private UserService userService;

    final Random random = new Random();
    
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
    
    /**
     * Create an new Quest with status open
     * @param principal
     * @param questDto
     * @return
     */
    public Quest createQuest(final Principal principal, final Quest questDto) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        questDto.setStartdate(new Date(System.currentTimeMillis()));
        questDto.setStatus(QuestState.OPEN);
        questDto.setCreatorName(user.getUsername());
        return questRepository.save(questDto);
    }

    @Transactional // Quest updates are not to be mixed
    public synchronized void updateQuest(final Quest quest) {
        final List<Task> tasks = quest.getTasks();
        final List<Task> solvedTasks = taskRepository.findByQuestAndStatus(quest, SonarQuestStatus.SOLVED);
        final List<Task> closedTasks = taskRepository.findByQuestAndStatus(quest, SonarQuestStatus.CLOSED);
        if (tasks.size() == (solvedTasks.size() + closedTasks.size())) {
            gratificationService.rewardUsersForSolvingQuest(quest);
            quest.setStatus(QuestState.SOLVED);
            questRepository.save(quest);
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
    
    
    /**
     * Save an quest
     * @param quest to save
     * @return Quest
     */
    public synchronized Quest saveQuest(Quest quest) {
    	return questRepository.save(quest);
    }
    
    // TODO refactoring
	public ProgressDTO calculateQuestProgress(Quest quest) {
		if(quest == null) {
			return new ProgressDTO(0, 0);
		}
		long openTaks = quest.getTasks().stream().filter(task -> SonarQuestStatus.OPEN.equals(task.getStatus())).count();
		int taskSize = quest.getTasks().size();
		
		ProgressDTO progressDTO = new ProgressDTO(taskSize, openTaks);
		progressDTO.setCalculatedProgress(Math.round(100- (100*(double)openTaks/taskSize)));
		return progressDTO;
	}
    
    
    /**
     * Calculate quest progress from closed tasks
     * 
     * @param questId to find quest
     * @return rounded value
     */
	public double calculateQuestProgress(Long questId) {
		Quest quest = findById(questId);
		if(quest == null) {
			return 0;
		}
			
		long openTaks = quest.getTasks().stream().filter(task -> SonarQuestStatus.OPEN.equals(task.getStatus())).count();
		int taskSize = quest.getTasks().size();
		
		return Math.round(100- (100*(double)openTaks/taskSize));
	}
}
