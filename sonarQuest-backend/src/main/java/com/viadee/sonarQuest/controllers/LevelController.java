package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.dtos.LevelDto;
import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.repositories.LevelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/level")
public class LevelController {

    private LevelRepository levelRepository;

    public LevelController(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<LevelDto> getAllLevels() {
        return this.levelRepository.findAll().stream().map(this::toLevelDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public LevelDto getLevelById(@PathVariable(value = "id") Long id) {
        Level level = this.levelRepository.findById(id);
        return this.toLevelDto(level);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Level createLevel(@RequestBody LevelDto levelDto) {
        return this.levelRepository.save(new Level(levelDto.getMax(), levelDto.getMin()));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Level updateLevel(@PathVariable(value = "id") Long id, @RequestBody LevelDto levelDto) {
        Level level = this.levelRepository.findById(id);
        if (level != null) {
            level.setMin(levelDto.getMin());
            level.setMax(levelDto.getMax());
            level = this.levelRepository.save(level);
        }
        return level;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteLevel(@PathVariable(value = "id") Long id) {
        Level level = this.levelRepository.findById(id);
        if (level != null) {
            this.levelRepository.delete(level);
        }
    }

    private LevelDto toLevelDto(Level level) {
        LevelDto levelDto = null;
        if (level != null) {
            levelDto = new LevelDto(level.getId(), level.getMin(), level.getMax(), level.getDevelopers());
        }
        return levelDto;
    }
}
