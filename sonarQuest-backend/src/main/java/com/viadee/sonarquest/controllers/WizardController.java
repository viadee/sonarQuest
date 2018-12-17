package com.viadee.sonarquest.controllers;

import java.util.Optional;

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

	@Autowired
	private WizardService wizardService;

	@Autowired
	private WorldRepository worldRepository;

	@GetMapping(value = { "/world/{id}", "/world" })
	public WizardMessage getWizardMessageForWorld(@PathVariable(value = "id") final Optional<Long> id) {
		World world = new World();
		if (id.isPresent()) {
			world = worldRepository.findOne(id.get());
		}
		return wizardService.getMostImportantMessageFor(world);
	}
}