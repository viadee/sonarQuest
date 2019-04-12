package com.viadee.sonarquest.skillTree.entities;

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
    
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "User_Skill_Group_Following", joinColumns = @JoinColumn(name = "user_skill_group_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "following_user_skill_group_id", referencedColumnName = "id"))
    private List<UserSkillGroup> followingUserSkillGroups = new ArrayList<UserSkillGroup>(0);
    
    @Column(name = "is_root")
    private boolean isRoot;
    
    @Column(name="group_icon")
    private String icon;

//    @OneToMany(mappedBy = "userSkillGroup", cascade = CascadeType.ALL)
//    private List<UserSkill> userskills = new ArrayList<UserSkill>(0);

    public UserSkillGroup() {
    }

    public UserSkillGroup(Long id, String name, boolean isRoot) {
        this.id = id;
        this.name = name;
        this.isRoot = isRoot;
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

	public List<UserSkillGroup> getFollowingUserSkillGroups() {
		return followingUserSkillGroups;
	}

	public void setFollowingUserSkillGroups(List<UserSkillGroup> followingUserSkillGroups) {
		this.followingUserSkillGroups = followingUserSkillGroups;
	}
    
	public void addFollowingUserSkillGroup(UserSkillGroup userSkillGroup) {
		this.followingUserSkillGroups.add(userSkillGroup);
	}
	
	public void removeFollowingUserSkillGroup(UserSkillGroup userSkillGroup) {
		this.followingUserSkillGroups.remove(userSkillGroup);
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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
