package com.viadee.sonarquest.controllers;

import java.util.Optional;

import com.viadee.sonarquest.services.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.WizardMessage;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.WorldRepository;
import com.viadee.sonarquest.services.WizardService;

@RestController
@RequestMapping("/wizard")
public class WizardController {

	private final WizardService wizardService;

	private final WorldService worldService;

	public WizardController(WizardService wizardService, WorldService worldService) {
		this.wizardService = wizardService;
		this.worldService = worldService;
	}

	@GetMapping(value = { "/world/{id}", "/world" })
	public WizardMessage getWizardMessageForWorld(@PathVariable(value = "id") final Long worldId) {
		return wizardService.getMostImportantMessageFor(worldService.findById(worldId));
	}
}