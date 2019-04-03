package com.viadee.sonarquest.skillTree.utils.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.viadee.sonarquest.skillTree.dto.UserSkillDTO;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.repositories.UserSkillToSkillTreeUserRepository;

@Component
public class UserSkillDtoEntityMapper {

	public UserSkillDTO entityToDto(UserSkill userSkill) {
		UserSkillDTO userSkillDTO = new UserSkillDTO();
		userSkillDTO.setId(userSkill.getId());
		userSkillDTO.setDescription(userSkill.getDescription());
		userSkillDTO.setName(userSkill.getName());
		userSkillDTO.setRuleKey(userSkill.getSonarRules().stream().map(rule -> rule.getKey()).collect(Collectors.toList()));
		
		return userSkillDTO;
	}

}
