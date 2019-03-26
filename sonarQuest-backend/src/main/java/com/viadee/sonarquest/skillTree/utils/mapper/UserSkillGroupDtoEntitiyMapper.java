package com.viadee.sonarquest.skillTree.utils.mapper;

import org.springframework.stereotype.Component;

import com.viadee.sonarquest.skillTree.dto.UserSkillGroupDTO;
import com.viadee.sonarquest.skillTree.entities.UserSkillGroup;

@Component
public class UserSkillGroupDtoEntitiyMapper {
	
	public UserSkillGroupDTO entityToDto (UserSkillGroup userSkillGroup) {
		UserSkillGroupDTO userSkillGroupDTO = new UserSkillGroupDTO();
		userSkillGroupDTO.setId(userSkillGroup.getId());
		userSkillGroupDTO.setName(userSkillGroup.getName());
		userSkillGroupDTO.setRoot(userSkillGroup.isRoot());
		
		return userSkillGroupDTO;
	}
	
	
	//TODO 
	public UserSkillGroup dtoToEntity (UserSkillGroupDTO userSkillGroupDTO) {
		return null;
	}

}
