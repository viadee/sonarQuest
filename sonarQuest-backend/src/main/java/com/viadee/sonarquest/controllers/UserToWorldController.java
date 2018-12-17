package com.viadee.sonarquest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.services.UserService;
import com.viadee.sonarquest.services.WorldService;

@RestController
@RequestMapping("/user_to_world")
public class UserToWorldController {

    @Autowired
    private UserService userService;

    @Autowired
    private WorldService worldService;

    @PreAuthorize("hasAuthority('USER_WORLD_ASSIGNMENT')")
    @PostMapping(value = "/{user_id}/{world_id}")
    public User addUserToWorld(@PathVariable(value = "user_id") final Long userId,
            @PathVariable(value = "world_id") final Long worldId) {
        final User user = userService.findById(userId);
        final World world = worldService.findById(worldId);
        user.addWorld(world);
        return userService.save(user);
    }

    @PreAuthorize("hasAuthority('USER_WORLD_ASSIGNMENT')")
    @DeleteMapping(value = "/{user_id}/{world_id}")
    public User removeUserToWorld(@PathVariable(value = "user_id") final Long userId,
            @PathVariable(value = "world_id") final Long worldId) {
        final User user = userService.findById(userId);
        final World world = worldService.findById(worldId);
        user.removeWorld(world);
        if (user.getCurrentWorld() != null && user.getCurrentWorld().equals(world)) {
            user.setCurrentWorld(null);
        }
        return userService.save(user);
    }
}
