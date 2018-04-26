package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.dtos.AvatarClassDto;
import com.viadee.sonarQuest.entities.AvatarClass;
import com.viadee.sonarQuest.repositories.AvatarClassRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/avatarClass")
public class AvatarClassController {

    private AvatarClassRepository avatarClassRepository;

    public AvatarClassController(AvatarClassRepository avatarClassRepository) {
        this.avatarClassRepository = avatarClassRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AvatarClassDto> getAllAvatarClasses() {
        return avatarClassRepository.findAll().stream().map(this::toAvatarClassDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AvatarClassDto getAvatarClassById(@PathVariable(value = "id") Long id) {
        AvatarClass avatarClass = avatarClassRepository.findOne(id);
        return toAvatarClassDto(avatarClass);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AvatarClass createAvatarClass(@RequestBody AvatarClassDto avatarClassDto) {
        return avatarClassRepository.save(new AvatarClass(avatarClassDto.getName()));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public AvatarClassDto updateAvatarClass(@PathVariable(value = "id") Long id, @RequestBody AvatarClassDto avatarClassDto) {
        AvatarClass avatarClass = avatarClassRepository.findOne(id);
        if (avatarClass != null) {
            avatarClass.setName(avatarClassDto.getName());
            avatarClass.setSkills(avatarClassDto.getSkills());
            avatarClass = avatarClassRepository.save(avatarClass);
            avatarClassDto = toAvatarClassDto(avatarClass);
        }
        return avatarClassDto;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAvatarClass(@PathVariable(value = "id") Long id) {
        AvatarClass level = avatarClassRepository.findOne(id);
        if (level != null) {
            avatarClassRepository.delete(level);
        }
    }


    private AvatarClassDto toAvatarClassDto(AvatarClass avatarClass) {
        AvatarClassDto avatarClassDto = null;
        if (avatarClass != null) {
            avatarClassDto = new AvatarClassDto(avatarClass.getId(), avatarClass.getName(), avatarClass.getDevelopers(),avatarClass.getSkills());
        }
        return avatarClassDto;
    }

}
