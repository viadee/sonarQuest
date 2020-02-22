package com.viadee.sonarquest.controllers;

import java.util.List;

import com.viadee.sonarquest.services.LevelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.Level;
import com.viadee.sonarquest.repositories.LevelRepository;

@RestController
@RequestMapping("/level")
public class LevelController {

    private LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping
    public List<Level> getAllLevels() {
        return levelService.getAllLevels();
    }

    @GetMapping(value = "/{id}")
    public Level getLevelById(@PathVariable(value = "id") final Long levelId) {
        return levelService.findById(levelId);
    }

}
