package com.viadee.sonarquest.dto;

import java.util.Date;

public class SolvedTaskHistoryDTO {

	private Date date;
	private long solvedTasksForDay;
	private long totalSolvedTasks;

	public SolvedTaskHistoryDTO(Date date, long solvedTasksForDay, long totalSolvedTasks) {
		super();
		this.date = date;
		this.solvedTasksForDay = solvedTasksForDay;
		this.totalSolvedTasks = totalSolvedTasks;
	}

	public SolvedTaskHistoryDTO() {
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getSolvedTasksForDay() {
		return solvedTasksForDay;
	}

	public void setSolvedTasksForDay(long solvedTasksForDay) {
		this.solvedTasksForDay = solvedTasksForDay;
	}

	public long getTotalSolvedTasks() {
		return totalSolvedTasks;
	}

	public void setTotalSolvedTasks(long totalSolvedTasks) {
		this.totalSolvedTasks = totalSolvedTasks;
	}

}
