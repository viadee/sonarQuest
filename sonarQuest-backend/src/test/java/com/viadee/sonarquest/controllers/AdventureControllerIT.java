package com.viadee.sonarquest.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarquest.constants.AdventureState;
import com.viadee.sonarquest.controllers.AdventureController;
import com.viadee.sonarquest.entities.Adventure;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdventureControllerIT {

    @Autowired
    private AdventureController adventureController;

    @Test
    public void createAdventure() throws Exception {
        // Given
        String title = "Testadventure";
        String story = "My story";
        long gold = 10L;
        long xp = 10L;
        Adventure adventure = new Adventure(title, story, AdventureState.OPEN, gold, xp);
        // when
        Adventure newAdventure = adventureController.createAdventure(adventure);
        // then
        assertNotNull(newAdventure.getId());
        assertTrue(newAdventure.getId() > 0);
        assertEquals(title, newAdventure.getTitle());
        assertEquals(story, newAdventure.getStory());
        assertEquals(AdventureState.OPEN, newAdventure.getStatus());
        assertEquals(gold, newAdventure.getGold().longValue());
        assertEquals(xp, newAdventure.getXp().longValue());

    }

}
