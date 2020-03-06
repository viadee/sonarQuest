package com.viadee.sonarquest.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserDtoTest {

    @Test
    public void testConstructUserDto() {
		// Given
		final User user = new User();

		// When
        final UserDto userDto = new UserDto(user);

        // Then
        assertEquals(userDto.getId(), user.getId());
    }

}
