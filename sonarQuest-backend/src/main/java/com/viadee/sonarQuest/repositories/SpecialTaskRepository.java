package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.SpecialTask;
import com.viadee.sonarQuest.entities.StandardTask;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SpecialTaskRepository extends TaskBaseRepository<SpecialTask> {

    List<SpecialTask> findAll();

    List<SpecialTask> findByStatus(String status);
}
