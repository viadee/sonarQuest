package com.viadee.sonarquest.dto;

import java.sql.Date;

import com.viadee.sonarquest.interfaces.HighScore;

public class HighScoreDTO implements HighScore {

	private Date scoreDay;

	private Long scorePoints;

	public HighScoreDTO(Date scoreDay, Long scorePoints) {
		super();
		this.scoreDay = scoreDay;
		this.scorePoints = scorePoints;
	}

	@Override
	public Long getScorePoints() {
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

	/**
	 * Update highScore if the points are higher than before
	 */
	@Override
	public void updateHighScore(Date scoreDay, long points) {
		if(points > this.scorePoints) {
			this.scoreDay = scoreDay;
			this.scorePoints = points;	
		}
	}
}
