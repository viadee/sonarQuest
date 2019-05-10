package com.viadee.sonarquest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarquest.constants.EventType;
import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.entities.EventUserDto;
import com.viadee.sonarquest.entities.MessageDto;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.UserDto;
import com.viadee.sonarquest.repositories.EventRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class EventServiceIT {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testServiceEvent() {

        Event event1 = new Event();
        event1.setTimestamp(new Timestamp(System.currentTimeMillis()));
        event1.setTitle("event1");
        eventRepository.save(event1);

        // get Event lists
        List<Event> events1 = eventService.getLatestEvent();

        // verify result
        assertTrue(events1.get(0).equals(event1));

        Event event2 = new Event();
        event2.setTimestamp(new Timestamp(System.currentTimeMillis()));
        event2.setTitle("event2");
        eventRepository.save(event2);

        Event event3 = new Event();
        event3.setTimestamp(new Timestamp(System.currentTimeMillis()));
        event3.setTitle("event3");
        eventRepository.save(event3);

        // get Event lists
        List<Event> events2 = eventService.get2LatestEvents();
        List<Event> events3 = eventService.get3LatestEvents();

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
    public void testCreateEventForCreatedQuest_shortStory() throws Exception {
        // Given
        String longStory = createStoryWithLength(25);
        Quest quest = createQuest("A new quest in the land of Testiara!", longStory);
        Principal principal = createPrincipal();
        // When
        eventService.createEventForCreatedQuest(quest, principal);
        // Then
        List<Event> events = eventService.getLatestEvent();
        Event latestEvent = events.get(0);
        assertEquals("A new quest in the land of Testiara!", latestEvent.getTitle());
    }

    @Test
    public void testCreateEventForCreatedQuest_storyMoreThen255CharsLong() throws Exception {
        // Given
        String longStory = createStoryWithLength(300);
        Quest quest = createQuest("A new quest in the land of Testiara!", longStory);
        Principal principal = createPrincipal();
        // When
        eventService.createEventForCreatedQuest(quest, principal);
        // Then
        List<Event> events = eventService.getLatestEvent();
        Event latestEvent = events.get(0);
        assertEquals("A new quest in the land of Testiara!", latestEvent.getTitle());
    }
    
    @Test
    public void testEventToEventUserDto() throws Exception {
    	EventType type = EventType.MESSAGE;
    	Event event = eventRepository.findLast1ByType(type);
    	User user = event.getUser();
    	
        assertEquals(EventType.MESSAGE, event.getType());
        assertTrue(event.getUser().getId() > 0);
        
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        

        assertEquals(eventUserDto.getEventDtos().get(0).getId(), event.getId());
        assertEquals(eventUserDto.getEventDtos().get(0).getUserId(), user.getId());
        assertEquals(eventUserDto.getUserDtos().get(0).getId(), user.getId());
    }
    
    @Test
    public void eventsToEventUserDto() {
    	// Given
    	List<Event> events = new ArrayList<>();
    	User user   = new User();
    	user.setId(1L);
    	Long userId = user.getId();
    	String message1 	   = "Test Message1";
    	String message2 	   = "Test Message2";
    	MessageDto messageDto1 = new MessageDto(message1,userId);
    	MessageDto messageDto2 = new MessageDto(message2,userId);
    	// When
    	Event event1 			  = eventService.createEventForNewMessage(messageDto1);
    	Event event2 			  = eventService.createEventForNewMessage(messageDto2);
    	events.add(event1);
    	events.add(event2);
    	
    	EventUserDto eventUserDto = eventService.eventsToEventUserDto(events);
    	// Then
        assertEquals(eventUserDto.getEventDtos().size(), events.size());
    }
    
    @Test
    public void hasUserDto() {
    	// Given
    	List<Event> events = eventRepository.findFirst2ByOrderByTimestampDesc();
    	
    	UserDto userDto1 = new UserDto(events.get(0).getUser());
    	UserDto userDto2 = new UserDto(events.get(1).getUser());
    	
    	// When
    	List<UserDto> userDtos = new ArrayList<>();
    	userDtos.add(userDto1);
    	userDtos.add(userDto2);
    	
    	// Then
        assertTrue(eventService.hasUserDto(userDtos, userDto1));
    }

    private String createStoryWithLength(int storyLength) {
        return RandomStringUtils.randomAlphabetic(storyLength);
    }

    private Principal createPrincipal() {
        return new Principal() {

            @Override
            public String getName() {
                return "admin";
            }
        };
    }

    private Quest createQuest(String title, String story) {
        return new Quest(title, story, QuestState.OPEN, 1L, 1L, null, null, true, null, null, null);
    }
}
