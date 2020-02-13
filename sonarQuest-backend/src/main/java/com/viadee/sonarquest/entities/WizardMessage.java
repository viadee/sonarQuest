package com.viadee.sonarquest.entities;

/**
 * Entity for WizardMessages (not persistant). It contains a message that usually points to a problem, as well as a
 * solution advice.
 * 
 * @author MeC
 *
 */
public class WizardMessage {

    private String message;

    private String solution;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}