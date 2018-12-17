package com.viadee.sonarquest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarquest.entities.AvatarRace;

public interface AvatarRaceRepository extends CrudRepository<AvatarRace,Long> {

    List<AvatarRace> findAll();
    
    AvatarRace findByName(String name);
}
