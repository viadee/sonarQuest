package com.viadee.sonarQuest.repositories;

import java.util.List;

import javax.transaction.Transactional;

import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.World;

@Transactional
public interface TaskRepository extends TaskBaseRepository<Task> {

    @Override
    List<Task> findAll();

    @Override
    List<Task> findAll(Iterable<Long> iterable);

    Task findById(Long id);

    List<Task> findByQuestAndStatus(Quest quest, String status);

    List<Task> findByWorldAndStatus(World world, String status);
}
