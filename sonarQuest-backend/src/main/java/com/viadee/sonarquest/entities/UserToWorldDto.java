package com.viadee.sonarquest.entities;


public class UserToWorldDto {
	private Long userId;
	private Long worldId;
	private String worldName;
	private Boolean joined;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getWorldId() {
		return worldId;
	}
	public void setWorldId(Long worldId) {
		this.worldId = worldId;
	}
	public String getWorldName() {
		return worldName;
	}
	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}
	public Boolean getJoined() {
		return joined;
	}
	public void setJoined(Boolean joined) {
		this.joined = joined;
	}

}