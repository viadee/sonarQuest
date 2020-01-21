package com.viadee.sonarquest.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.constants.AdventureState;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Raid_TYPE")
@Entity
@Table(name = "raid")
public class Raid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AdventureState status;
    
    @Column(name = "gold")
    private Long gold;

    @Column(name = "xp")
    private Long xp;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "world_id")
    private World world;

    @OneToMany(mappedBy = "adventure", cascade = CascadeType.ALL)
    private List<Quest> quests;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Adventure_User", joinColumns = @JoinColumn(name = "adventure_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users;
    
    public Raid() {
	}
    
    public Raid(final String title, final String story, final AdventureState status, final Long gold,
            final Long xp, World world) {
    	super();
        this.title = title;
        this.story = story;
        this.status = status;
        this.gold = gold;
        this.xp = xp;
        this.world = world;
        this.setStartdate(new Date(System.currentTimeMillis()));
    }
    
    public Raid(final Long id, final String title, final String story, final AdventureState status,
            final Long gold,
            final Long xp, final World world,
            final List<Quest> quests, final List<User> users) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.status = status;
        this.gold = gold;
        this.xp = xp;
        this.world = world;
        this.quests = quests;
        this.users = users;
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

    public AdventureState getStatus() {
        return status;
    }

    public void setStatus(final AdventureState status) {
        this.status = status;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(final List<Quest> quests) {
        this.quests = quests;
    }

    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

    public synchronized void addUser(final User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(final World world) {
        this.world = world;
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
}
