package com.viadee.sonarquest.repositories;

import java.util.List;

import javax.transaction.Transactional;

import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.rules.SonarQuestStatus;

@Transactional
public interface StandardTaskRepository extends TaskBaseRepository<StandardTask>{

    List<StandardTask> findAll();

    StandardTask findByKey(String key);
    
    List<StandardTask> findByWorld(World world);
    
    List<StandardTask> findByWorldAndStatusAndQuestIsNull(World world, SonarQuestStatus status);
}
