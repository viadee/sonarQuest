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
@Table(name = "Skill_Tree_User")
public class SkillTreeUser {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "mail")
	private String mail;

	@OneToMany(mappedBy = "skillTreeUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserSkillToSkillTreeUser> userSkillToSkillTreeUser = new ArrayList<UserSkillToSkillTreeUser>(0);

	public SkillTreeUser() {
		super();
	}

	public SkillTreeUser(String mail) {
		super();
		this.mail = mail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public List<UserSkillToSkillTreeUser> getUserSkillToSkillTreeUser() {
		return userSkillToSkillTreeUser;
	}

	public void setUserSkillToSkillTreeUser(List<UserSkillToSkillTreeUser> userSkillToSkillTreeUser) {
		this.userSkillToSkillTreeUser = userSkillToSkillTreeUser;
	}
	
	public void addUserSkillToSkillTreeUser(UserSkillToSkillTreeUser userSkillToSkillTreeUser) {
		this.userSkillToSkillTreeUser.add(userSkillToSkillTreeUser);
	}
}
