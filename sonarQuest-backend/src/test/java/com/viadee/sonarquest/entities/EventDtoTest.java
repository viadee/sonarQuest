package com.viadee.sonarquest.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
	
}
