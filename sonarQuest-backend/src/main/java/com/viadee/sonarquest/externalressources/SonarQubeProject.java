package com.viadee.sonarquest.externalressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarQubeProject {

    @JsonProperty(value = "key")
    private String key;

    @JsonProperty(value = "name")
    private String name;

    public SonarQubeProject() {
    }

    public SonarQubeProject(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SonarQubeProject{" +
                    "key='" + key + '\'' +
                    ", name=" + name +
                '}';
    }
}

