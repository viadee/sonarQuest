package com.viadee.sonarquest.repositories;

import java.util.List;

import javax.transaction.Transactional;

import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.rules.SonarQuestTaskStatus;

@Transactional
public interface TaskRepository extends TaskBaseRepository<Task> {

    @Override
    List<Task> findAll();

    List<Task> findByQuestAndStatus(Quest quest, SonarQuestTaskStatus status);

    List<Task> findByWorldAndStatus(World world, SonarQuestTaskStatus status);

    List<Task> findByWorldAndStatusAndQuestIsNull(World world, SonarQuestTaskStatus status);
}
