package com.viadee.sonarquest.entities;

import com.viadee.sonarquest.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDtoTest {

    @Test
    public void testConstructUserDto() {
		// Given
		User user = new User();
		
		// When
        UserDto userDto = new UserDto(user);

        // Then
        assertEquals(userDto.getId(), user.getId());
    }
	
}
