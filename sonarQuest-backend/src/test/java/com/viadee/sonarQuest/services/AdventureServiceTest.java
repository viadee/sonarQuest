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

        // create mock developer
        final Developer mockDeveloper1 = new Developer();
        mockDeveloper1.setUsername("mockUserAdventureServiceTest1");

        // create mock world
        final World mockWorld = new World();

        // create mock Adventure
        final Adventure mockAdventure1 = new Adventure();
        final Adventure mockAdventure2 = new Adventure();
        final Adventure mockAdventure3 = new Adventure();
        mockAdventure1.setTitle("mockAdventure1");
        mockAdventure2.setTitle("mockAdventure2");
        mockAdventure3.setTitle("mockAdventure3");
        
        mockAdventure1.setWorld(mockWorld);
        mockAdventure2.setWorld(mockWorld);
        mockAdventure3.setWorld(mockWorld);


        // a Adventure has a Developers
        mockAdventure1.addDeveloper(mockDeveloper1);
        mockAdventure2.addDeveloper(mockDeveloper1);


        // init mock repos
        final List<Adventure> adventuresByDeveloperAndWorld = new ArrayList<>();
        adventuresByDeveloperAndWorld.add(mockAdventure1);
        adventuresByDeveloperAndWorld.add(mockAdventure2);
        final List<Adventure> adventuresByWorld = new ArrayList<>();
        adventuresByWorld.add(mockAdventure1);
        adventuresByWorld.add(mockAdventure2);
        adventuresByWorld.add(mockAdventure3);
        
        final List<Developer> developers = new ArrayList<>();
        developers.add(mockDeveloper1);
        
        when(adventureRepository.findByDevelopersAndWorld(developers,mockWorld)).thenReturn(adventuresByDeveloperAndWorld);
        when(adventureRepository.findByWorld(mockWorld)).thenReturn(adventuresByWorld);

        // call method to be tested
        final List<Adventure> joinedAdventures = adventureService.getJoinedAdventuresForDeveloperInWorld(mockWorld,mockDeveloper1);
        final List<Adventure> freeAdventures   = adventureService.getFreeAdventuresForDeveloperInWorld(mockWorld,mockDeveloper1);

        // verify result
        assertTrue(joinedAdventures.contains(mockAdventure1));
        assertTrue(joinedAdventures.contains(mockAdventure2));
        assertTrue(freeAdventures.contains(mockAdventure3));


    }
}
