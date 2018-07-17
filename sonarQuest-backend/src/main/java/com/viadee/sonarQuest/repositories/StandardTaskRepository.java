package com.viadee.sonarQuest.repositories;

import java.util.List;

import javax.transaction.Transactional;

import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.World;

@Transactional
public interface StandardTaskRepository extends TaskBaseRepository<StandardTask> {
    @Deprecated List<StandardTask> findAll();

    List<StandardTask> findAllByOrderByScoreDesc();

    StandardTask findByKey(String key);

    List<StandardTask> findByWorldOrderByScoreDesc(World world);
}
