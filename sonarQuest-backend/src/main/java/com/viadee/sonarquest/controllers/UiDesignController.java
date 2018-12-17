package com.viadee.sonarquest.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.UiDesign;
import com.viadee.sonarquest.entities.UiDesignName;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.UiDesignRepository;
import com.viadee.sonarquest.services.UiDesignService;
import com.viadee.sonarquest.services.UserService;

@RestController
@RequestMapping("/ui")
public class UiDesignController {

    @Autowired
    private UiDesignService uiDesignService;

    @Autowired
    private UserService userService;

    @Autowired
    private UiDesignRepository uiDesignRepository;

    @GetMapping
    public UiDesign getUiDesign(final Principal principal) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        final UiDesign ui = uiDesignRepository.findByUser(user);
        return ui == null ? uiDesignService.updateUiDesign(user, UiDesignName.light) : ui;
    }

    @PutMapping
    public UiDesign updateUiDesign(final Principal principal, @RequestBody final String designName) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        return uiDesignService.updateUiDesign(user, UiDesignName.valueOf(designName));
    }
}
