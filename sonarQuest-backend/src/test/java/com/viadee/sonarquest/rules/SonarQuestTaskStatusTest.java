package com.viadee.sonarquest.rules;

import static org.junit.Assert.assertSame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SonarQuestTaskStatusTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testFromStatusText() throws Exception {
        SonarQuestTaskStatus sonarQuestTaskStatus = SonarQuestTaskStatus.fromStatusText("SOLVED");
        assertSame(SonarQuestTaskStatus.SOLVED, sonarQuestTaskStatus);
    }

    @Test
    public void testFromStatusText_unknownStatusFail() throws Exception {
        exception.expect(IllegalArgumentException.class);
        SonarQuestTaskStatus.fromStatusText("UNMAPPED_STATUS");
    }

}
