package com.viadee.sonarquest.entities;

public class Monster {
	
	private String monsterName;
	
	private String monsterImage;
	
	public Monster(String monsterName, String monsterImage, double healthPoints, double demageTaken) {
		super();
		this.monsterName = monsterName;
		this.monsterImage = monsterImage;
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
}
