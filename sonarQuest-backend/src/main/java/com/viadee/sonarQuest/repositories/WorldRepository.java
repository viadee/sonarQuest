package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.World;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorldRepository extends CrudRepository<World,Long> {

    List<World> findAll();

    World findByProject(String project);
    
    World findById(long id);
}
