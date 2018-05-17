package com.viadee.sonarQuest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Ui_Design")
public class UiDesign {


	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToOne
	@JoinColumn(name = "developer_id")
	private Developer developer;

	public UiDesign() {
	}
	
	public UiDesign(String name, Developer developer) {
		super();
		this.name = name;
		this.developer = developer;
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

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}
	    
	
	
}
