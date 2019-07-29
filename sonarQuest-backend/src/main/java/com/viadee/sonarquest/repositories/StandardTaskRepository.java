package com.viadee.sonarquest.repositories;

import javax.transaction.Transactional;

import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.rules.SonarQuestStatus;

import java.util.List;

@Transactional
public interface StandardTaskRepository extends TaskBaseRepository<StandardTask>{

    List<StandardTask> findAll();

    StandardTask findByKey(String key);
    
    List<StandardTask> findByWorld(World world);

    List<StandardTask> findByWorldAndStatusAndQuestIsNull(World world, SonarQuestStatus status);
}
