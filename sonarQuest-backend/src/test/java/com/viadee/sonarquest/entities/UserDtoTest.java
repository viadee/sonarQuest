package com.viadee.sonarquest.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserDtoTest {	
	
	@Test
    public void testConstructUserDto() throws Exception {
		// Given
		User user = new User();
		
		// When
        UserDto userDto = new UserDto(user);

        // Then
        assertEquals(userDto.getId(), user.getId());
    }
	
}
