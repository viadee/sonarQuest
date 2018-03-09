package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.StandardTask;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface StandardTaskRepository extends TaskBaseRepository<StandardTask>{

    List<StandardTask> findAll();

    StandardTask findByKey(String key);
}
