package com.viadee.sonarQuest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.repositories.LevelRepository;

@Service
public class LevelService {

    private LevelRepository levelRepository;

    @Autowired
    public LevelService(final LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public Level getLevelByUserXp(final Long xp) {
        Level level = levelRepository.findFirstByMinIsLessThanEqualAndMaxIsGreaterThanEqual(xp, xp);

        if (level == null) {
            level = levelRepository.findFirstByMaxIsLessThanOrderByMinDesc(xp);
        }

        return level;

    }

}
