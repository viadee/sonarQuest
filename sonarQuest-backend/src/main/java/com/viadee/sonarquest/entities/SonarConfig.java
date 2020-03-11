package com.viadee.sonarquest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

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

    @Column(name = "http_basic_auth_username")
    private String httpBasicAuthUsername;

    @Column(name = "http_basic_auth_password")
    private String httpBasicAuthPassword;
    
    @Column(name="organization")
    private String organization;
    
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

    public String getHttpBasicAuthUsername() {
        return httpBasicAuthUsername;
    }

    public void setHttpBasicAuthUsername(final String httpBasicAuthUsername) {
        this.httpBasicAuthUsername = httpBasicAuthUsername;
    }

    public String getHttpBasicAuthPassword() {
        return httpBasicAuthPassword;
    }

    public void setHttpBasicAuthPassword(final String httpBasicAuthPassword) {
        this.httpBasicAuthPassword = httpBasicAuthPassword;
    }

    public boolean hasHttpBasicAuth() {
        return StringUtils.isNotBlank(httpBasicAuthUsername) && StringUtils.isNotBlank(httpBasicAuthPassword);
    }

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
}
