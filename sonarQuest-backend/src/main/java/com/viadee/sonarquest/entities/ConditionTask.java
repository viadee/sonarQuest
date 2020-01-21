package com.viadee.sonarquest.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.viadee.sonarquest.rules.SonarQuestStatus;

//@Entity
//@DiscriminatorValue("CONDITION")
public class ConditionTask extends SpecialTask {
	
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
	
	public ConditionTask(final String title, final SonarQuestStatus status, final Long gold, final Long xp, final Quest quest,
            final String message, final World world, final String metricKey, final String comparator, final double acceptedValue, final double errorThreshold) {
		super(title, status, gold, xp, quest, message, world);
		this.metricKey = metricKey;
		this.comparator = comparator;
		this.acceptedValue = acceptedValue;
		this.errorThreshold = errorThreshold;
	}
	
	/**
	 * Create default specailTask with zero gold and xp
	 * @param title
	 * @param quest
	 * @param message
	 * @param world
	 * @param metricKey 
	 * @param comparator
	 * @param acceptedValue
	 * @param errorThreshold
	 */
	public ConditionTask(final String title, final Quest quest, final String message, final World world, 
			final String metricKey, final String comparator, final double acceptedValue, final double errorThreshold) {
		super(title, SonarQuestStatus.OPEN, 0L, 0L, quest, message, world);
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
