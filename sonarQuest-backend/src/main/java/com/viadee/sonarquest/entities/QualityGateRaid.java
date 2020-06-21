package com.viadee.sonarquest.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;
import com.viadee.sonarquest.interfaces.HighScore;
import com.viadee.sonarquest.interfaces.Reward;

/**
 * A quality gate raid is a special raid game mode. 
 * It represent the SonarQube quality gate with conditions (= based on measure thresholds).
 * There is a reward (gold/xp) for every error-free day.
 * 
 * @see BaseRaid, SonarQubeProjectStatus
 */
@Entity
@Table(name = "quality_gate_raid")
public class QualityGateRaid extends BaseRaid implements HighScore, Reward {

	@Enumerated(EnumType.STRING)
	@Column(name = "sonarQubeStatus")
	private SonarQubeProjectStatusType sonarQubeStatus;

	@Column(name = "scoreDay")
	private Date scoreDay;

	@Column(name = "scorePoints")
	private Long scorePoints;

	@OneToMany(mappedBy = "qualityGateRaid", cascade = CascadeType.ALL)
	private List<Condition> conditions;
	
	@OneToMany(mappedBy = "raid", cascade = CascadeType.ALL)
	private List<QualityGateRaidRewardHistory> rewardHistory;	
	
	public QualityGateRaid() {
		super();
	}

	public QualityGateRaid(World world) {
		super("", "", "", 0l, 0l, world);
	}
	
	public SonarQubeProjectStatusType getSonarQubeStatus() {
		return sonarQubeStatus;
	}

	public void setSonarQubeStatus(SonarQubeProjectStatusType sonarQubeStatus) {
		this.sonarQubeStatus = sonarQubeStatus;
	}

	@Override
	public Long getScorePoints() {
		if(scorePoints==null)
			scorePoints = 0l;
		return scorePoints;
	}

	@Override
	public void setScoreDay(Date scoreDay) {
		this.scoreDay = scoreDay;
	}

	@Override
	public Date getScoreDay() {
		return scoreDay;
	}

	@Override
	public void setScorePoints(Long points) {
		this.scorePoints = points;
	}

	@JsonIgnore
	@Override
	public void updateHighScore(Date scoreDay, long points) {
		if(points > this.getScorePoints()) {
			this.scoreDay = scoreDay;
			this.scorePoints = points;	
		}
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public List<QualityGateRaidRewardHistory> getRewardHistory() {
		return rewardHistory;
	}

	public void setRewardHistory(List<QualityGateRaidRewardHistory> rewardHistory) {
		this.rewardHistory = rewardHistory;
	}
}
