package com.viadee.sonarquest.repositories;

import javax.transaction.Transactional;

import com.viadee.sonarquest.entities.SpecialTask;
import com.viadee.sonarquest.entities.World;

import java.util.List;

@Transactional
public interface SpecialTaskRepository extends TaskBaseRepository<SpecialTask> {

    List<SpecialTask> findAll();

    List<SpecialTask> findByStatus(String status);
    
    List<SpecialTask> findByWorld(World world);
}
