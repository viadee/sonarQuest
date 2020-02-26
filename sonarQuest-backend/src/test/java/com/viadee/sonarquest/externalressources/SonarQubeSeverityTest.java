package com.viadee.sonarquest.externalressources;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class SonarQubeSeverityTest {

	@Test
	public void testFromString() {
		SonarQubeSeverity severity = SonarQubeSeverity.fromString("BLOCKER");
		assertSame(SonarQubeSeverity.BLOCKER, severity);
	}

}
