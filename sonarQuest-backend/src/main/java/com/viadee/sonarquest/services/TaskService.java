package com.viadee.sonarquest.services;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.TaskRepository;
import com.viadee.sonarquest.rules.SonarQuestStatus;

@Service
public class TaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private GratificationService gratificationService;

	@Autowired
	private QuestService questService;

	@Autowired
	private AdventureService adventureService;

	public List<Task> getFreeTasksForWorld(final World world) {
		return taskRepository.findByWorldAndStatusAndQuestIsNull(world, SonarQuestStatus.OPEN);
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
		return taskRepository.findAll();
	}

	@Transactional
	public void solveTaskManually(final Task task) {
		if (task != null && task.getStatus() != SonarQuestStatus.SOLVED) {
			task.setStatus(SonarQuestStatus.SOLVED);
			task.setEnddate(new Date(System.currentTimeMillis()));
			save(task);
			gratificationService.rewardUserForSolvingTask(task);
			questService.updateQuest(task.getQuest());
			adventureService.updateAdventure(task.getQuest().getAdventure());
		}
	}

	@Transactional
	public void solveAllTasksInQuest(Quest quest) {
		if (quest != null && quest.getStatus() != QuestState.SOLVED) {
			LOGGER.info("Solving all tasks in quest with ID {}", quest.getId());
			List<Task> tasks = quest.getTasks();
			for (Task task : tasks) {
				gratificationService.rewardUserForSolvingTask(task);
				task.setStatus(SonarQuestStatus.SOLVED);
				task.setEnddate(new Date(System.currentTimeMillis()));
				save(task);
			}
			questService.updateQuest(quest);
			adventureService.updateAdventure(quest.getAdventure());
		}
	}
}
