package com.viadee.sonarquest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.rules.SonarQuestStatus;

/**
 *	Represent a Quality Gate condition:
 *	It's a combination of: measure, comparison operator and error value
 *	@see SonarQubeCondition	
 *
 *	(Maybe a reward will be added.)
 */
@Entity
@Table(name = "Condition")
public class Condition {
	
	@Id
    @GeneratedValue
    private Long id;

	@Column(name = "title")
    private String title;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SonarQuestStatus status;
    
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "quality_gate_raid_id")
    private QualityGateRaid qualityGateRaid;
	
	@Column(name = "metricKey")
	private String metricKey;

	@Column(name = "comparator")
	private String comparator;

	@Column(name = "actualValue")
	private double actualValue;

	@Column(name = "errorThreshold")
	private double errorThreshold;
	
	public Condition() {
	}
	
	public Condition(String metricKey, String comparator, double actualValue, double errorThreshold) {
		this(metricKey, comparator, actualValue, errorThreshold, null);
	}
	
	public Condition(String metricKey, String comparator, double actualValue, double errorThreshold, QualityGateRaid raid) {
		super();
		this.metricKey = metricKey;
		this.comparator = comparator;
		this.actualValue = actualValue;
		this.errorThreshold = errorThreshold;
		this.qualityGateRaid = raid;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMetricKey() {
		return metricKey;
	}

	public void setMetricKey(String metricKey) {
		this.metricKey = metricKey;
	}

	public double getActualValue() {
		return actualValue;
	}

	public void setActualValue(double actualValue) {
		this.actualValue = actualValue;
	}

	public String getComparator() {
		return comparator;
	}

	public void setComparator(String comparator) {
		this.comparator = comparator;
	}
	
	public double getErrorThreshold() {
		return errorThreshold;
	}

	public void setErrorThreshold(double errorThreshold) {
		this.errorThreshold = errorThreshold;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SonarQuestStatus getStatus() {
		return status;
	}

	public void setStatus(SonarQuestStatus status) {
		this.status = status;
	}

	public QualityGateRaid getQualityGateRaid() {
		return qualityGateRaid;
	}

	public void setQualityGateRaid(QualityGateRaid qualityGateRaid) {
		this.qualityGateRaid = qualityGateRaid;
	}
}
