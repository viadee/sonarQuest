package com.viadee.sonarQuest.rules;

/**
 * Represents the internal SonarQuest-Task-Status.
 * 
 * @author MeC
 *
 */
public enum SonarQuestStatus {

    //@formatter:off
    OPEN("OPEN"),
    CLOSED("CLOSED"),
    SOLVED("SOLVED");
    //@formatter:on

    private String text;

    private SonarQuestStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    /**
     * Gets a corresponding SonarQuestStatus from a statusText. E.g. will retrieve
     * the Status Enum SOLVED when the text "SOLVED" is searched.
     * 
     * @param statusText
     * @return
     * @throws IllegalArgumentException
     *             when no status is found, due to the game being inconsistant. All
     *             status must be mapped.
     */
    public static SonarQuestStatus fromStatusText(String statusText) {
        if (statusText == null) {
        	return SonarQuestStatus.OPEN;
        }
    	for (SonarQuestStatus status : values()) {
            if (status.getText().equals(statusText)) {
                return status;
            }
        }
        throw new IllegalArgumentException(
                "All status of external resources/issues must be mapped but an unmapped status was found: "
                        + statusText);
    }

}
