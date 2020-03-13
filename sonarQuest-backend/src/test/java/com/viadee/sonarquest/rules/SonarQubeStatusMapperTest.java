package com.viadee.sonarquest.rules;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SonarQubeStatusMapperTest {

    @InjectMocks
    private SonarQubeStatusMapper mapper;

    @Test
    public void testMapExternalStatus_Open() {
        IssueWithStatus issueWithStatus = issue("OPEN");
        SonarQuestTaskStatus sonarQuestTaskStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestTaskStatus.OPEN, sonarQuestTaskStatus);
    }

    @Test
    public void testMapExternalStatus_Reopen() {
        IssueWithStatus issueWithStatus = issue("REOPENED");
        SonarQuestTaskStatus sonarQuestTaskStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestTaskStatus.OPEN, sonarQuestTaskStatus);
    }

    @Test
    public void testMapExternalStatus_Confirmed() {
        IssueWithStatus issueWithStatus = issue("CONFIRMED");
        SonarQuestTaskStatus sonarQuestTaskStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestTaskStatus.OPEN, sonarQuestTaskStatus);
    }

    @Test
    public void testMapExternalStatus_Closed() {
        IssueWithStatus issueWithStatus = issue("CLOSED");
        SonarQuestTaskStatus sonarQuestTaskStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestTaskStatus.SOLVED, sonarQuestTaskStatus);
    }

    @Test
    public void testMapExternalStatus_Resolved_FalsePositive() {
        IssueWithStatus issueWithStatus = issue("RESOLVED");
        when(issueWithStatus.getResolution()).thenReturn("FALSE-POSITIVE");
        SonarQuestTaskStatus sonarQuestTaskStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestTaskStatus.CLOSED, sonarQuestTaskStatus);
    }

    @Test
    public void testMapExternalStatus_Resolved() {
        IssueWithStatus issueWithStatus = issue("RESOLVED");
        SonarQuestTaskStatus sonarQuestTaskStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestTaskStatus.OPEN, sonarQuestTaskStatus);
    }

    private IssueWithStatus issue(String sonarQubeStatus) {
        IssueWithStatus issueWithStatus = mock(IssueWithStatus.class);
        when(issueWithStatus.getStatus()).thenReturn(sonarQubeStatus);
        return issueWithStatus;
    }

}
