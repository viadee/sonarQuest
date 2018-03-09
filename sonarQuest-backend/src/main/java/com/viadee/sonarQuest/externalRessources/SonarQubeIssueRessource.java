package com.viadee.sonarQuest.externalRessources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarQubeIssueRessource {

    private List<SonarQubeIssue> issues;

    public SonarQubeIssueRessource() {
    }

    public SonarQubeIssueRessource(List<SonarQubeIssue> issues) {
        this.issues = issues;
    }

    public List<SonarQubeIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<SonarQubeIssue> issues) {
        this.issues = issues;
    }
}
