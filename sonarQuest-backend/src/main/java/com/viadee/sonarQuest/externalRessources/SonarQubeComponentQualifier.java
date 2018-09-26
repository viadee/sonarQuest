package com.viadee.sonarQuest.externalRessources;

/**
 * The Issue-Types for the search function in SonarQube.
 * 
 * @see https://sonarcloud.io/web_api/api/components/search
 */
public enum SonarQubeComponentQualifier {
    /**
     * Sub-projects
     */
    BRC,
    /**
     * Directories
     */
    DIR,
    /**
     * Files
     */
    FIL,
    /**
     * Projects
     */
    TRK,
    /**
     * Test Files
     */
    UTS
}
