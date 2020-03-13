package com.viadee.sonarquest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.entities.EventUserDto;
import com.viadee.sonarquest.entities.MessageDto;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.UserDto;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.EventRepository;

@SpringBootTest
@Transactional
public class EventServiceIT {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testServiceEvent() {

        final Event event1 = new Event();
        event1.setTimestamp(new Timestamp(System.currentTimeMillis()));
        event1.setTitle("event1");
        eventRepository.save(event1);

        // get Event lists
        final List<Event> events1 = eventService.getLatestEvent();

        // verify result
        assertEquals(events1.get(0), event1);

        final Event event2 = new Event();
        event2.setTimestamp(new Timestamp(System.currentTimeMillis()));
        event2.setTitle("event2");
        eventRepository.save(event2);

        final Event event3 = new Event();
        event3.setTimestamp(new Timestamp(System.currentTimeMillis()));
        event3.setTitle("event3");
        eventRepository.save(event3);

        // get Event lists
        final List<Event> events2 = eventService.get2LatestEvents();
        final List<Event> events3 = eventService.get3LatestEvents();

        // verify result
        assertTrue(events2.contains(event3));
        assertTrue(events2.contains(event2));
        assertFalse(events2.contains(event1));

        // verify result
        assertTrue(events3.contains(event3));
        assertTrue(events3.contains(event2));
        assertTrue(events3.contains(event1));

    }

    @Test
    public void testCreateEventForCreatedQuest_shortStory() {
        // Given
        final String longStory = createStoryWithLength(25);
        final Quest quest = createQuest("A new quest in the land of Testiara!", longStory);
        final Principal principal = createPrincipal();
        // When
        eventService.createEventForCreatedQuest(quest, principal);
        // Then
        final List<Event> events = eventService.getLatestEvent();
        final Event latestEvent = events.get(0);
        assertEquals("A new quest in the land of Testiara!", latestEvent.getTitle());
    }

    @Test
    public void testCreateEventForCreatedQuest_storyMoreThen255CharsLong() {
        // Given
        final String longStory = createStoryWithLength(300);
        final Quest quest = createQuest("A new quest in the land of Testiara!", longStory);
        final Principal principal = createPrincipal();
        // When
        eventService.createEventForCreatedQuest(quest, principal);
        // Then
        final List<Event> events = eventService.getLatestEvent();
        final Event latestEvent = events.get(0);
        assertEquals("A new quest in the land of Testiara!", latestEvent.getTitle());
    }

    @Test
    public void testEventToEventUserDto() {
    	final User user1 		= new User();
    	final Event event1 	= new Event();
    	event1.setUser(user1);

        // When
        final EventUserDto eventUserDto = eventService.eventToEventUserDto(event1);

        // Then
        assertEquals(eventUserDto.getEventDtos().get(0).getId(), event1.getId());
        assertEquals(eventUserDto.getEventDtos().get(0).getUserId(), user1.getId());
        assertEquals(eventUserDto.getUserDtos().get(0).getId(), user1.getId());
    }

    @Test
    public void testEventsToEventUserDto() {
    	// Given
    	final List<Event> events = new ArrayList<>();
    	final World world	= new World();
    	final User user1   = createUser(1L,world);
    	final User user2   = createUser(2L,world);
    	final User user3   = createUser(3L,world);

    	final Event event1 			  = eventService.createEventForNewMessage(new MessageDto("Test Message1",user1.getId()));
    	final Event event2 			  = eventService.createEventForNewMessage(new MessageDto("Test Message2",user2.getId()));
    	final Event event3 			  = eventService.createEventForNewMessage(new MessageDto("Test Message3",user3.getId()));
    	final Event event4 			  = eventService.createEventForNewMessage(new MessageDto("Test Message4",user1.getId()));
    	final Event event5 			  = eventService.createEventForNewMessage(new MessageDto("Test Message5",user2.getId()));

    	events.add(event1);
    	events.add(event2);
    	events.add(event3);
    	events.add(event4);
    	events.add(event5);

    	// When
    	final EventUserDto eventUserDto = eventService.eventsToEventUserDto(events);

    	// Then
        assertEquals(5, eventUserDto.getEventDtos().size());
        assertEquals(3, eventUserDto.getUserDtos().size());
    }

    private User createUser(final Long id, final World world) {
    	final User user = new User();
    	user.setId(id);
    	user.setPicture("pic");
    	user.setCurrentWorld(world);
    	return user;
    }


	@Test
	public void hasUserDto() {
		// Given
		final World world = new World();
		final User user1 = createUser(1L, world);
		eventService.createEventForNewMessage(new MessageDto("Test Message1", user1.getId()));
		eventService.createEventForNewMessage(new MessageDto("Test Message2", user1.getId()));
		final List<Event> events = eventRepository.findFirst2ByOrderByTimestampDesc();

		final UserDto userDto1 = new UserDto(events.get(0).getUser());
		final UserDto userDto2 = new UserDto(events.get(1).getUser());

		// When
		final List<UserDto> userDtos = new ArrayList<>();
		userDtos.add(userDto1);
		userDtos.add(userDto2);

		// Then
		assertTrue(eventService.hasUserDto(userDtos, userDto1));
	}

    private String createStoryWithLength(final int storyLength) {
        return RandomStringUtils.randomAlphabetic(storyLength);
    }

    private Principal createPrincipal() {
        return () -> "admin";
    }

    private Quest createQuest(final String title, final String story) {
        return new Quest(title, story, QuestState.OPEN, 1L, 1L, null, null, true, null, null, null);
    }
}
