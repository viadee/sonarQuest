package com.viadee.sonarquest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.entities.Artefact;

public interface ArtefactRepository extends JpaRepository<Artefact, Long> {

    @Override
    List<Artefact> findAll();

    List<Artefact> findByQuantityIsGreaterThanEqual(Long min);
}
