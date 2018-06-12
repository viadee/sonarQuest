package com.viadee.sonarQuest.rules;

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
        SonarQuestStatus sonarQuestStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestStatus.OPEN, sonarQuestStatus);
    }

    @Test
    public void testMapExternalStatus_Reopen() throws Exception {
        IssueWithStatus issueWithStatus = issue("REOPENED");
        SonarQuestStatus sonarQuestStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestStatus.OPEN, sonarQuestStatus);
    }

    @Test
    public void testMapExternalStatus_Confirmed() throws Exception {
        IssueWithStatus issueWithStatus = issue("CONFIRMED");
        SonarQuestStatus sonarQuestStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestStatus.OPEN, sonarQuestStatus);
    }

    @Test
    public void testMapExternalStatus_Closed() throws Exception {
        IssueWithStatus issueWithStatus = issue("CLOSED");
        SonarQuestStatus sonarQuestStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestStatus.SOLVED, sonarQuestStatus);
    }

    @Test
    public void testMapExternalStatus_Resolved_FalsePositive() throws Exception {
        IssueWithStatus issueWithStatus = issue("RESOLVED");
        when(issueWithStatus.getResolution()).thenReturn("FALSE-POSITIVE");
        SonarQuestStatus sonarQuestStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestStatus.CLOSED, sonarQuestStatus);
    }

    @Test
    public void testMapExternalStatus_Resolved() throws Exception {
        IssueWithStatus issueWithStatus = issue("RESOLVED");
        SonarQuestStatus sonarQuestStatus = mapper.mapExternalStatus(issueWithStatus);
        assertSame(SonarQuestStatus.OPEN, sonarQuestStatus);
    }

    private IssueWithStatus issue(String sonarQubeStatus) {
        IssueWithStatus issueWithStatus = mock(IssueWithStatus.class);
        when(issueWithStatus.getStatus()).thenReturn(sonarQubeStatus);
        return issueWithStatus;
    }

}
