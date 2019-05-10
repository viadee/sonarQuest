package com.viadee.sonarquest.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.viadee.sonarquest.constants.EventType;
import com.viadee.sonarquest.repositories.EventRepository;

public class UserDtoTest {
	
	@Autowired
	private EventRepository eventRepository;
	
	
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
	
}
