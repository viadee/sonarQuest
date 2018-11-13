package com.viadee.sonarQuest.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarQuest.entities.WizardMessage;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.services.WizardService;
import com.viadee.sonarQuest.services.WorldService;

@RunWith(MockitoJUnitRunner.class)
public class WizardControllerTest {
	
    @InjectMocks
    private WizardController wizardController;
    
    @Mock
    private WorldService worldService;
    
    @Spy
    private WizardService wizardService;

	@Test
	public void testGetWizardMessageForWorld() throws Exception {
		throw new UnsupportedOperationException();
//		Long worldId = 1L;
//		World world = mock(World.class);
//		when(worldService.findById(worldId)).thenReturn(world);
//		WizardMessage wizardMessage = wizardController.getWizardMessageForWorld(worldId);
//		assertEquals("WIZARD.TESTMSG", wizardMessage.getMessage());
//		assertEquals("WIZARD.TESTSOLUTION", wizardMessage.getSolution());
	}

}
