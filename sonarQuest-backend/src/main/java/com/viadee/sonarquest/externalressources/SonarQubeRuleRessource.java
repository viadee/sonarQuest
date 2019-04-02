package com.viadee.sonarquest.externalressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarQubeRuleRessource {

	@JsonProperty("rules")
	private List<SonarQubeRule> rules;

	public List<SonarQubeRule> getRules() {
		return rules;
	}

	public void setRules(List<SonarQubeRule> rules) {
		this.rules = rules;
	}
	
	public void addRuleIfNotExists(SonarQubeRule sonarQubeRule) {
		if(rules.stream().filter(rule -> sonarQubeRule.getKey().equals(rule.getKey())).findAny().orElse(null) == null) {
			this.rules.add(sonarQubeRule);	
		}
	}

	@Override
	public String toString() {
		return "SonarQubeIssueRessource{issues=" + rules + '}';
	}
}
