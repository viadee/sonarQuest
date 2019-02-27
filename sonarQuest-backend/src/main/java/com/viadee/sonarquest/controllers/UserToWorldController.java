package com.viadee.sonarquest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.UserToWorldDto;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.UserRepository;
import com.viadee.sonarquest.repositories.WorldRepository;
import com.viadee.sonarquest.services.UserService;
import com.viadee.sonarquest.services.WorldService;

@RestController
@RequestMapping("/user_to_world")
public class UserToWorldController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserToWorldController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private WorldService worldService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WorldRepository worldRepository;

    @PreAuthorize("hasAuthority('USER_WORLD_ASSIGNMENT')")
    @PostMapping(value = "/{user_id}/{world_id}")
    public User addUserToWorld(@PathVariable(value = "user_id") final Long userId,
            @PathVariable(value = "world_id") final Long worldId) {
	    LOGGER.info("addUserToWorld: userId " + userId + ", worldId " + worldId);
        final User user = userService.findById(userId);
        if (userId == user.getId()) {
    	    LOGGER.info("user " + user.getId());
        } else {
        	LOGGER.info("user " + user.getId() +" nicht gefunden");
        }
        final World world = worldService.findById(worldId);
        if (worldId == world.getId()) {
    	    LOGGER.info("world " + world.getId());
        } else {
        	LOGGER.info("world " + world.getId() +" nicht gefunden");
        }
        user.addWorld(world);
        

        User userProve = userRepository.findOne(user.getId());
        if (userProve.getWorlds().contains(world)) {

    	    LOGGER.info("world "+ world.getId() +" hinzugefügt");
        }else{

    	    LOGGER.info("fail");
        }
        return userService.save(user);
    }

    @PreAuthorize("hasAuthority('USER_WORLD_ASSIGNMENT')")
    @DeleteMapping(value = "/{user_id}/{world_id}")
    public User removeUserToWorld(@PathVariable(value = "user_id") final Long userId,
            @PathVariable(value = "world_id") final Long worldId) {
	    LOGGER.info("removeUserToWorld: userId " + userId + ", worldId " + worldId);
        final User user = userService.findById(userId);
        final World world = worldService.findById(worldId);
        user.removeWorld(world);
        if (user.getCurrentWorld() != null && user.getCurrentWorld().equals(world)) {
            user.setCurrentWorld(null);
        }
        return userService.save(user);
    }

    @PreAuthorize("hasAuthority('USER_WORLD_ASSIGNMENT')")
    @PutMapping(value = "/update")
    public void updateUserToWorld(@RequestBody final List<UserToWorldDto> userToWorlds) {
    	
        userToWorlds.forEach(userToWorld -> {
        	User user = userRepository.findOne(userToWorld.getUserId());
        	if (userToWorld.getJoined()) {
        		user.addWorld(worldRepository.findOne(userToWorld.getWorldId()));
        	} else {
        		user.removeWorld(worldRepository.findOne(userToWorld.getWorldId()));
        	}

    		userRepository.save(user);
        });
    }
}