package com.viadee.sonarquest.entities;

import java.sql.Timestamp;


public class UserDto {
	
	private Long id;
    private String username;
    private String mail;
    private Role role;
    private String picture;
    private String aboutMe;
    private AvatarClass avatarClass;
    private AvatarRace avatarRace;
    private Long gold;
    private Long xp;
    private Level level;
    private Long currentWorldId;
    private Timestamp lastLogin;
    
    public UserDto() {}
    
	public UserDto(User user) {		
		
		if (user.getCurrentWorld() != null) {
			this.currentWorldId = user.getCurrentWorld().getId();
		} else {
			this.currentWorldId = null;
		}
		
		this.id = user.getId();
		this.username = user.getUsername();
		this.mail = user.getMail();
		this.role = user.getRole();
		this.aboutMe = user.getAboutMe();
		this.avatarClass = user.getAvatarClass();
		this.avatarRace = user.getAvatarRace();
		this.gold = user.getGold();
		this.xp = user.getXp();
		this.level = user.getLevel();
		this.lastLogin = user.getLastLogin();
		this.picture = user.getPicture();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public AvatarClass getAvatarClass() {
		return avatarClass;
	}

	public void setAvatarClass(AvatarClass avatarClass) {
		this.avatarClass = avatarClass;
	}

	public AvatarRace getAvatarRace() {
		return avatarRace;
	}

	public void setAvatarRace(AvatarRace avatarRace) {
		this.avatarRace = avatarRace;
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

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Long getCurrentWorldId() {
		return currentWorldId;
	}

	public void setCurrentWorldId(Long currentWorldId) {
		this.currentWorldId = currentWorldId;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	
}
