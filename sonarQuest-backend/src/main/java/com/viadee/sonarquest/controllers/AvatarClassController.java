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

import com.viadee.sonarquest.entities.AvatarClass;
import com.viadee.sonarquest.repositories.AvatarClassRepository;

@RestController
@RequestMapping("/avatarClass")
public class AvatarClassController {

    @Autowired
    private AvatarClassRepository avatarClassRepository;

    @GetMapping
    public List<AvatarClass> getAllAvatarClasses() {
        return avatarClassRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public AvatarClass getAvatarClassById(@PathVariable(value = "id") final Long id) {
        return avatarClassRepository.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvatarClass createAvatarClass(@RequestBody final AvatarClass avatarClass) {
        return avatarClassRepository.save(avatarClass);

    }

    @PutMapping(value = "/{id}")
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

    @DeleteMapping(value = "/{id}")
    public void deleteAvatarClass(@PathVariable(value = "id") final Long id) {
        final AvatarClass level = avatarClassRepository.findOne(id);
        if (level != null) {
            avatarClassRepository.delete(level);
        }
    }

}
