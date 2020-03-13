package com.viadee.sonarquest.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SonarQuestTaskStatusTest {

    @Test
    public void testFromStatusText() {
        SonarQuestTaskStatus sonarQuestTaskStatus = SonarQuestTaskStatus.fromStatusText("SOLVED");
        assertSame(SonarQuestTaskStatus.SOLVED, sonarQuestTaskStatus);
    }

    @Test
    public void testFromStatusText_unknownStatusFail() {
        assertThrows(IllegalArgumentException.class, () -> SonarQuestTaskStatus.fromStatusText("UNMAPPED_STATUS"));

    }

}
