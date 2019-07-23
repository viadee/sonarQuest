package com.viadee.sonarquest.skillTree.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_skill_id")
    @JsonIgnore
    private UserSkill userSkill;
    
    @Column(name="added_at")
    private Timestamp addedAt;   

    public SonarRule() {
    }

    public SonarRule(String name, String key, UserSkill userSkill) {
        this.name = name;
        this.key = key;
        this.userSkill = userSkill;
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

	public UserSkill getUserSkill() {
		return userSkill;
	}

	public void setUserSkill(UserSkill userSkill) {
		this.userSkill = userSkill;
	}

	public Timestamp getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Timestamp addedAT) {
		this.addedAt = addedAT;
	}

}
