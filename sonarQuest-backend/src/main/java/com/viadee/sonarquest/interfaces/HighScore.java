package com.viadee.sonarquest.interfaces;

import java.sql.Date;

public interface HighScore {
	
	public void updateHighScore(Date scoreDay, long scorePoints);
	
	public Long getScorePoints();
	public void setScorePoints(Long scorePoints);

	public Date getScoreDay();
	public void setScoreDay(Date scoreDay);
}
