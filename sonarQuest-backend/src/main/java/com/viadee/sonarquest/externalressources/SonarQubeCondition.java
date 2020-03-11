package com.viadee.sonarquest.externalressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Condition response from SonarQube project status.
 * 
 * @see https://sonarcloud.io/web_api/api/qualitygates/project_status
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarQubeCondition {

	private String status;
	private String metricKey;
	private String comparator;
	private double actualValue;
	private double errorThreshold;
	private double period;


	public SonarQubeCondition() {
	}

	public SonarQubeCondition(String status, String metricKey, String comparator, double actualValue,
			double errorThreshold, double period) {
		super();
		this.status = status;
		this.metricKey = metricKey;
		this.comparator = comparator;
		this.actualValue = actualValue;
		this.errorThreshold = errorThreshold;
		this.period = period;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public double getActualValue() {
		return actualValue;
	}

	public void setActualValue(double actualValue) {
		this.actualValue = actualValue;
	}

	public double getErrorThreshold() {
		return errorThreshold;
	}

	public void setErrorThreshold(double errorThreshold) {
		this.errorThreshold = errorThreshold;
	}
	
	public double getPeriod() {
		return period;
	}

	public void setPeriod(double period) {
		this.period = period;
	}
}
