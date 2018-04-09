package com.viadee.sonarQuest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Level")
public class Level {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "min")
    private  Long min;

    @Column(name = "max")
    private Long max;

    @OneToMany(mappedBy="level",cascade = CascadeType.ALL)
    private List<Developer> developers;

    @OneToMany(mappedBy="minLevel",cascade = CascadeType.ALL)
    private List<Artefact> artefacts;


    protected Level() {
    }
    

    public Level(Long min, Long max) {
        this.min = min;
        this.max = max;
    }

    public Level(Long min, Long max, List<Developer> developers, List<Artefact> artefacts) {
        this.min = min;
        this.max = max;
        this.developers = developers;
        this.artefacts = artefacts;
    }

    public Level(Long minLevel) {
		this.min = minLevel;
	}


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  
    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    @JsonIgnore
    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    @JsonIgnore
    public List<Artefact> getArtefacts() {
        return artefacts;
    }

    public void setArtefacts(List<Artefact> artefacts) {
        this.artefacts = artefacts;
    }
}
