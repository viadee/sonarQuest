package com.viadee.sonarquest.interfaces;

import java.sql.Date;
import java.util.List;

import com.viadee.sonarquest.constants.AdventureState;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.World;

public interface RaidI extends PositivePoints {
	// TODO id, startDate and endDate in abstract basic class
	public Long getId();
	public Date getEnddate();
	public Date getStartdate();

	public String getTitle();
	public AdventureState getStatus();
	public List<Quest> getQuests();
	public World getWorld();
	public Boolean getVisible();
	public String getDescription();
	public String getMonsterName();
	public String getMonsterImage();
}
