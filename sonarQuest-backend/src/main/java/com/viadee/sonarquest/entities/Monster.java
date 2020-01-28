package com.viadee.sonarquest.entities;

public class Monster {
	private String monsterName;
	private String monsterImage;
	private double healthPoints;
	private double demageTaken;
	
	public Monster(String monsterName, String monsterImage, double healthPoints, double demageTaken) {
		super();
		this.monsterName = monsterName;
		this.monsterImage = monsterImage;
		this.healthPoints = healthPoints;
		this.demageTaken = demageTaken;
	}
	
	public String getMonsterName() {
		return monsterName;
	}
	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}
	public String getMonsterImage() {
		return monsterImage;
	}
	public void setMonsterImage(String monsterImage) {
		this.monsterImage = monsterImage;
	}
	public double getHealthPoints() {
		return healthPoints;
	}
	public void setHealthPoints(double healthPoints) {
		this.healthPoints = healthPoints;
	}
	public double getDemageTaken() {
		return demageTaken;
	}
	public void setDemageTaken(double demageTaken) {
		this.demageTaken = demageTaken;
	}
}
