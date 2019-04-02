package com.viadee.sonarquest.skillTree.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.viadee.sonarquest.services.ExternalRessourceService;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.repositories.SkillTreeUserRepository;
import com.viadee.sonarquest.skillTree.repositories.SonarRuleRepository;

@Service
public class SonarRuleService {

	@Autowired
	private ExternalRessourceService externalResourceService;

	@Autowired
	private SonarRuleRepository sonarRuleRepository;
	
	@Value("${last.rule.update:2000-01-01}")
	private String lastRuleUpdateFromProperty;

	public List<SonarRule> update(final String language) {
		final List<SonarRule> sonarRules = externalResourceService.generateSonarRulesByLanguage(language);
		sonarRules.forEach(this::saveRuleIfNotExists);
		// TODO entferne return
		return sonarRules;
	}

	public void createSonarRule(String key, String name) {
		SonarRule sonarRule = new SonarRule();
		sonarRule.setKey(key);
		sonarRule.setName(name);
		sonarRuleRepository.save(sonarRule);
	}

	private void saveRuleIfNotExists(final SonarRule sonarRule) {
		final SonarRule foundSonarRule = sonarRuleRepository.findSonarRuleByKey(sonarRule.getKey());
		if (foundSonarRule == null) {
			sonarRuleRepository.save(sonarRule);
		}
	}

	public String getLastAddedDate() {
		SonarRule sonarRule = findAll().stream().max(Comparator.comparing(SonarRule::getAddedAT))
				.orElseThrow(NoSuchElementException::new);
		Date date = new Date();
		if (sonarRule.getAddedAT() == null) {
			return lastRuleUpdateFromProperty;
		} else {
			date.setTime(sonarRule.getAddedAT().getTime());
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		}

	}
	

	public List<SonarRule> findAll() {

		return sonarRuleRepository.findAll();
	}
}
