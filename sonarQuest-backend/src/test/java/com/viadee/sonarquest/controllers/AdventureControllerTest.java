package com.viadee.sonarquest.controllers;

import com.viadee.sonarquest.constants.AdventureState;
import com.viadee.sonarquest.entities.Adventure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AdventureControllerTest {

    @Autowired
    private AdventureController adventureController;

    @Test
    public void createAdventure() {
        // Given
        String title = "Testadventure";
        String story = "My story";
        long gold = 10L;
        long xp = 10L;
        Adventure adventure = new Adventure(title, story, AdventureState.OPEN, gold, xp);
        // when
        Adventure newAdventure = adventureController.createAdventure(createPrincipal(), adventure);
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
