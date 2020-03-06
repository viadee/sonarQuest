package com.viadee.sonarquest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.UserToWorldConnection;
import com.viadee.sonarquest.entities.UserToWorldDto;
import com.viadee.sonarquest.services.UserService;
import com.viadee.sonarquest.services.WorldService;

@RestController
@RequestMapping("/user_to_world")
public class UserToWorldController {

    private final UserService userService;

    private final WorldService worldService;

    public UserToWorldController(final UserService userService, final WorldService worldService) {
        this.userService = userService;
        this.worldService = worldService;
    }

    @PreAuthorize("hasAuthority('USER_WORLD_ASSIGNMENT')")
    @PutMapping(value = "/update")
    public Boolean updateUserToWorld(@RequestBody final List<UserToWorldDto> userToWorlds) {
        final List<UserToWorldConnection> userToWorldConnections = userToWorlds.stream()
                .map(this::toUserToWorldConnection)
                .collect(Collectors.toList());
        userService.updateUserToWorld(userToWorldConnections);
        // TODO Prüfen, warum ein Boolean erwartet wird
        return true;
    }

    private UserToWorldConnection toUserToWorldConnection(final UserToWorldDto userToWorldDto) {
        final UserToWorldConnection connection = new UserToWorldConnection();
        connection.setJoined(userToWorldDto.getJoined());
        connection.setUser(userService.findById(userToWorldDto.getUserId()));
        connection.setWorld(worldService.findById(userToWorldDto.getWorldId()));
        return connection;
    }
}