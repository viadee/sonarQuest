package com.viadee.sonarquest.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.constants.QuestState;

@Entity
@Table(name = "Quest")
public class Quest {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "startdate")
	private Date startdate;

	@Column(name = "enddate")
	private Date enddate;

	@Column(name = "visible")
	private Boolean visible;

	@Column(name = "title")
	private String title;

	@Column(name = "story")
	private String story;

    @Column(name = "creator_name")
    private String creatorname;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private QuestState status;

	@Column(name = "gold")
	private Long gold;

	@Column(name = "xp")
	private Long xp;

	@Column(name = "image")
	private String image;

	@ManyToOne()
	@JoinColumn(name = "world_id")
	private World world;

	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "adventure_id")
	private Adventure adventure;
	
	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "raid_id")
	private Raid raid;
	
	@OneToMany(mappedBy = "quest", fetch = FetchType.LAZY)
	private List<Task> tasks;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "quest")
	private List<Participation> participations;
	
	public Quest() {
	}

	public Quest(final String title, final String story, final QuestState status, final Long gold, final Long xp,
			final String image, final Boolean visible) {
		this.title = title;
		this.story = story;
		this.status = status;
		this.gold = gold;
		this.xp = xp;
		this.image = image;
		this.visible = visible;
	}

	public Quest(final String title, final String story, final QuestState status, final Long gold, final Long xp,
			final String image, final World world, final Boolean visible, final Adventure adventure,
			final List<Task> tasks, final List<Participation> participations) {
		this.title = title;
		this.story = story;
		this.status = status;
		this.gold = gold;
		this.xp = xp;
		this.world = world;
		this.visible = visible;
		this.adventure = adventure;
		this.tasks = tasks;
		this.participations = participations;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getStory() {
		return story;
	}

	public void setStory(final String story) {
		this.story = story;
	}

	public QuestState getStatus() {
		return status;
	}

	public void setStatus(final QuestState status) {
		this.status = status;
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

	public String getImage() {
		return image;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(final World world) {
		this.world = world;
	}

	public Adventure getAdventure() {
		return adventure;
	}

	public void setAdventure(final Adventure adventure) {
		this.adventure = adventure;
	}

	public List<Task> getTasks() {
		if(tasks == null)
			this.tasks = new ArrayList<Task>();
		return tasks;
	}

	public void setTasks(final List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(final List<Participation> participations) {
		this.participations = participations;
	}

	/**
     * Looks up the usernames of all participants in this quests and returns them in a list.
	 */
	public List<String> getParticipants() {
		if (participations != null) {
			return participations.stream().map(Participation::getUser).map(User::getUsername)
					.collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

    public String getCreatorName() {
        return creatorname;
    }

    public void setCreatorName(String creatorname) {
        this.creatorname = creatorname;
    }

	public Raid getRaid() {
		return raid;
	}

	public void setRaid(Raid raid) {
		this.raid = raid;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
