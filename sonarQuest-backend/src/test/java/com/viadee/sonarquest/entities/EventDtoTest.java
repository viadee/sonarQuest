package com.viadee.sonarquest.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.viadee.sonarquest.constants.EventState;
import com.viadee.sonarquest.constants.EventType;

public class EventDtoTest {
	
	@Test
    public void testConstructEventDto() throws Exception {
		// Given
		Event event = new Event();
       
    	// When
        EventDto eventDto = new EventDto(event);
        
        // Then
        assertEquals(eventDto.getId(), event.getId());
    }
	
	@Test
	public void testNewEventDto() throws Exception {
		
		// Given
		Artefact artefact = new Artefact();
		EventType type = EventType.ARTEFACT;
		Long typeId = artefact.getId();
		String title = "title";
		String story = "story";
		EventState state = EventState.NEW_ITEM;
		String image = "image";
		World world = new World();
		User user = new User();
		
		// When
		Event event1 = new Event(type, typeId, title, story, state, image, world);
		EventDto eventDto1 = new EventDto(event1);
		Event event2 = new Event(type, typeId, title, story, state, image, world, user);
		EventDto eventDto2 = new EventDto(event2);
		Event event3 = new Event(type, typeId, title, story, state, world);
		EventDto eventDto3 = new EventDto(event3);
		Event event4 = new Event(type, story, world, user);
		EventDto eventDto4 = new EventDto(event4);
		Event event5 = new Event(type, typeId, title, story, state, image, user);
		EventDto eventDto5 = new EventDto(event5);
		
					
		
		// Then
		assertEquals(artefact.getId(),event1.getTypeId());
		assertEquals(artefact.getId(),eventDto1.getTypeId());
		assertEquals(artefact.getId(),event2.getTypeId());
		assertEquals(artefact.getId(),eventDto2.getTypeId());
		assertEquals(artefact.getId(),event3.getTypeId());
		assertEquals(artefact.getId(),eventDto3.getTypeId());
		assertEquals(user,event4.getUser());
		assertEquals(user.getId(),eventDto4.getUserId());
		assertEquals(user,event5.getUser());
		assertEquals(user.getId(),eventDto5.getUserId());
		
	}
	
}
