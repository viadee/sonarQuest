package com.viadee.sonarQuest.repositories;

import java.util.List;

import javax.transaction.Transactional;

import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.rules.SonarQuestStatus;

@Transactional
public interface TaskRepository extends TaskBaseRepository<Task> {

    @Override
    List<Task> findAll();

    List<Task> findAllByOrderByScoreDesc();

    @Override
    List<Task> findAll(Iterable<Long> iterable);

    Task findById(Long id);

    List<Task> findByQuestAndStatus(Quest quest, String status);

    List<Task> findByWorldAndStatus(World world, String status);

	List<Task> findByWorldAndStatusAndQuestIsNullOrderByScoreDesc(World world, SonarQuestStatus open);
}
