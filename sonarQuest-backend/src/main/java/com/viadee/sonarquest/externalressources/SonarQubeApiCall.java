package com.viadee.sonarquest.externalressources;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builder used for constructing SonarQube-REST API Calls.
 */
public class SonarQubeApiCall {

    private static final Logger LOGGER = LoggerFactory.getLogger(SonarQubeApiCall.class);

    private String sonarQubeRestApiCall;

    private void appendSearchParameter(String parameter) {
        if (!sonarQubeRestApiCall.endsWith("?")) {
            sonarQubeRestApiCall += "&";
        }
        sonarQubeRestApiCall += parameter;
    }

    /**
     * Initializes a new Ressources Object (all values reset) and sets the server URL as a basis for a new REST API
     * Call.
     * 
     * @param sonarQubeServerUrl
     * @return the new instance
     */
    public static SonarQubeApiCall onServer(String sonarQubeServerUrl) {
        SonarQubeApiCall ressource = new SonarQubeApiCall();
        ressource.sonarQubeRestApiCall = sonarQubeServerUrl;
        return ressource;
    }

    /**
     * Appends the call for the search issues function (e.g. /api/issues/search? ) in the SonarQube REST API to the
     * sonarQubeRestApiCall.
     * 
     * @see https://sonarcloud.io/web_api/api/issues
     */
    public SonarQubeApiCall searchIssues() {
        sonarQubeRestApiCall += "/api/issues/search?";
        return this;
    }

    /**
     * Appends the call for the search components function (e.g. /api/components/search? ) in the SonarQube REST API to
     * the sonarQubeRestApiCall.
     * 
     * @param qualifier
     *            _required_ - the Qualifier to search for (e.g. TRK for "projects")
     * 
     * @see https://sonarcloud.io/web_api/api/components
     */
    public SonarQubeApiCall searchComponents(SonarQubeComponentQualifier qualifier) {
        sonarQubeRestApiCall += "/api/components/search?";
        withQualifiers(qualifier);
        return this;
    }

    /**
     * Appends the parameter "componentKeys" with the value projectKey to the sonarQubeRestApiCall.
     */
    public SonarQubeApiCall withComponentKeys(String projectKey) {
        appendSearchParameter("componentKeys=" + projectKey);
        return this;
    }
    
    /**
     * Appends the parameter "organization" with the value organization to the sonarQubeRestApiCall.
     */
    public SonarQubeApiCall withOrganization(String organization) {
        appendSearchParameter("organization=" + organization);
        return this;
    }

    /**
     * Appends the parameter "types" with the value issueType to the sonarQubeRestApiCall.
     */
    public SonarQubeApiCall withTypes(SonarQubeIssueType issueType) {
        appendSearchParameter("types=" + issueType);
        return this;
    }

    /**
     * Appends the parameter "qualifiers" with the single value qualifier to the sonarQubeRestApiCall.
     */
    private SonarQubeApiCall withQualifiers(SonarQubeComponentQualifier qualifier) {
        appendSearchParameter("qualifiers=" + qualifier);
        return this;
    }

    /**
     * Appends the parameter "q" (Query) as a text-based simple search param to the sonarQubeRestApiCall.
     */
    public SonarQubeApiCall withQuery(String query) {
        appendSearchParameter("q=" + query);
        return this;
    }

    /**
     * Appends the parameter "severities" with the values in the severities-List to the sonarQubeRestApiCall.
     */
    public SonarQubeApiCall withSeverities(List<SonarQubeSeverity> severities) {
        appendSearchParameter("severities=" + StringUtils.join(severities, ","));
        return this;
    }

    /**
     * Appends the parameter "pageSize" with the value maxNumberOfIssuesOnPage to the sonarQubeRestApiCall.
     */
    public SonarQubeApiCall pageSize(int maxNumberOfIssuesOnPage) {
        appendSearchParameter("pageSize=" + maxNumberOfIssuesOnPage);
        return this;
    }

    /**
     * Appends the parameter "pageIndex" with the value startIndex to the sonarQubeRestApiCall. Paging starts here.
     */
    public SonarQubeApiCall pageIndex(int startIndex) {
        appendSearchParameter("pageIndex=" + startIndex);
        return this;
    }

    /**
     * Returns the constructed Ressource that contains the String API call.
     */
    public SonarQubeApiCall build() {
        return this;
    }

    /**
     * Returns the API Call built as a String.
     */
    public String asString() {
        LOGGER.info("Built SonarQube API Call: {}", sonarQubeRestApiCall);
        return sonarQubeRestApiCall;
    }

}
