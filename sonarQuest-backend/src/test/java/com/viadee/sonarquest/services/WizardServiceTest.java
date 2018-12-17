package com.viadee.sonarquest.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.entities.WizardMessage;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.services.WizardService;
import com.viadee.sonarquest.services.WorldService;

@RunWith(MockitoJUnitRunner.class)
public class WizardServiceTest {

	@InjectMocks
	private WizardService service;

	@Mock
	private WorldService worldService;

	@Test
	public void testGetMostImportantMessageFor() throws Exception {
		World world = new World();
		when(worldService.findAll()).thenReturn(new ArrayList<>());
		WizardMessage wizardMessage = service.getMostImportantMessageFor(world);
		assertEquals("WIZARD.MSG_NO_WORLDS_FOUND", wizardMessage.getMessage());
		assertEquals("WIZARD.HINT_NO_WORLDS_FOUND", wizardMessage.getSolution());
	}

}
