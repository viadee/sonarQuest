package com.viadee.sonarQuest.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.services.UserService;

@RestController
@RequestMapping(PathConstants.USER_URL)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public User user(final Principal principal) {
        final String username = principal.getName();
        return userService.findByUsername(username);
    }
}
