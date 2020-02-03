package com.viadee.sonarquest.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.viadee.sonarquest.rules.SonarQuestStatus;

/**
 * Extends a task with condition (= SonarQube Condition)
 *
 */
@Entity
@DiscriminatorValue("CONDITION")
@Table(name = "ConditionTask")
public class ConditionTask extends Task {
	
	@Column(name = "metricKey")
	private String metricKey;

	@Column(name = "comparator")
	private String comparator;

	@Column(name = "acceptedValue")
	private double acceptedValue;

	@Column(name = "errorThreshold")
	private double errorThreshold;
	
	public ConditionTask() {
	}
	
	public ConditionTask(String metricKey, String comparator, double acceptedValue, double errorThreshold) {
		super();
		this.metricKey = metricKey;
		this.comparator = comparator;
		this.acceptedValue = acceptedValue;
		this.errorThreshold = errorThreshold;
	}

	public ConditionTask(ConditionTask qualityTask) {
		super(qualityTask.getTitle(), qualityTask.getStatus(), qualityTask.getGold(), qualityTask.getXp(), qualityTask.getWorld());
		this.metricKey = qualityTask.getMetricKey();
		this.comparator = qualityTask.getComparator();
		this.acceptedValue = qualityTask.getAcceptedValue();
		this.errorThreshold = qualityTask.getErrorThreshold();
	}
	
	public ConditionTask(String title, SonarQuestStatus status, Long gold, Long xp, World world) {
		super(title, status, gold, xp, world);
	}
	
	public ConditionTask(String title, SonarQuestStatus status, Long gold, Long xp, World world, String metricKey, String comparator, double acceptedValue, double errorThreshold) {
		super(title, status, gold, xp, world);
		this.metricKey = metricKey;
		this.comparator = comparator;
		this.acceptedValue = acceptedValue;
		this.errorThreshold = errorThreshold;
	}
	
	public String getMetricKey() {
		return metricKey;
	}

	public void setMetricKey(String metricKey) {
		this.metricKey = metricKey;
	}

	public String getComparator() {
		return comparator;
	}

	public void setComparator(String comparator) {
		this.comparator = comparator;
	}

	public double getAcceptedValue() {
		return acceptedValue;
	}

	public void setAcceptedValue(double acceptedValue) {
		this.acceptedValue = acceptedValue;
	}

	public double getErrorThreshold() {
		return errorThreshold;
	}

	public void setErrorThreshold(double errorThreshold) {
		this.errorThreshold = errorThreshold;
	}
}
