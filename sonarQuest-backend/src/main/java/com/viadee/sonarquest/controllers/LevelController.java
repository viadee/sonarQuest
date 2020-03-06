package com.viadee.sonarquest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.Level;
import com.viadee.sonarquest.services.LevelService;

@RestController
@RequestMapping("/level")
public class LevelController {

    private final LevelService levelService;

    public LevelController(final LevelService levelService) {
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
