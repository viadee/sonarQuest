package com.viadee.sonarquest.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.rules.SonarQuestStatus;

public class TaskDTO {

	private Long id;

	private Date startdate;

	private Date enddate;

	private String title;

	private SonarQuestStatusDTO status;

	private Long gold;

	private Long xp;

	private String key;

	private WorldDTO world;

	public TaskDTO() {
	}

	public TaskDTO(Long id, Date startdate, Date enddate, String title, SonarQuestStatusDTO status, Long gold, Long xp,
			String key, WorldDTO world) {
		this.id = id;
		this.startdate = startdate;
		this.enddate = enddate;
		this.title = title;
		this.status = status;
		this.gold = gold;
		this.xp = xp;
		this.key = key;
		this.world = world;
	}

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

	public SonarQuestStatusDTO getStatus() {
		return status;
	}

	public void setStatus(SonarQuestStatusDTO status) {
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
		return world;
	}

	public void setWorld(WorldDTO world) {
		this.world = world;
	}

}
