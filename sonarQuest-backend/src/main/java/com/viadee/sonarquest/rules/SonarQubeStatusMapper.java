package com.viadee.sonarquest.rules;

import org.springframework.stereotype.Component;

/**
 * This class maps the status from an external SonarQube "issue" to an internal
 * SonarQuest task status.
 * 
 * @author MeC
 *
 */
@Component
public class SonarQubeStatusMapper implements StatusMapper {

    private enum SonarQubeStatus {
        OPEN, REOPENED, CONFIRMED, CLOSED, RESOLVED;
    }

    @Override
    public SonarQuestTaskStatus mapExternalStatus(final IssueWithStatus issueWithStatus) {
        final SonarQuestTaskStatus mappedStatus;
        final String externalStatus = issueWithStatus.getStatus();
        final SonarQubeStatus sonarQubeStatus = SonarQubeStatus.valueOf(externalStatus);
        final String resolution = issueWithStatus.getResolution();
        switch (sonarQubeStatus) {
        case CLOSED:
            mappedStatus = SonarQuestTaskStatus.SOLVED;
            break;
        case RESOLVED:
            if ("FALSE-POSITIVE".equals(resolution)) {
                mappedStatus = SonarQuestTaskStatus.CLOSED;
            } else {
                mappedStatus = SonarQuestTaskStatus.OPEN;
            }
            break;
        default:
            mappedStatus = SonarQuestTaskStatus.OPEN;
            break;
        }
        return mappedStatus;
    }

}
