package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.dtos.AvatarRaceDto;
import com.viadee.sonarQuest.entities.AvatarRace;
import com.viadee.sonarQuest.repositories.AvatarRaceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/avatarRace")
public class AvatarRaceController {

    private AvatarRaceRepository avatarRaceRepository;

    public AvatarRaceController(AvatarRaceRepository avatarRaceRepository) {
        this.avatarRaceRepository = avatarRaceRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AvatarRaceDto> getAllAvatarRaces() {
        return this.avatarRaceRepository.findAll().stream().map(avatarRace -> toAvatarRaceDto(avatarRace)).collect(Collectors.toList());
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AvatarRaceDto getAvatarRaceById(@PathVariable(value = "id") Long id) {
        AvatarRace avatarRace = this.avatarRaceRepository.findOne(id);
        return this.toAvatarRaceDto(avatarRace);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AvatarRace createAvatarRace(@RequestBody AvatarRaceDto avatarRaceDto) {
        return this.avatarRaceRepository.save(new AvatarRace(avatarRaceDto.getName()));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public AvatarRaceDto updateAvatarRace(@PathVariable(value = "id") Long id, @RequestBody AvatarRaceDto avatarRaceDto) {
        AvatarRace avatarRace = this.avatarRaceRepository.findOne(id);
        if (avatarRace != null) {
            avatarRace.setName(avatarRaceDto.getName());
            avatarRace = this.avatarRaceRepository.save(avatarRace);
            avatarRaceDto = this.toAvatarRaceDto(avatarRace);
        }
        return avatarRaceDto;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAvatarRace(@PathVariable(value = "id") Long id) {
        AvatarRace level = this.avatarRaceRepository.findOne(id);
        if (level != null) {
            this.avatarRaceRepository.delete(level);
        }
    }


    private AvatarRaceDto toAvatarRaceDto(AvatarRace avatarRace) {
        AvatarRaceDto avatarRaceDto = null;
        if (avatarRace != null) {
            avatarRaceDto = new AvatarRaceDto(avatarRace.getId(), avatarRace.getName(), avatarRace.getDevelopers());
        }
        return avatarRaceDto;
    }
}