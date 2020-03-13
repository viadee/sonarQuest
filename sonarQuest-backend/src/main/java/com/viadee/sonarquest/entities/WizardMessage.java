package com.viadee.sonarquest.entities;

import lombok.Data;

/**
 * Entity for WizardMessages (not persistant). It contains a message that usually points to a problem, as well as a
 * solution advice.
 * 
 * @author MeC
 *
 */
@Data
public class WizardMessage {

    private String message;

    private String solution;
}