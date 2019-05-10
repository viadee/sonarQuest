package com.viadee.sonarquest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.Principal;
import java.sql.Timestamp;
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
import com.viadee.sonarquest.entities.EventDto;
import com.viadee.sonarquest.entities.EventUserDto;
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
    public void testConstructEventDto() throws Exception {
    	EventType type = EventType.MESSAGE;
    	Event event = eventRepository.findLast1ByType(type);

        assertEquals(EventType.MESSAGE, event.getType());
        
        EventDto eventDto = new EventDto(event);
        

        assertEquals(eventDto.getId(), event.getId());
        assertEquals(eventDto.getTimestamp(), event.getTimestamp());
        assertEquals(eventDto.getUserId(), event.getUser().getId());
        assertEquals(eventDto.getWorldId(), event.getWorld().getId());
    }
    
    @Test
    public void testConstructUserDto() throws Exception {
    	EventType type = EventType.MESSAGE;
    	Event event = eventRepository.findLast1ByType(type);
    	User user = event.getUser();
    	
        assertEquals(EventType.MESSAGE, event.getType());
        assertTrue(event.getUser().getId() > 0);
        
        UserDto userDto = new UserDto(user);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getCurrentWorldId(), user.getCurrentWorld().getId());
        
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
