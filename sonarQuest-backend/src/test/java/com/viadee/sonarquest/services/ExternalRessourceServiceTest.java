package com.viadee.sonarquest.services;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.viadee.sonarquest.externalressources.SonarQubeSeverity;

public class ExternalRessourceServiceTest {

    @Test
    public void sonarQubeIssuesWithSeverity_constructUrl() throws Exception {
        List<SonarQubeSeverity> severities = Arrays.asList(SonarQubeSeverity.BLOCKER, SonarQubeSeverity.CRITICAL,
                SonarQubeSeverity.MAJOR);
        String sonarServerUrl = "http://www.fantasyurl.io:8080";
        String projectKey = "toweldonator42";
        String pageIndex = "2";
        final String fooResourceUrl = sonarServerUrl + "/api/issues/search?componentRoots="
                + projectKey + "&pageSize=500&pageIndex=" + pageIndex + "&severities="
                + StringUtils.join(severities, "%C");
        assertEquals(
                "http://www.fantasyurl.io:8080/api/issues/search?componentRoots=toweldonator42&pageSize=500&pageIndex=2&severities=BLOCKER%CCRITICAL%CMAJOR",
                fooResourceUrl);
    }

}
