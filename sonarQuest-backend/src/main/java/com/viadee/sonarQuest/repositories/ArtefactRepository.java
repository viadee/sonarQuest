package com.viadee.sonarQuest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarQuest.entities.Artefact;

public interface ArtefactRepository extends JpaRepository<Artefact, Long> {

    @Override
    List<Artefact> findAll();

    List<Artefact> findByQuantityIsGreaterThanEqual(Long min);
}
