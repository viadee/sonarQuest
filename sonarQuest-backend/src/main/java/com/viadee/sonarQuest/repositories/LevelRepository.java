package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.Level;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LevelRepository extends CrudRepository<Level,Long> {

    Level findById(Long id);

    List<Level> findAll();

    Level findFirstByMinIsLessThanEqualAndMaxIsGreaterThanEqual(Long min,Long max);

    Level findFirstByMaxIsLessThanOrderByMinDesc(Long xp);


}
