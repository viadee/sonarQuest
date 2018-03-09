package com.viadee.sonarQuest.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.helpers.Settings;
import com.viadee.sonarQuest.repositories.AdventureRepository;
import com.viadee.sonarQuest.repositories.ParticipationRepository;

@RunWith(MockitoJUnitRunner.class)
public class AdventureServiceTest {

    @Mock
    private AdventureRepository adventureRepository;

    @Mock
    private ParticipationRepository participationRepository;

    @InjectMocks
    private AdventureService adventureService;

    @Test
    public void testGetAllAdventuresForWorldAndDeveloper() {
        if (Settings.DEBUG) {
            System.out.println("AdventureServiceTest.testGetAllAdventuresForWorldAndDeveloper() started");
        }

        // create mock developer
        final Developer mockDeveloper1 = new Developer();
        mockDeveloper1.setUsername("mockUserAdventureServiceTest1");
        if (Settings.DEBUG) {
            System.out.println(" - MockDeveloper created.");
        }

        // create mock world
        final World mockWorld = new World();
        if (Settings.DEBUG) {
            System.out.println(" - MockWorld created.");
        }

        // create mock Adventure
        final Adventure mockAdventure1 = new Adventure();
        final Adventure mockAdventure2 = new Adventure();
        final Adventure mockAdventure3 = new Adventure();
        mockAdventure1.setTitle("mockAdventure1");
        mockAdventure2.setTitle("mockAdventure2");
        mockAdventure3.setTitle("mockAdventure3");
        if (Settings.DEBUG) {
            System.out.println(" - MockAdventures created: " + mockAdventure1.getTitle() + ", "
                    + mockAdventure2.getTitle() + ", " + mockAdventure3.getTitle());
        }
        mockAdventure1.setWorld(mockWorld);
        mockAdventure2.setWorld(mockWorld);
        mockAdventure3.setWorld(mockWorld);
        if (Settings.DEBUG) {
            System.out.println(" - Set MockWorld to mockAdventure.");
        }

        // a Adventure has a Developers
        mockAdventure1.addDeveloper(mockDeveloper1);
        mockAdventure2.addDeveloper(mockDeveloper1);
        if (Settings.DEBUG) {
            System.out.println(" - Add mockDeveloper1 to mockAdventure1 and mockAdventure2.");
        }

        // init mock repos
        final List<Adventure> adventuresByDeveloper = new ArrayList<>();
        adventuresByDeveloper.add(mockAdventure1);
        adventuresByDeveloper.add(mockAdventure2);
        final List<Adventure> adventuresByWorld = new ArrayList<>();
        adventuresByWorld.add(mockAdventure1);
        adventuresByWorld.add(mockAdventure2);
        adventuresByWorld.add(mockAdventure3);
        final List<Developer> developers = new ArrayList<>();
        developers.add(mockDeveloper1);
        when(adventureRepository.findByDevelopers(developers)).thenReturn(adventuresByDeveloper);
        when(adventureRepository.findByWorld(mockWorld)).thenReturn(adventuresByWorld);

        // call method to be tested
        final List<List<Adventure>> result1 = adventureService.getAllAdventuresForWorldAndDeveloper(mockWorld,
                mockDeveloper1);

        // verify result
        assertTrue(result1.get(0).contains(mockAdventure1));
        assertTrue(result1.get(0).contains(mockAdventure2));
        assertTrue(result1.get(1).contains(mockAdventure3));
        if (Settings.DEBUG) {
            System.out.println(" - Test successfully finished.");
        }

    }
}
