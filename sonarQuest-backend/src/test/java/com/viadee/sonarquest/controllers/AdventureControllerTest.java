package com.viadee.sonarquest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Principal;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.viadee.sonarquest.constants.AdventureState;
import com.viadee.sonarquest.entities.Adventure;

@SpringBootTest
@Transactional
public class AdventureControllerTest {

    @Autowired
    private AdventureController adventureController;

    @Test
    public void createAdventure() {
        // Given
        final String title = "Testadventure";
        final String story = "My story";
        final long gold = 10L;
        final long xp = 10L;
        final Adventure adventure = new Adventure(title, story, AdventureState.OPEN, gold, xp);
        // when
        final Adventure newAdventure = adventureController.createAdventure(createPrincipal(), adventure);
        // then
        assertNotNull(newAdventure.getId());
        assertTrue(newAdventure.getId() > 0);
        assertEquals(title, newAdventure.getTitle());
        assertEquals(story, newAdventure.getStory());
        assertEquals(AdventureState.OPEN, newAdventure.getStatus());
        assertEquals(gold, newAdventure.getGold().longValue());
        assertEquals(xp, newAdventure.getXp().longValue());

    }

    private Principal createPrincipal() {
        return () -> "admin";
    }

}
