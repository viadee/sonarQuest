package com.viadee.sonarquest.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.AdventureRepository;
import com.viadee.sonarquest.repositories.ParticipationRepository;
import com.viadee.sonarquest.services.AdventureService;

@RunWith(MockitoJUnitRunner.class)
public class AdventureServiceTest {

    @Mock
    private AdventureRepository adventureRepository;

    @Mock
    private ParticipationRepository participationRepository;

    @InjectMocks
    private AdventureService adventureService;

    @Test
    public void testGetAllAdventuresForWorldAndUser() {

        // create mock user
        final User mockUser1 = new User();
        mockUser1.setUsername("mockUserAdventureServiceTest1");

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

        // a Adventure has a Users
        mockAdventure1.addUser(mockUser1);
        mockAdventure2.addUser(mockUser1);

        // init mock repos
        final List<Adventure> adventuresByUserAndWorld = new ArrayList<>();
        adventuresByUserAndWorld.add(mockAdventure1);
        adventuresByUserAndWorld.add(mockAdventure2);
        final List<Adventure> adventuresByWorld = new ArrayList<>();
        adventuresByWorld.add(mockAdventure1);
        adventuresByWorld.add(mockAdventure2);
        adventuresByWorld.add(mockAdventure3);

        final List<User> users = new ArrayList<>();
        users.add(mockUser1);

        when(adventureRepository.findByUsersAndWorld(users, mockWorld)).thenReturn(adventuresByUserAndWorld);
        when(adventureRepository.findByWorld(mockWorld)).thenReturn(adventuresByWorld);

        // call method to be tested
        final List<Adventure> joinedAdventures = adventureService.getJoinedAdventuresForUserInWorld(mockWorld,
                mockUser1);
        final List<Adventure> freeAdventures = adventureService.getFreeAdventuresForUserInWorld(mockWorld,
                mockUser1);

        // verify result
        assertTrue(joinedAdventures.contains(mockAdventure1));
        assertTrue(joinedAdventures.contains(mockAdventure2));
        assertTrue(freeAdventures.contains(mockAdventure3));

    }
}
