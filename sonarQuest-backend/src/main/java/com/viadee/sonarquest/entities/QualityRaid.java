package com.viadee.sonarquest.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.viadee.sonarquest.constants.AdventureState;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;

@Entity
@DiscriminatorValue("QUALITY")
@Table(name = "quality_raid")
public class QualityRaid extends Raid {

	@Enumerated(EnumType.STRING)
	@Column(name = "sonarQubeStatus")
	private SonarQubeProjectStatusType sonarQubeStatus;

	@Column(name = "amountOfError")
	private int amountOfError = 0;

	@Column(name = "amountOfWarn")
	private int amountOfWarn = 0;

	@Column(name = "amountOfOk")
	private int amountOfOk = 0;

	public QualityRaid() {
	}

	public QualityRaid(SonarQubeProjectStatusType sonarQubeStatus, int amountOfError, int amountOfWarn,
			int amountOfOk) {
		this.sonarQubeStatus = sonarQubeStatus;
		this.amountOfError = amountOfError;
		this.amountOfWarn = amountOfWarn;
		this.amountOfOk = amountOfOk;
	}
	
	public QualityRaid(final String title, final String story, final AdventureState status, final Long gold,
            final Long xp, World world) {
		super(title, story, status, gold, xp, world);
		this.sonarQubeStatus = SonarQubeProjectStatusType.NONE;
	}
	
	public SonarQubeProjectStatusType getSonarQubeStatus() {
		return sonarQubeStatus;
	}

	public void setSonarQubeStatus(SonarQubeProjectStatusType sonarQubeStatus) {
		this.sonarQubeStatus = sonarQubeStatus;
	}

	public int getAmountOfError() {
		return amountOfError;
	}

	public void setAmountOfError(int amountOfError) {
		this.amountOfError = amountOfError;
	}

	public int getAmountOfWarn() {
		return amountOfWarn;
	}

	public void setAmountOfWarn(int amountOfWarn) {
		this.amountOfWarn = amountOfWarn;
	}

	public int getAmountOfOk() {
		return amountOfOk;
	}

	public void setAmountOfOk(int amountOfOk) {
		this.amountOfOk = amountOfOk;
	}

}
