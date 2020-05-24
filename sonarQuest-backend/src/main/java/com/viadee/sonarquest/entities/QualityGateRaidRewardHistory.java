package com.viadee.sonarquest.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;
import com.viadee.sonarquest.interfaces.HighScore;
import com.viadee.sonarquest.interfaces.Reward;
/**
 *	There is a QualityGateRaid reward for every error-free day.
 *	QualityGateRaidRewardHistory stores the status, reward and the number of error-free-day per DAY!
 */
@Entity
@Table
public class QualityGateRaidRewardHistory implements Reward, HighScore {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "sonarQubeStatus")
	private SonarQubeProjectStatusType sonarQubeStatus;
	
	@Column(name = "statusDate")
	private Date statusDate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "raid_id")
	private QualityGateRaid raid;

	@Column(name = "gold")
	private Long gold;
	
	@Column(name = "xp")
	private Long xp;
	
	@Column(name = "numberOfErrorFreeDays")
	private Long numberOfErrorFreeDays;
	
	public QualityGateRaidRewardHistory() {
	}

	public QualityGateRaidRewardHistory(Date statusDate, SonarQubeProjectStatusType sonarQubeStatus,
			QualityGateRaid raid, Long gold, Long xp, Long errorFreeDay) {
		super();
		this.statusDate = statusDate;
		this.sonarQubeStatus = sonarQubeStatus;
		this.raid = raid;
		this.gold = gold;
		this.xp = xp;
		this.numberOfErrorFreeDays = errorFreeDay;
	}
	
	public QualityGateRaidRewardHistory(Long gold, Long xp) {
		this.gold = gold;
		this.xp = xp;
	}

	@JsonIgnore
	@PreUpdate
	private void onPreUpdate() {
		// Verify reset bonus (= Gold, XP)   
		if (!SonarQubeProjectStatusType.OK.equals(sonarQubeStatus)) {
			setGold(0l);
			setXp(0l);
			setNumberOfErrorFreeDays(0l);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public QualityGateRaid getRaid() {
		return raid;
	}

	public void setRaid(QualityGateRaid raid) {
		this.raid = raid;
	}

	public SonarQubeProjectStatusType getSonarQubeStatus() {
		return sonarQubeStatus;
	}

	public void setSonarQubeStatus(SonarQubeProjectStatusType sonarQubeStatus) {
		this.sonarQubeStatus = sonarQubeStatus;
	}

	public Long getGold() {
		if(gold == null)
			this.gold = 0l;
		return gold;
	}
	
	@Override
	public void setGold(Long gold) {
		this.gold = gold;
	}

	public Long getXp() {
		if(xp == null)
			this.xp = 0l;
		return xp;
	}

	public void setXp(Long xp) {
		this.xp = xp;
	}
	
	public Long getNumberOfErrorFreeDays() {
		if(numberOfErrorFreeDays == null)
			this.numberOfErrorFreeDays = 0l;
		return numberOfErrorFreeDays;
	}
	
	public void setNumberOfErrorFreeDays(Long numberOfErrorFreeDays) {
		this.numberOfErrorFreeDays = numberOfErrorFreeDays;
	}

	@Override
	public void updateHighScore(Date scoreDay, long scorePoints) {
		setScoreDay(scoreDay);
		setScorePoints(scorePoints);
	}

	@Override
	public Long getScorePoints() {
		return getNumberOfErrorFreeDays();
	}

	@Override
	public void setScorePoints(Long scorePoints) {
		setNumberOfErrorFreeDays(scorePoints);
	}

	@Override
	public Date getScoreDay() {
		return getStatusDate();
	}

	@Override
	public void setScoreDay(Date scoreDay) {
		setStatusDate(scoreDay);
	}
}
