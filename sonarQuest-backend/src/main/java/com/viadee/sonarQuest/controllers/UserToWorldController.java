package com.viadee.sonarQuest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.services.UserService;
import com.viadee.sonarQuest.services.WorldService;

@RestController
@RequestMapping("/user_to_world")
public class UserToWorldController {

    @Autowired
    private UserService userService;

    @Autowired
    private WorldService worldService;

    @RequestMapping(value = "/{user_id}/{world_id}", method = RequestMethod.POST)
    public User addUserToWorld(@PathVariable(value = "user_id") final Long userId,
            @PathVariable(value = "world_id") final Long worldId) {
        final User user = userService.findById(userId);
        final World world = worldService.findById(worldId);
        user.addWorld(world);
        return userService.save(user);
    }

    @RequestMapping(value = "/{user_id}/{world_id}", method = RequestMethod.DELETE)
    public User removeUserToWorld(@PathVariable(value = "user_id") final Long userId,
            @PathVariable(value = "world_id") final Long worldId) {
        final User user = userService.findById(userId);
        final World world = worldService.findById(worldId);
        user.removeWorld(world);
        if (user.getCurrentWorld().equals(world)) {
            user.setCurrentWorld(null);
        }
        return userService.save(user);
    }
}
