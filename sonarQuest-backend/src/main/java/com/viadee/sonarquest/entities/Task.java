package com.viadee.sonarquest.entities;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.rules.SonarQuestStatus;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "task_type")
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "startdate")
    private Date startdate;

    @Column(name = "enddate")
    private Date enddate;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SonarQuestStatus status;

    @Column(name = "gold")
    private Long gold;

    @Column(name = "xp")
    private Long xp;

    @Column(name = "task_key")
    private String key;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "quest_id")
    private Quest quest;
    
    @ManyToOne
    @JoinColumn(name = "world_id")
    private World world;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "participation_id")
    private Participation participation;
    
    public Task() {
	}

    public Task(String title, SonarQuestStatus status, Long gold, Long xp, World world) {
		super();
		this.title = title;
		this.status = status;
		this.gold = gold;
		this.xp = xp;
		this.world = world;
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
    
    public SonarQuestStatus getStatus() {
		return status;
	}

	public void setStatus(SonarQuestStatus status) {
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

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(final Quest quest) {
        this.quest = quest;
    }

    public Participation getParticipation() {
        return participation;
    }
    
	public String getParticipant() {
		if(participation == null)
			return "";
		return participation.getUser().getUsername();
	}

    public void setParticipation(final Participation participation) {
        this.participation = participation;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(final World world) {
        this.world = world;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
    
    public static class TaskBuilder {
        
        private Date startdate;
        private Date enddate;
        private String title;
        private SonarQuestStatus status;
        private World world;
        private Long xp;
        private Long gold;
        private Quest quest;

        public TaskBuilder() {    
        }
          
        public TaskBuilder(Date startdate, Date enddate, String title, SonarQuestStatus status, World world, Long xp, Long gold, Quest quest) {    
          this.startdate = startdate; 
          this.enddate = enddate; 
          this.title = title; 
          this.status = status; 
          this.world = world; 
          this.xp = xp; 
          this.gold = gold; 
          this.quest = quest;             
        }
            
        public TaskBuilder startdate(Date startdate){
          this.startdate = startdate;
          return TaskBuilder.this;
        }

        public TaskBuilder enddate(Date enddate){
          this.enddate = enddate;
          return TaskBuilder.this;
        }

        public TaskBuilder title(String title){
          this.title = title;
          return TaskBuilder.this;
        }

        public TaskBuilder status(SonarQuestStatus status){
          this.status = status;
          return TaskBuilder.this;
        }

        public TaskBuilder world(World world){
          this.world = world;
          return TaskBuilder.this;
        }

        public TaskBuilder xp(Long xp){
          this.xp = xp;
          return TaskBuilder.this;
        }

        public TaskBuilder gold(Long gold){
          this.gold = gold;
          return TaskBuilder.this;
        }

        public TaskBuilder quest(Quest quest){
          this.quest = quest;
          return TaskBuilder.this;
        }

        public Task build() {

            return new Task(this);
        }
      }

      private Task(TaskBuilder builder) {
        this.startdate = builder.startdate; 
        this.enddate = builder.enddate; 
        this.title = builder.title; 
        this.status = builder.status; 
        this.world = builder.world; 
        this.xp = builder.xp; 
        this.gold = builder.gold; 
        this.quest = builder.quest;     
      }

}
