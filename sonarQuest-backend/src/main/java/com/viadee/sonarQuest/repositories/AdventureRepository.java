package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.World;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdventureRepository extends CrudRepository<Adventure,Long> {

    List<Adventure> findAll();
    
    List<Adventure> findByWorld(World world);

    List<Adventure> findByDevelopers(List<Developer> developers);
    
    List<Adventure> findByDevelopersAndWorld(List<Developer> developers, World world);
}
