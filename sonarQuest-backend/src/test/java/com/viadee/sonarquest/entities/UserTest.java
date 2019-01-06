package com.viadee.sonarquest.entities;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class UserTest {

	@Test
	public void testGetJoinedWorlds_noWorlds() throws Exception {
		User user = new User();
		List<String> joinedWorlds = user.getJoinedWorlds();
		assertEquals(Collections.emptyList(), joinedWorlds);
	}

	@Test
	public void testGetJoinedWorlds_twoActiveWorlds() throws Exception {
		// Given
		User user = new User();
		List<World> activeWorlds = new ArrayList<>();
		World firstWorld = new World("First World", "JDK 9", true, true);
		World secondWorld = new World("Second World", "JDK 10", true, true);
		activeWorlds.add(firstWorld);
		activeWorlds.add(secondWorld);
		user.setWorlds(activeWorlds);
		// When
		List<String> joinedWorlds = user.getJoinedWorlds();
		// Then
		assertArrayEquals(new String[] { "First World", "Second World" }, joinedWorlds.toArray());
	}

}
