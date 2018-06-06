package com.viadee.sonarQuest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "picture")
    private String picture;

    @Column(name = "about_me")
    private String aboutMe;

    @ManyToOne
    @JoinColumn(name = "avatar_class_id")
    private AvatarClass avatarClass;

    @ManyToOne
    @JoinColumn(name = "avatar_race_id")
    private AvatarRace avatarRace;

    @Column(name = "gold")
    private Long gold;

    @Column(name = "xp")
    private Long xp;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(final String picture) {
        this.picture = picture;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(final String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public AvatarClass getAvatarClass() {
        return avatarClass;
    }

    public void setAvatarClass(final AvatarClass avatarClass) {
        this.avatarClass = avatarClass;
    }

    public AvatarRace getAvatarRace() {
        return avatarRace;
    }

    public void setAvatarRace(final AvatarRace avatarRace) {
        this.avatarRace = avatarRace;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(final Long gold) {
        this.gold = gold;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(final Long xp) {
        this.xp = xp;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }

}
