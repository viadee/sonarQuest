package com.viadee.sonarquest.services;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Level;

@SpringBootTest
@Transactional
public class ArtefactServiceIT {

	@Autowired
	private ArtefactService service;

	@Autowired
	private LevelService levelService;

	@Test
	public void createArtefact_newLevel() {
		final Artefact artefact = new Artefact();
		artefact.setDescription("Test");
		artefact.setIcon("ra-chain");
		artefact.setMinLevel(new Level(1L));
		artefact.setName("Sword of Ogre Decapitation");
		artefact.setPrice(1L);
		artefact.setQuantity(1L);
		final Artefact createdArtefact = service.createArtefact(artefact);
		assertNotNull(createdArtefact);
		assertNotNull(createdArtefact.getId());
	}

	@Test
	public void createArtefact_existingLevel() {
		final Level level = levelService.findByLevel(1);
		final Artefact artefact = new Artefact();
		artefact.setDescription("Test");
		artefact.setIcon("ra-chain");
		artefact.setMinLevel(level);
		artefact.setName("Sword of Ogre Decapitation");
		artefact.setPrice(1L);
		artefact.setQuantity(1L);
		final Artefact createdArtefact = service.createArtefact(artefact);
		assertNotNull(createdArtefact);
		assertNotNull(createdArtefact.getId());
	}

}
