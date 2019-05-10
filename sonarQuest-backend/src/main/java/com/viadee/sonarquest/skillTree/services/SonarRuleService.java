package com.viadee.sonarquest.skillTree.services;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.viadee.sonarquest.services.ExternalRessourceService;
import com.viadee.sonarquest.skillTree.dto.SonarRuleDTO;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.repositories.SonarRuleRepository;
import com.viadee.sonarquest.skillTree.utils.mapper.SonarRuleSDtoEntityMapper;

@Service
public class SonarRuleService {

	@Autowired
	private ExternalRessourceService externalResourceService;

	@Autowired
	private SonarRuleRepository sonarRuleRepository;
	
	@Autowired
	private SonarRuleSDtoEntityMapper sonarRuleMapper;
	
	@Value("${last.rule.update:2000-01-01}")
	private String lastRuleUpdateFromProperty;

	public List<SonarRule> update(final String language) {
		final List<SonarRule> sonarRules = externalResourceService.generateSonarRulesByLanguage(language);
		sonarRules.forEach(this::saveRuleIfNotExists);
		// TODO entferne return
		return sonarRules;
	}

	//TODO evtl. ueberfluessig
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
		SonarRule sonarRule = findAll().stream().max(Comparator.comparing(SonarRule::getAddedAt))
				.orElse(null);
		Date date = new Date();
		if (sonarRule == null || sonarRule.getAddedAt() == null) {
			return lastRuleUpdateFromProperty;
		} else {
			date.setTime(sonarRule.getAddedAt().getTime());
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		}

	}
	

	public List<SonarRule> findAll() {
		return sonarRuleRepository.findAll();
	}

	public List<SonarRuleDTO> getUnassignedRules() {
		return this.sonarRuleRepository.findByUserSkillIsNull().stream().map(sonarRuleMapper::entityToDto).collect(Collectors.toList());
	}

	public void deleteSonarRule(Long id) {
		this.sonarRuleRepository.delete(id);
		
	}
}
