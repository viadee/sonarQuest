package com.viadee.sonarquest.externalressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarQubeIssueRessource {

    private SonarQubePaging paging;

    @JsonProperty("issues")
    private List<SonarQubeIssue> issues;

    public List<SonarQubeIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<SonarQubeIssue> issues) {
        this.issues = issues;
    }

    public SonarQubePaging getPaging() {
        return paging;
    }

    public void setPaging(SonarQubePaging paging) {
        this.paging = paging;
    }

    @Override
    public String toString() {
        return "SonarQubeIssueRessource{" +
                "paging=" + paging +
                ", issues=" + issues +
                '}';
    }
}
