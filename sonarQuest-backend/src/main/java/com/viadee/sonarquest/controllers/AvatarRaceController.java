package com.viadee.sonarquest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.AvatarRace;
import com.viadee.sonarquest.repositories.AvatarRaceRepository;

@RestController
@RequestMapping("/avatarRace")
public class AvatarRaceController {

    @Autowired
    private AvatarRaceRepository avatarRaceRepository;

    @GetMapping
    public List<AvatarRace> getAllAvatarRaces() {
        return avatarRaceRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public AvatarRace getAvatarRaceById(@PathVariable(value = "id") final Long id) {
        return avatarRaceRepository.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvatarRace createAvatarRace(@RequestBody final AvatarRace avatarRace) {
        return avatarRaceRepository.save(avatarRace);

    }

    @PutMapping(value = "/{id}")
    public AvatarRace updateAvatarRace(@PathVariable(value = "id") final Long id, @RequestBody final AvatarRace data) {
        AvatarRace avatarRace = avatarRaceRepository.findOne(id);
        if (avatarRace != null) {
            avatarRace.setName(data.getName());
            avatarRace = avatarRaceRepository.save(avatarRace);
        }
        return avatarRace;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAvatarRace(@PathVariable(value = "id") final Long id) {
        final AvatarRace level = avatarRaceRepository.findOne(id);
        if (level != null) {
            avatarRaceRepository.delete(level);
        }
    }

}