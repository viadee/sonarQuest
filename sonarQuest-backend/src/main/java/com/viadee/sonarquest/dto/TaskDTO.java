package com.viadee.sonarquest.dto;

import java.sql.Date;

import com.viadee.sonarquest.rules.SonarQuestStatus;

public class TaskDTO {
	private Long id;

	private Date startdate;

	private Date enddate;

	private String title;

	private SonarQuestStatus status;

	private Long gold;

	private Long xp;

	private String key;

	private WorldDTO worldDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
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

	public Long getGold() {
		return gold;
	}

	public void setGold(Long gold) {
		this.gold = gold;
	}

	public Long getXp() {
		return xp;
	}

	public void setXp(Long xp) {
		this.xp = xp;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public WorldDTO getWorld() {
		return worldDto;
	}

	public void setWorldDTO(WorldDTO worldDto) {
		this.worldDto = worldDto;
	}

}
