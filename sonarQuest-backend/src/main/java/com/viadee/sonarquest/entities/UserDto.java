package com.viadee.sonarquest.entities;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
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

    public UserDto() {
    }

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
}
