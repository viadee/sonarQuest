package com.viadee.sonarQuest.externalRessources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarQubeIssueRessource {

    private SonarQubeIssuePaging paging;

    @JsonProperty("issues")
    private List<SonarQubeIssue> issues;

    public List<SonarQubeIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<SonarQubeIssue> issues) {
        this.issues = issues;
    }

    public SonarQubeIssuePaging getPaging() {
        return paging;
    }

    public void setPaging(SonarQubeIssuePaging paging) {
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
