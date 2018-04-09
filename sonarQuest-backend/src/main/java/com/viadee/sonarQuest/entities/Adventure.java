package com.viadee.sonarQuest.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Adventure")
public class Adventure {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name="story")
    private String story;

    @Column(name="status")
    private String status;

    @Column(name="gold")
    private Long gold;

    @Column(name="xp")
    private Long xp;

    @ManyToOne()
    @JoinColumn(name="world_id")
    private World world;

    @OneToMany(mappedBy="adventure",cascade = CascadeType.ALL)
    private List<Quest> quests;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Adventure_Developer", joinColumns = @JoinColumn(name = "adventure_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "developer_id", referencedColumnName = "id"))
    private List<Developer> developers;

    public Adventure() {
    }

    public Adventure(String title, String story, String status, Long gold, Long xp) {
        this.title = title;
        this.story = story;
        this.status = status;
        this.gold = gold;
        this.xp = xp;
    }

    public Adventure(Long id, String title, String story, String status, Long gold, Long xp, World world, List<Quest> quests, List<Developer> developers) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.status = status;
        this.gold = gold;
        this.xp = xp;
        this.world = world;
        this.quests = quests;
        this.developers = developers;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    @JsonIgnore
    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

	public synchronized void addDeveloper(Developer developer) {
    	List<Developer> developers = this.getDevelopers();

    	if (developers == null) {
    		developers = new ArrayList<Developer>();
    		developers.add(developer);
    		this.setDevelopers(developers);
    	} else {
    		this.developers.add(developer);
    	}

    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
