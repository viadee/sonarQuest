package com.viadee.sonarquest.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.Principal;

import javax.transaction.Transactional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarquest.constants.AdventureState;
import com.viadee.sonarquest.entities.QualityRaid;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.World;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QualityRaidControllerTest {
	@Autowired
	private QualityRaidController controller;

	@Test
	@Ignore
	public void createQualityRaid() throws Exception {
		// Given
		String title = "Testadventure";
		String story = "My story";
		long gold = 10L;
		long xp = 10L;
		World world = new World("Kontentsu_CMS", "dk.kontentsu:Kontentsu_CMS", true, true);
//		Raid raid = new Raid(title, story, AdventureState.OPEN, gold, xp, world);
		// when
//		QualityRaid newAdventure = controller.createQualityRaid(raid);
		// then
//		assertNotNull(newAdventure.getId());
//		assertEquals(title, newAdventure.getTitle());
		
	}

	private Principal createPrincipal() {
		return new Principal() {

			@Override
			public String getName() {
				return "admin";
			}
		};
	}

}
