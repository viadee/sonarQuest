package com.viadee.sonarquest.dto;

import java.sql.Date;

import com.viadee.sonarquest.entities.RaidLeaderboard;
import com.viadee.sonarquest.entities.UserDto;

public class RaidLeaderboardDTO {
	private Date scoreDay;
	private Long scoreSolvedTasks;
	private Long scoreGold;
	private Long scoreXp;
	private UserDto user;
	
	public RaidLeaderboardDTO(RaidLeaderboard leaderBoard) {
		this(leaderBoard.getScoreGold(), leaderBoard.getScoreXp(), new UserDto(leaderBoard.getUser()),
				leaderBoard.getScoreDay(), leaderBoard.getScoreSolvedTasks());
	}

	public RaidLeaderboardDTO(Long scoreGold, Long scoreXp, UserDto user, Date scoreDay, Long scoreSolvedTasks) {
		this.scoreGold = scoreGold;
		this.scoreXp = scoreXp;
		this.user = user;
		this.scoreDay = scoreDay;
		this.scoreSolvedTasks = scoreSolvedTasks;
	}

	public Long getScoreGold() {
		return scoreGold;
	}

	public void setScoreGold(Long scoreGold) {
		this.scoreGold = scoreGold;
	}

	public Long getScoreXp() {
		return scoreXp;
	}

	public void setScoreXp(Long scoreXp) {
		this.scoreXp = scoreXp;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Date getScoreDay() {
		return scoreDay;
	}

	public void setScoreDay(Date scoreDay) {
		this.scoreDay = scoreDay;
	}

	public Long getScoreSolvedTasks() {
		return scoreSolvedTasks;
	}

	public void setScoreSolvedTasks(Long scoreSolvedTasks) {
		this.scoreSolvedTasks = scoreSolvedTasks;
	}

}
