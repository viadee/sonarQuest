package com.viadee.sonarquest.skillTree.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viadee.sonarquest.skillTree.dto.SonarRuleDTO;
import com.viadee.sonarquest.skillTree.entities.SonarRule;

@Component
public class SonarRuleSDtoEntityMapper {

	@Autowired
	private UserSkillDtoEntityMapper userSkillMapper;

	public SonarRuleDTO entityToDto(SonarRule entity) {
		SonarRuleDTO dto = new SonarRuleDTO();
		dto.setName(entity.getName());
		dto.setKey(entity.getKey());
		dto.setAddedAt(entity.getAddedAt());
		dto.setUserSkilLDto(userSkillMapper.entityToDto(entity.getUserSkill()));
		return dto;
	}

}
