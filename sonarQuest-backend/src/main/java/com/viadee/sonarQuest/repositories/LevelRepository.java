package com.viadee.sonarQuest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarQuest.entities.Level;

public interface LevelRepository extends JpaRepository<Level, Long> {

    Level findById(Long id);

	Level findFirstByMinXpIsLessThanEqualOrderByLevelDesc(Long xp, Long xp2);
}
