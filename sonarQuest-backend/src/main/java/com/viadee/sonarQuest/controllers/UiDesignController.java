package com.viadee.sonarQuest.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.UiDesign;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.repositories.UiDesignRepository;
import com.viadee.sonarQuest.services.UiDesignService;
import com.viadee.sonarQuest.services.UserService;

@RestController
@RequestMapping("/ui")
public class UiDesignController {

    @Autowired
    private UiDesignService uiDesignService;

    @Autowired
    private UserService userService;

    @Autowired
    private UiDesignRepository uiDesignRepository;

    @RequestMapping(method = RequestMethod.GET)
    public UiDesign getUiDesign(final Principal principal) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        final UiDesign ui = uiDesignRepository.findByUser(user);
        return ui == null ? uiDesignService.updateUiDesign(user, "light") : ui;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT)
    public UiDesign updateUiDesign(final Principal principal, @RequestBody final String designName) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        return uiDesignService.updateUiDesign(user, designName);
    }
}
