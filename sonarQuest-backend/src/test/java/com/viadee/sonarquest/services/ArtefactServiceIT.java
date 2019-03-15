package com.viadee.sonarquest.services;

import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Level;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ArtefactServiceIT {
	
	@Autowired
	private ArtefactService service;

	@Autowired
	private LevelService levelService;

	@Test
	public void createArtefact_newLevel() {
		Artefact artefact = new Artefact();
		artefact.setDescription("Test");
		artefact.setIcon("ra-chain");
		artefact.setMinLevel(new Level(1L));
		artefact.setName("Sword of Ogre Decapitation");
		artefact.setPrice(1L);
		artefact.setQuantity(1L);
		Artefact createdArtefact = service.createArtefact(artefact);
		assertNotNull(createdArtefact);
		assertNotNull(createdArtefact.getId());
	}

	@Test
	public void createArtefact_existingLevel() {
		Level level = levelService.findByLevel(1);
		Artefact artefact = new Artefact();
		artefact.setDescription("Test");
		artefact.setIcon("ra-chain");
		artefact.setMinLevel(level);
		artefact.setName("Sword of Ogre Decapitation");
		artefact.setPrice(1L);
		artefact.setQuantity(1L);
		Artefact createdArtefact = service.createArtefact(artefact);
		assertNotNull(createdArtefact);
		assertNotNull(createdArtefact.getId());
	}

}
