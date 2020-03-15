package com.viadee.sonarquest.dto;

import java.util.List;

import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.World;

public class QuestDTO {
	private Long id;
	private String title;
	private String story;
	private String creatorname;
	private QuestState status;
	private Long gold;
	private Long xp;
	private String image;
	private World world;
	private Adventure adventure;
	private RaidDTO raid;
	private List<Task> tasks;
	private ProgressDTO questProgress;
	private List<String> participations;

	public QuestDTO(Quest quest) {
		this(quest.getId(), quest.getTitle(), quest.getStory(), quest.getCreatorName(), quest.getStatus(),
				quest.getGold(), quest.getXp(), quest.getImage(), quest.getWorld(), quest.getAdventure(),
				new RaidDTO(quest.getRaid()), quest.getTasks(), new ProgressDTO(0, 0), quest.getParticipants());
	}

	public QuestDTO(Long id, String title, String story, String creatorname, QuestState status, Long gold, Long xp,
			String image, World world, Adventure adventure, RaidDTO raid, List<Task> tasks, ProgressDTO questProgress,
			List<String> participations) {
		super();
		this.id = id;
		this.title = title;
		this.story = story;
		this.creatorname = creatorname;
		this.status = status;
		this.gold = gold;
		this.xp = xp;
		this.image = image;
		this.world = world;
		this.adventure = adventure;
		this.raid = raid;
		this.tasks = tasks;
		this.questProgress = questProgress;
		this.participations = participations;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getCreatorname() {
		return creatorname;
	}

	public void setCreatorname(String creatorname) {
		this.creatorname = creatorname;
	}

	public QuestState getStatus() {
		return status;
	}

	public void setStatus(QuestState status) {
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Adventure getAdventure() {
		return adventure;
	}

	public void setAdventure(Adventure adventure) {
		this.adventure = adventure;
	}

	public RaidDTO getRaid() {
		return raid;
	}

	public void setRaid(RaidDTO raid) {
		this.raid = raid;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public ProgressDTO getQuestProgress() {
		return questProgress;
	}

	public void setQuestProgress(ProgressDTO raidProgress) {
		this.questProgress = raidProgress;
	}

	public List<String> getParticipations() {
		return participations;
	}

	public void setParticipations(List<String> participations) {
		this.participations = participations;
	}
}
