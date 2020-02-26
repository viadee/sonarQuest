package com.viadee.sonarquest.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.viadee.sonarquest.rules.SonarQuestTaskStatus;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("STANDARD")
public class StandardTask extends Task {

    @Column(name = "component")
    private String component;

    @Column(name = "severity")
    private String severity;

    @Column(name = "type")
    private String type;

    @Column(name = "debt")
    private Integer debt;

    @Column(name = "issue_key")
    private String issueKey;

    @Column(name = "issue_rule")
    private String issueRule;

    public StandardTask() {
    }

    public StandardTask(final String title, final SonarQuestTaskStatus status, final Long gold, final Long xp,
                        final Quest quest,
                        final World world, final String key,
                        final String component, final String severity, final String type, final Integer debt,
                        final String issueKey, String issueRule) {
        setTitle(title);
        setStatus(status);
        setGold(gold);
        setXp(xp);
        setQuest(quest);
        setWorld(world);
        setKey(key);
        this.component = component;
        this.severity = severity;
        this.type = type;
        this.debt = debt;
        this.issueKey = issueKey;
        this.issueRule = issueRule;
    }
}
