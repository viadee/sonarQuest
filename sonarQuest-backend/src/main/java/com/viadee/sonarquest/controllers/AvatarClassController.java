package com.viadee.sonarquest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.viadee.sonarquest.entities.AvatarClass;
import com.viadee.sonarquest.repositories.AvatarClassRepository;

@RestController
@RequestMapping("/avatarClass")
public class AvatarClassController {

    private final AvatarClassRepository avatarClassRepository;

    public AvatarClassController(AvatarClassRepository avatarClassRepository) {
        this.avatarClassRepository = avatarClassRepository;
    }

    @GetMapping
    public List<AvatarClass> getAllAvatarClasses() {
        return avatarClassRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public AvatarClass getAvatarClassById(@PathVariable(value = "id") final Long avatarClassId) {
        return avatarClassRepository.findById(avatarClassId).orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvatarClass createAvatarClass(@RequestBody final AvatarClass avatarClass) {
        return avatarClassRepository.save(avatarClass);

    }

    @PutMapping(value = "/{id}")
    public AvatarClass updateAvatarClass(@PathVariable(value = "id") final Long avatarClassId,
                                         @RequestBody final AvatarClass avatarClassInput) {
        AvatarClass avatarClass = avatarClassRepository.findById(avatarClassId).orElseThrow(ResourceNotFoundException::new);
        avatarClass.setName(avatarClassInput.getName());
        avatarClass.setSkills(avatarClassInput.getSkills());
        return avatarClassRepository.save(avatarClass);

    }

    @DeleteMapping(value = "/{id}")
    public void deleteAvatarClass(@PathVariable(value = "id") final Long avatarClassId) {
        avatarClassRepository.deleteById(avatarClassId);
    }

}
