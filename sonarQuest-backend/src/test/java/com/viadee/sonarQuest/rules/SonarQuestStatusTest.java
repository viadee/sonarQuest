package com.viadee.sonarQuest.rules;

import static org.junit.Assert.assertSame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SonarQuestStatusTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testFromStatusText() throws Exception {
        SonarQuestStatus sonarQuestStatus = SonarQuestStatus.fromStatusText("SOLVED");
        assertSame(SonarQuestStatus.SOLVED, sonarQuestStatus);
    }

    @Test
    public void testFromStatusText_unknownStatusFail() throws Exception {
        exception.expect(IllegalArgumentException.class);
        SonarQuestStatus.fromStatusText("UNMAPPED_STATUS");
    }

}
