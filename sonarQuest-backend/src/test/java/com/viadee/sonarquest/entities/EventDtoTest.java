package com.viadee.sonarquest.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.viadee.sonarquest.constants.EventType;
import com.viadee.sonarquest.repositories.EventRepository;

public class EventDtoTest {

	@Autowired
	private EventRepository eventRepository;
	
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
	
}
