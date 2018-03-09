package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.AvatarRace;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvatarRaceRepository extends CrudRepository<AvatarRace,Long> {

    List<AvatarRace> findAll();
}
