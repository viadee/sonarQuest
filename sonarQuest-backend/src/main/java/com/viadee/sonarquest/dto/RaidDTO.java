package com.viadee.sonarquest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.viadee.sonarquest.constants.RaidState;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.RaidLeaderboard;
import com.viadee.sonarquest.entities.World;

public class RaidDTO {
	private Long id;
	private Boolean visible;
	private String title;
	private String monsterName;
	private String monsterImage;
	private RaidState status;
	private Long gold;
	private Long xp;
	private World world;
	private List<Quest> quests;
	private ProgressDTO raidProgress;
	private List<RaidLeaderboardDTO> raidLeaderboardList;

	public RaidDTO(Raid raid) {
		this(raid.getId(), raid.getVisible(), raid.getTitle(), raid.getMonsterName(), raid.getMonsterImage(),
				raid.getStatus(), raid.getGold(), raid.getXp(), raid.getWorld(), raid.getQuests(),
				raid.getRaidLeaderboadList());
	}
	
	public RaidDTO(Raid raid, ProgressDTO raidProgress) {
		this(raid.getId(), raid.getVisible(), raid.getTitle(), raid.getMonsterName(), raid.getMonsterImage(),
				raid.getStatus(), raid.getGold(), raid.getXp(), raid.getWorld(), raid.getQuests(),
				raid.getRaidLeaderboadList());
		this.raidProgress = raidProgress;
	}

	public RaidDTO(Long id, Boolean visible, String title, String monsterName, String monsterImage, RaidState status,
			Long gold, Long xp, World world, List<Quest> quests, List<RaidLeaderboard> raidLeaderboardList) {
		super();
		this.id = id;
		this.visible = visible;
		this.title = title;
		this.monsterName = monsterName;
		this.monsterImage = monsterImage;
		this.status = status;
		this.gold = gold;
		this.xp = xp;
		this.world = world;
		this.quests = quests;
		this.raidProgress = new ProgressDTO(0, 0);
		this.raidLeaderboardList = raidLeaderboardList.stream().map(score -> new RaidLeaderboardDTO(score))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public RaidState getStatus() {
		return status;
	}

	public void setStatus(RaidState status) {
		this.status = status;
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

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public List<Quest> getQuests() {
		return quests;
	}

	public void setQuests(List<Quest> quests) {
		this.quests = quests;
	}

	public ProgressDTO getRaidProgress() {
		return raidProgress;
	}

	public void setRaidProgress(ProgressDTO raidProgress) {
		this.raidProgress = raidProgress;
	}

	public List<RaidLeaderboardDTO> getRaidLeaderboardList() {
		return raidLeaderboardList;
	}

	public void setRaidLeaderboardList(List<RaidLeaderboardDTO> raidLeaderboardList) {
		this.raidLeaderboardList = raidLeaderboardList;
	}
}
