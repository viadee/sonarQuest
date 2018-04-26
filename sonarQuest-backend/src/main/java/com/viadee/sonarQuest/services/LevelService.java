package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelService {

    private LevelRepository levelRepository;

    @Autowired
    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public Level getLevelByDeveloperXp(Long xp) {
        Level level = levelRepository.findFirstByMinIsLessThanEqualAndMaxIsGreaterThanEqual(xp, xp);

        if (level == null) {
            level = levelRepository.findFirstByMaxIsLessThanOrderByMinDesc(xp);
        }

        return level;

    }
    
}
