package com.viadee.sonarquest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.AvatarClass;
import com.viadee.sonarquest.repositories.AvatarClassRepository;

@RestController
@RequestMapping("/avatarClass")
public class AvatarClassController {

    private AvatarClassRepository avatarClassRepository;

    public AvatarClassController(final AvatarClassRepository avatarClassRepository) {
        this.avatarClassRepository = avatarClassRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AvatarClass> getAllAvatarClasses() {
        return avatarClassRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AvatarClass getAvatarClassById(@PathVariable(value = "id") final Long id) {
        return avatarClassRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AvatarClass createAvatarClass(@RequestBody final AvatarClass avatarClass) {
        return avatarClassRepository.save(avatarClass);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public AvatarClass updateAvatarClass(@PathVariable(value = "id") final Long id,
            @RequestBody final AvatarClass data) {
        AvatarClass avatarClass = avatarClassRepository.findOne(id);
        if (avatarClass != null) {
            avatarClass.setName(data.getName());
            avatarClass.setSkills(data.getSkills());
            avatarClass = avatarClassRepository.save(avatarClass);
        }
        return avatarClass;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAvatarClass(@PathVariable(value = "id") final Long id) {
        final AvatarClass level = avatarClassRepository.findOne(id);
        if (level != null) {
            avatarClassRepository.delete(level);
        }
    }

}
