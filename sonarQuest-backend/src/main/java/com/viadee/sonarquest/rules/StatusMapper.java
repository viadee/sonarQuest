package com.viadee.sonarquest.rules;

/**
 * Class for mapping status from an external system that SonarQuest draws data
 * from (e.g. SonarQube) to a SonarQuest task status.
 * 
 * @author MeC
 *
 */
public interface StatusMapper {

    /**
     * Maps the Status from an external "source issue" to a SonarQuestStatus. E.g.
     * an external source issue could have a status called "fresh" that would have
     * to be mapped to a SonarQuest status "CREATED".
     * 
     * @param sourceIssue
     * @return
     */
    SonarQuestStatus mapExternalStatus(final IssueWithStatus sourceIssue);

}
