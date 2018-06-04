package com.viadee.sonarQuest.rules;

/**
 * An external issue with some kind of status and methods to read it.
 * 
 * @author MeC
 *
 */
public interface IssueWithStatus {

    String getStatus();

    String getResolution();

}
