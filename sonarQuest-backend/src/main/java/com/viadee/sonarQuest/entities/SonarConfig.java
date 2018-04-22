package com.viadee.sonarQuest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Sonar_Config")
public class SonarConfig {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "sonar_server_url")
    private String sonarServerUrl;

    @NotNull
    @Column(name = "sonar_project")
    private String sonarProject;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSonarServerUrl() {
        return sonarServerUrl;
    }

    public void setSonarServerUrl(final String sonarServerUrl) {
        this.sonarServerUrl = sonarServerUrl;
    }

    public String getSonarProject() {
        return sonarProject;
    }

    public void setSonarProject(final String sonarProject) {
        this.sonarProject = sonarProject;
    }

}
