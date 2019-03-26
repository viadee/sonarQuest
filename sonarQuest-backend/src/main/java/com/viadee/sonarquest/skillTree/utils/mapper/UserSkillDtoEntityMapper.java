package com.viadee.sonarquest.skillTree.utils.mapper;

import org.springframework.stereotype.Component;

import com.viadee.sonarquest.skillTree.dto.UserSkillDTO;
import com.viadee.sonarquest.skillTree.entities.UserSkill;

@Component
public class UserSkillDtoEntityMapper {

	public UserSkillDTO entityToDto(UserSkill userSkill) {
		UserSkillDTO userSkillDTO = new UserSkillDTO();
		userSkillDTO.setId(userSkill.getId());
		userSkillDTO.setDescription(userSkill.getDescription());
		userSkillDTO.setName(userSkill.getName());
		return userSkillDTO;

	}
	

}
