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

    @Override
    List<Task> findAll(Iterable<Long> iterable);

    Task findById(Long id);

    List<Task> findByQuestAndStatus(Quest quest, SonarQuestStatus status);

    List<Task> findByWorldAndStatus(World world, SonarQuestStatus status);

    List<Task> findByWorldAndStatusAndQuestIsNull(World world, SonarQuestStatus status);
}
