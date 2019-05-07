package com.viadee.sonarquest.entities;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;

import com.viadee.sonarquest.controllers.UserController;


public class UserDto {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDto.class);

	@Autowired
	private UserController userController;
	
	private Long id;
    private String username;
    private String mail;
    private Role role;
    private Blob picture;
    private String aboutMe;
    private AvatarClass avatarClass;
    private AvatarRace avatarRace;
    private Long gold;
    private Long xp;
    private Level level;
    private Long currentWorldId;
    private Timestamp lastLogin;
    
    @Value("${avatar.directory}")
    private String avatarDirectoryPath;
    
    public UserDto() {}
    
	public UserDto(User user) {
		String path = "/avatar/";
		
		if (user.getPicture() != null) {
            String avatarFileName = path + user.getPicture();
            InputStream inputStream = SpringApplication.class.getResourceAsStream(avatarFileName);
            try {
                 this.picture = new javax.sql.rowset.serial.SerialBlob(IOUtils.toByteArray(inputStream));
            } catch (IOException | SQLException ex) {
                LOGGER.error("Avatar file not found: " + avatarFileName, ex);
            }
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
		this.currentWorldId = user.getCurrentWorld().getId();
		this.lastLogin = user.getLastLogin();
	}

	public UserController getUserController() {
		return userController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
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

	public Blob getPicture() {
		return picture;
	}

	public void setPicture(Blob picture) {
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

	public String getAvatarDirectoryPath() {
		return avatarDirectoryPath;
	}

	public void setAvatarDirectoryPath(String avatarDirectoryPath) {
		this.avatarDirectoryPath = avatarDirectoryPath;
	}
	
	
}
