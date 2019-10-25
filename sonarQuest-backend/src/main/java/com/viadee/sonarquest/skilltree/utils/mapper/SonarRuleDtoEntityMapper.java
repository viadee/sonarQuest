package com.viadee.sonarquest.skilltree.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viadee.sonarquest.skilltree.dto.SonarRuleDTO;
import com.viadee.sonarquest.skilltree.entities.SonarRule;

@Component
public class SonarRuleDtoEntityMapper {

	@Autowired
	private UserSkillDtoEntityMapper userSkillMapper;

	public SonarRuleDTO entityToDto(SonarRule entity) {
		SonarRuleDTO dto = new SonarRuleDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setKey(entity.getKey());
		dto.setAddedAt(entity.getAddedAt());
		dto.setUserSkilLDto(userSkillMapper.entityToDto(entity.getUserSkill()));
		return dto;
	}

	public SonarRule dtoToEntity(SonarRuleDTO dto){
		SonarRule sonarRule = new SonarRule();
		sonarRule.setId(dto.getId());
		sonarRule.setKey(dto.getKey());
		sonarRule.setName(dto.getName());
		sonarRule.setAddedAt(dto.getAddedAt());
		sonarRule.setUserSkill(userSkillMapper.dtoToEntity(dto.getUserSkilLDto()));
		return  sonarRule;
	}

}
