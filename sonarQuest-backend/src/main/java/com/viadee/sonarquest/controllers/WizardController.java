package com.viadee.sonarquest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.WizardMessage;
import com.viadee.sonarquest.services.WizardService;
import com.viadee.sonarquest.services.WorldService;

@RestController
@RequestMapping("/wizard")
public class WizardController {

    private final WizardService wizardService;

    private final WorldService worldService;

    public WizardController(final WizardService wizardService, final WorldService worldService) {
        this.wizardService = wizardService;
        this.worldService = worldService;
    }

    @GetMapping(value = { "/world/{id}", "/world" })
    public WizardMessage getWizardMessageForWorld(@PathVariable(value = "id", required = false) final Long worldId) {
        return wizardService.getMostImportantMessageFor(worldService.findById(worldId));
    }
}