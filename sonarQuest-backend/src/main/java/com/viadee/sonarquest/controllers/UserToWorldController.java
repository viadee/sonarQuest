package com.viadee.sonarquest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.UserToWorldDto;
import com.viadee.sonarquest.services.UserService;

@RestController
@RequestMapping("/user_to_world")
public class UserToWorldController {

    @Autowired
    private UserService userService;
    
    @PreAuthorize("hasAuthority('USER_WORLD_ASSIGNMENT')")
    @PutMapping(value = "/update")
    public Boolean updateUserToWorld(@RequestBody final List<UserToWorldDto> userToWorlds) {
    	return userService.updateUserToWorld(userToWorlds);
    }
}