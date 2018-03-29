package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.World;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface StandardTaskRepository extends TaskBaseRepository<StandardTask>{

    List<StandardTask> findAll();

    StandardTask findByKey(String key);
    
    List<StandardTask> findByWorld(World world);
}
