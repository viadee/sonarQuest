package com.viadee.sonarQuest.dtos;

import com.viadee.sonarQuest.entities.*;

import java.util.List;

public class DeveloperDto {


    private Long id;

    private String username;

    private Long gold;

    private Long xp;

    private Level level;

    private String picture;

    private String aboutMe;

    private AvatarClass avatarClass;

    private AvatarRace avatarRace;

    private List<Artefact> artefacts;

    private List<Adventure> adventures;

    private List<Participation> participations;
    
    private World world;

    public DeveloperDto() {
    }

    public DeveloperDto(Long id, String username, Long gold, Long xp, Level level, String picture, String aboutMe,
			AvatarClass avatarClass, AvatarRace avatarRace, List<Artefact> artefacts, List<Adventure> adventures,
			List<Participation> participations, World world) {
		super();
		this.id = id;
		this.username = username;
		this.gold = gold;
		this.xp = xp;
		this.level = level;
		this.picture = picture;
		this.aboutMe = aboutMe;
		this.avatarClass = avatarClass;
		this.avatarRace = avatarRace;
		this.artefacts = artefacts;
		this.adventures = adventures;
		this.participations = participations;
		this.setWorld(world);
	}

	public DeveloperDto(String username) {
		this.username = username;
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

    public String getPicture() {
        return picture;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public List<Artefact> getArtefacts() {
        return artefacts;
    }

    public void setArtefacts(List<Artefact> artefacts) {
        this.artefacts = artefacts;
    }

    public List<Adventure> getAdventures() {
        return adventures;
    }

    public void setAdventures(List<Adventure> adventures) {
        this.adventures = adventures;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
