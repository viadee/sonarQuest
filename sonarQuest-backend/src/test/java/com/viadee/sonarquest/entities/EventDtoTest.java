package com.viadee.sonarquest.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventDtoTest {
	
	@Test
    public void testConstructEventDto() {
		// Given
		Event event = new Event();
       
    	// When
        EventDto eventDto = new EventDto(event);
        
        // Then
        assertEquals(eventDto.getId(), event.getId());
    }
	
}
