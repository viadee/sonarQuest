package com.viadee.sonarquest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.viadee.sonarquest.entities.Task;

@NoRepositoryBean
public interface TaskBaseRepository<T extends Task> extends JpaRepository<T, Long> {
}
