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

	public List<Task> getFreeTasksForWorld(World world) {
		return taskRepository.findByWorldAndStatusAndQuestIsNull(world, SonarQuestStatus.OPEN);
	}
	
	public Task save(Task task) {
		return taskRepository.save(task);
	}
    
	public Task find(Long id) {
		return taskRepository.findById(id);
	}    
	
	public void delete(Task task) {
		taskRepository.delete(task);	
	}

	public List<Task> findAll() {
		return taskRepository.findAll();
	}
	
}
