package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.Developer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DeveloperRepository extends CrudRepository<Developer, Long> {

    List<Developer> findAll();

    Developer findById(Long id);

    Developer findByUsername(String username);
    
    List<Developer> findByDeleted(Boolean deleted);

}
