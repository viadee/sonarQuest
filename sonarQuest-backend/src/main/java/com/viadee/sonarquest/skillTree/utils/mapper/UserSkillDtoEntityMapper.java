package com.viadee.sonarquest.skillTree.utils.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.viadee.sonarquest.skillTree.dto.UserSkillDTO;
import com.viadee.sonarquest.skillTree.entities.UserSkill;

@Component
public class UserSkillDtoEntityMapper {

	public UserSkillDTO entityToDto(UserSkill userSkill) {
		UserSkillDTO userSkillDto = new UserSkillDTO();
		if (userSkill != null) {
			userSkillDto.setId(userSkill.getId());
			userSkillDto.setDescription(userSkill.getDescription());
			userSkillDto.setName(userSkill.getName());
			userSkillDto.setRuleKey(
					userSkill.getSonarRules().stream().map(rule -> rule.getKey()).collect(Collectors.toList()));
			userSkillDto.setRoot(userSkill.isRoot());
		} else {
			userSkillDto = null;
		}

		return userSkillDto;
	}

}
