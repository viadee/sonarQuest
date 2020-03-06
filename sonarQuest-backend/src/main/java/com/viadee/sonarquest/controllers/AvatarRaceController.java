package com.viadee.sonarquest.controllers;

import java.util.List;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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

    private final AvatarRaceRepository avatarRaceRepository;

    public AvatarRaceController(final AvatarRaceRepository avatarRaceRepository) {
        this.avatarRaceRepository = avatarRaceRepository;
    }

    @GetMapping
    public List<AvatarRace> getAllAvatarRaces() {
        return avatarRaceRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public AvatarRace getAvatarRaceById(@PathVariable(value = "id") final Long avatarRaceId) {
        return avatarRaceRepository.findById(avatarRaceId).orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvatarRace createAvatarRace(@RequestBody final AvatarRace avatarRace) {
        return avatarRaceRepository.save(avatarRace);

    }

    @PutMapping(value = "/{id}")
    public AvatarRace updateAvatarRace(@PathVariable(value = "id") final Long avatarRaceId, @RequestBody final AvatarRace avatarRaceInput) {
        final AvatarRace avatarRace = avatarRaceRepository.findById(avatarRaceId).orElseThrow(ResourceNotFoundException::new);
        avatarRace.setName(avatarRaceInput.getName());
        return avatarRaceRepository.save(avatarRace);

    }

    @DeleteMapping(value = "/{id}")
    public void deleteAvatarRace(@PathVariable(value = "id") final Long avatarRaceId) {
        avatarRaceRepository.deleteById(avatarRaceId);
    }

}