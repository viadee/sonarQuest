package com.viadee.sonarquest.skillTree.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Sonar_Rule")
public class SonarRule {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "rule_name")
    private String name;

    @Column(name = "rule_key")
    private String key;
    

    public SonarRule() {
    }

    public SonarRule(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public String getKey() {
        return key;
    }

    
    public void setKey(String key) {
        this.key = key;
    }

}
