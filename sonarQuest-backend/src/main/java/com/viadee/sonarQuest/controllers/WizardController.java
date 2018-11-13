package com.viadee.sonarQuest.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.WizardMessage;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.WizardService;

@RestController
@RequestMapping("/wizard")
public class WizardController {

	@Autowired
	private WizardService wizardService;

	@Autowired
	private WorldRepository worldRepository;

	@RequestMapping(value = { "/world/{id}", "/world" }, method = RequestMethod.GET)
	public WizardMessage getWizardMessageForWorld(@PathVariable(value = "id") final Optional<Long> id) {
		World world = new World();
		if (id.isPresent()) {
			world = worldRepository.findOne(id.get());
		}
		return wizardService.getMostImportantMessageFor(world);
	}
}