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
@Table(name = "User_Skill_Group")
public class UserSkillGroup {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "group_name")
    private String name;

//    @OneToMany(mappedBy = "userSkillGroup", cascade = CascadeType.ALL)
//    private List<UserSkill> userskills = new ArrayList<UserSkill>(0);

    public UserSkillGroup() {
    }

    public UserSkillGroup(Long id, String name, List<UserSkill> userskills) {
        super();
        this.id = id;
        this.name = name;
        //this.userskills = userskills;
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

//    public List<UserSkill> getUserskills() {
//        return userskills;
//    }
//
//    public void setUserskills(List<UserSkill> userskills) {
//        this.userskills = userskills;
//    }
//    
//    public void addUserSkills(UserSkill userSkill) {
//        this.userskills.add(userSkill);
//    }

}
