package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.SpecialTask;
import com.viadee.sonarQuest.entities.World;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SpecialTaskRepository extends TaskBaseRepository<SpecialTask> {

    List<SpecialTask> findAll();

    List<SpecialTask> findByStatus(String status);
    
    List<SpecialTask> findByWorld(World world);
}
