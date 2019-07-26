package com.viadee.sonarquest.skilltree.utils.mapper;

import java.util.stream.Collectors;

import com.viadee.sonarquest.skilltree.entities.SonarRule;
import org.springframework.stereotype.Component;

import com.viadee.sonarquest.skilltree.dto.UserSkillDTO;
import com.viadee.sonarquest.skilltree.entities.UserSkill;

@Component
public class UserSkillDtoEntityMapper {

	public UserSkillDTO entityToDto(UserSkill userSkill) {
		UserSkillDTO userSkillDto = new UserSkillDTO();
		if (userSkill != null) {
			userSkillDto.setId(userSkill.getId());
			userSkillDto.setDescription(userSkill.getDescription());
			userSkillDto.setName(userSkill.getName());
			userSkillDto.setRuleKey(
					userSkill.getSonarRules().stream().map(SonarRule::getKey).collect(Collectors.toList()));
			userSkillDto.setRoot(userSkill.isRoot());
		} else {
			userSkillDto = null;
		}

		return userSkillDto;
	}

}
