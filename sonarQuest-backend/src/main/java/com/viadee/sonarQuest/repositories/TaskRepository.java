package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.World;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TaskRepository extends TaskBaseRepository<Task>{

     List<Task> findAll();

     List<Task> findAll(Iterable<Long> iterable);

     Task findById(long id);

     List<Task> findByQuestAndStatus(Quest quest, String status);

     List<Task> findByWorldAndStatus(World world, String status);
}
