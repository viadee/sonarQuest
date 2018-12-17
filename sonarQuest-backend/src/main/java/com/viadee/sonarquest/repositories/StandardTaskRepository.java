package com.viadee.sonarquest.repositories;

import javax.transaction.Transactional;

import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.World;

import java.util.List;

@Transactional
public interface StandardTaskRepository extends TaskBaseRepository<StandardTask>{

    List<StandardTask> findAll();

    StandardTask findByKey(String key);
    
    List<StandardTask> findByWorld(World world);
}
