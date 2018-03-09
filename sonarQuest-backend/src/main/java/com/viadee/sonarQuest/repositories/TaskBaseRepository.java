package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TaskBaseRepository<T extends Task> extends CrudRepository<T,Long> {
}
