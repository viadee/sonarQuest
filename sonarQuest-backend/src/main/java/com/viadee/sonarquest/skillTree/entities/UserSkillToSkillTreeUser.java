package com.viadee.sonarquest.skillTree.entities;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "User_Skill_To_Skill_Tree_User")
public class UserSkillToSkillTreeUser {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "learned_on")
	private Timestamp learnedOn;

	@Column(name = "repeats")
	private int repeats;

	@Column(name = "score")
	private Double score;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_skill_id")
	private UserSkill userSkill;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "skill_tree_user_id")
	private SkillTreeUser skillTreeUser;

	public UserSkillToSkillTreeUser() {
	}

	public UserSkillToSkillTreeUser(UserSkill userSkill, int repeats) {
		this.userSkill = userSkill;
		this.repeats = repeats;
	}
	
	public UserSkillToSkillTreeUser(UserSkill userSkill, int repeats, Timestamp learndOn) {
		this.userSkill = userSkill;
		this.repeats = repeats;
		this.learnedOn = learndOn;
	}
	public UserSkillToSkillTreeUser(UserSkill userSkill, int repeats, Timestamp learndOn, SkillTreeUser skillTreeUser) {
		this.userSkill = userSkill;
		this.repeats = repeats;
		this.learnedOn = learndOn;
		this.skillTreeUser = skillTreeUser;
	}

	public UserSkillToSkillTreeUser(Timestamp learnedOn, int repeats, UserSkill userSkill, SkillTreeUser skillTreeUser,
			Double score) {
		this.learnedOn = learnedOn;
		this.repeats = repeats;
		this.userSkill = userSkill;
		this.skillTreeUser = skillTreeUser;
		this.score = score;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserSkillToSkillTreeUser))
			return false;
		UserSkillToSkillTreeUser that = (UserSkillToSkillTreeUser) o;
		return Objects.equals(userSkill.getId(), that.userSkill.getId())
				&& Objects.equals(skillTreeUser.getId(), that.skillTreeUser.getId())
				&& Objects.equals(learnedOn, that.learnedOn) && Objects.equals(repeats, that.repeats)
				&& Objects.equals(score, that.score);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userSkill.getName(), skillTreeUser.getMail(), learnedOn, repeats);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getLearnedOn() {
		return learnedOn;
	}

	public void setLearnedOn(Timestamp learnedOn) {
		this.learnedOn = learnedOn;
	}

	public int getRepeats() {
		return repeats;
	}

	public void setRepeats(int repeats) {
		this.repeats = repeats;
	}

	public UserSkill getUserSkill() {
		return userSkill;
	}

	public void setUserSkill(UserSkill userSkill) {
		this.userSkill = userSkill;
	}

	public SkillTreeUser getSkillTreeUser() {
		return skillTreeUser;
	}

	public void setSkillTreeUser(SkillTreeUser skillTreeUser) {
		this.skillTreeUser = skillTreeUser;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
