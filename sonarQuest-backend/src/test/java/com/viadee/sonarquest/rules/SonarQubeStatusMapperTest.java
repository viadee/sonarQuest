package com.viadee.sonarquest.rules;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SonarQubeStatusMapperTest {

    @InjectMocks
    private SonarQubeStatusMapper mapper;

    @Test
    public void testMapExternalStatus_Open() throws Exception {
        IssueWithStatus issueWithStatus = issue("OPEN");
        SonarQuestTaskStatus sonarQuestTaskStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestTaskStatus.OPEN, sonarQuestTaskStatus);
    }

    @Test
    public void testMapExternalStatus_Reopen() throws Exception {
        IssueWithStatus issueWithStatus = issue("REOPENED");
        SonarQuestTaskStatus sonarQuestTaskStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestTaskStatus.OPEN, sonarQuestTaskStatus);
    }

    @Test
    public void testMapExternalStatus_Confirmed() throws Exception {
        IssueWithStatus issueWithStatus = issue("CONFIRMED");
        SonarQuestTaskStatus sonarQuestTaskStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestTaskStatus.OPEN, sonarQuestTaskStatus);
    }

    @Test
    public void testMapExternalStatus_Closed() throws Exception {
        IssueWithStatus issueWithStatus = issue("CLOSED");
        SonarQuestTaskStatus sonarQuestTaskStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestTaskStatus.SOLVED, sonarQuestTaskStatus);
    }

    @Test
    public void testMapExternalStatus_Resolved_FalsePositive() throws Exception {
        IssueWithStatus issueWithStatus = issue("RESOLVED");
        when(issueWithStatus.getResolution()).thenReturn("FALSE-POSITIVE");
        SonarQuestTaskStatus sonarQuestTaskStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestTaskStatus.CLOSED, sonarQuestTaskStatus);
    }

    @Test
    public void testMapExternalStatus_Resolved() throws Exception {
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
