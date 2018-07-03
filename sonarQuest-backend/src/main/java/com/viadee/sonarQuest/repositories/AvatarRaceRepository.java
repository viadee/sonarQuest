package com.viadee.sonarQuest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarQuest.entities.AvatarRace;

public interface AvatarRaceRepository extends CrudRepository<AvatarRace,Long> {

    List<AvatarRace> findAll();
    
    AvatarRace findByName(String name);
}
