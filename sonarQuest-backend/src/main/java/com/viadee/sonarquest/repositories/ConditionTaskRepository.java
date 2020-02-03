package com.viadee.sonarquest.repositories;

import java.util.List;

import javax.transaction.Transactional;

import com.viadee.sonarquest.entities.ConditionTask;
import com.viadee.sonarquest.entities.World;

@Transactional
public interface ConditionTaskRepository extends TaskBaseRepository<ConditionTask>{

    List<ConditionTask> findAll();

    ConditionTask findByKey(String key);
    
    List<ConditionTask> findByWorld(World world);
    
}
