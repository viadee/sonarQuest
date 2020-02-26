package com.viadee.sonarquest.services;

import com.viadee.sonarquest.entities.WizardMessage;
import com.viadee.sonarquest.entities.World;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
