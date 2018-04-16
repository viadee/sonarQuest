package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.Artefact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtefactRepository extends CrudRepository<Artefact,Long> {

    List<Artefact> findAll();
    
    List<Artefact> findByQuantityIsGreaterThanEqual(Long min);
}
