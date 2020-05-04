package com.viadee.sonarquest.interfaces;

import java.sql.Date;

public interface LeaderboardScore {
	
	public String getUsername();
	public Date getScoreDay();
	
	public Long getScoreGold();
	public void setScoreGold(Long scoreGold);
	public void addScoreGold(Long scoreGold);
	
	public Long getScoreXp();
	public void setScoreXp(Long scoreXp);
	public void addScoreXp(Long scoreXp);

}
