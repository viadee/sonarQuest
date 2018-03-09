package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.dtos.DeveloperDto;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.LevelRepository;
import com.viadee.sonarQuest.services.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/developer")
public class DeveloperController {

    private DeveloperRepository developerRepository;

    private LevelRepository levelRepository;

    private LevelService levelService;

    @Autowired
    public DeveloperController(DeveloperRepository developerRepository, LevelRepository levelRepository, LevelService levelService) {
        this.developerRepository = developerRepository;
        this.levelRepository = levelRepository;
        this.levelService = levelService;
    }

    /**
     * Get All Developers
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<DeveloperDto> getAllDevelopers() {
        return this.developerRepository.findAll().stream().map(developer -> toDeveloperDto(developer)).collect(Collectors.toList());
    }

    /**
     * Get a Developer by Id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeveloperDto getDeveloperByID(@PathVariable(value = "id") Long id) {
        Developer developer = this.developerRepository.findById(id);
        DeveloperDto developerDto = null;
        if (developer != null) {
            developerDto = this.toDeveloperDto(developer);
        }
        return developerDto;
    }


    /**
     * Creates a Developer from a DTO
     *
     * @param developerDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public DeveloperDto createDeveloper(@RequestBody DeveloperDto developerDto) {
        Developer developer;
        if (this.checkIfUsernameNotExists(developerDto)) {
            Level level1 = this.levelRepository.findById((long) 1);
            developer = this.developerRepository.save(new Developer(developerDto.getUsername(), (long) 0, (long) 0, level1, developerDto.getPicture(),developerDto.getAboutMe(),developerDto.getAvatarClass(),developerDto.getAvatarRace()));
        } else {
            developer = null;
        }
        return this.toDeveloperDto(developer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeveloperDto updateDeveloper(@PathVariable(value = "id") Long id, @RequestBody DeveloperDto developerDto) {
        Developer developer = developerRepository.findById(id);
        if (developer != null) {
            developer.setGold((developerDto.getGold()));
            developer.setXp(developerDto.getXp());
            developer.setLevel(this.levelService.getLevelByDeveloperXp(developer.getXp()));
            developer.setPicture(developerDto.getPicture());
            developer.setAboutMe(developerDto.getAboutMe());
            developer.setAvatarClass(developerDto.getAvatarClass());
            developer.setAvatarRace(developerDto.getAvatarRace());
            developer.setArtefacts(developerDto.getArtefacts());
            developer = this.developerRepository.save(developer);
        }
        return this.toDeveloperDto(developer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteDeveloper(@PathVariable(value = "id") Long id) {
        Developer developer = developerRepository.findById(id);
        if (developer != null) {
            this.developerRepository.delete(developer);
        }
    }


    /**
     * Checks if Developer username already exists
     *
     * @param developerDto
     * @return
     */
    private Boolean checkIfUsernameNotExists(DeveloperDto developerDto) {
        Developer developer = this.developerRepository.findByUsername(developerDto.getUsername());
        return developer == null;
    }

    /**
     * Convert Developer into DeveloperDTO
     *
     * @param developer
     * @return
     */
    private DeveloperDto toDeveloperDto(Developer developer) {
        DeveloperDto developerDto = null;
        if(developer != null){
            developerDto = new DeveloperDto(developer.getId(), developer.getUsername(), developer.getGold(), developer.getXp(), developer.getLevel(),developer.getPicture(),developer.getAboutMe(), developer.getAvatarClass(),developer.getAvatarRace(),developer.getArtefacts(),developer.getAdventures(),developer.getParticipations());
        }
        return developerDto;
    }
}
