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
    public SonarQuestStatus mapExternalStatus(final IssueWithStatus issueWithStatus) {
        final SonarQuestStatus mappedStatus;
        final String externalStatus = issueWithStatus.getStatus();
        final SonarQubeStatus sonarQubeStatus = SonarQubeStatus.valueOf(externalStatus);
        final String resolution = issueWithStatus.getResolution();
        switch (sonarQubeStatus) {
        case OPEN:
        case REOPENED:
        case CONFIRMED:
            mappedStatus = SonarQuestStatus.OPEN;
            break;
        case CLOSED:
            mappedStatus = SonarQuestStatus.SOLVED;
            break;
        case RESOLVED:
            if ("FALSE-POSITIVE".equals(resolution)) {
                mappedStatus = SonarQuestStatus.CLOSED;
            } else {
                mappedStatus = SonarQuestStatus.OPEN;
            }
            break;
        default:
            mappedStatus = SonarQuestStatus.OPEN;
            break;
        }
        return mappedStatus;
    }

}
