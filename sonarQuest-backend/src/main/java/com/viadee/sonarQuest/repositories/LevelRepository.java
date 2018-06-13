package com.viadee.sonarQuest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarQuest.entities.Level;

public interface LevelRepository extends JpaRepository<Level, Long> {

    Level findById(Long id);

    @Override
    List<Level> findAll();

    Level findFirstByMinIsLessThanEqualAndMaxIsGreaterThanEqual(Long min, Long max);

    Level findFirstByMaxIsLessThanOrderByMinDesc(Long xp);
}
