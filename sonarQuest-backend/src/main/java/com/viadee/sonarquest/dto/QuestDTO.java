package com.viadee.sonarquest.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.World;

public class QuestDTO {
	
	
	private Long id;

	private Date startdate;

	private Date enddate;

	private Boolean visible;

	private String title;

	private String story;

    private String creatorname;

	private QuestStateDTO status;

	private Long gold;

	private Long xp;

	private String image;
	
	private WorldDTO world;

	public QuestDTO() {
	}
	
	

}
