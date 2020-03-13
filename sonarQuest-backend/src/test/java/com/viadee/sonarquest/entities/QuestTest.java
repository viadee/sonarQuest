package com.viadee.sonarquest.entities;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestTest {

    @Test
    public void testGetParticipants_noParticipants() {
        Quest quest = new Quest();
        List<String> participants = quest.getParticipants();
        assertEquals(Collections.emptyList(), participants);
    }

    @Test
    public void testGetParticipants_twoParticipants() {
        // Given
        Quest quest = new Quest();
        List<Participation> participations = new ArrayList<>();
        Participation participation1 = mockeParticipationMitUser("Jane");
        Participation participation2 = mockeParticipationMitUser("Joe");
        participations.add(participation1);
        participations.add(participation2);
        quest.setParticipations(participations);
        // When
        List<String> participants = quest.getParticipants();
        // Then
        assertArrayEquals(new String[] { "Jane", "Joe" }, participants.toArray());
    }

    private Participation mockeParticipationMitUser(String username) {
        Participation part = new Participation();
        User user = new User();
        user.setUsername(username);
        part.setUser(user);
        return part;
    }

}
