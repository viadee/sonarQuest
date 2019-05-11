package com.viadee.sonarquest.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.viadee.sonarquest.repositories.UserRepository;

public class UserDtoTest {	
	
	@Autowired
	private UserRepository userRepository ;
	
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
