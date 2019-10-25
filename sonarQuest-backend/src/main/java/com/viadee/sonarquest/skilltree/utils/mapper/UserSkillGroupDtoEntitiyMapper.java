package com.viadee.sonarquest.skilltree.utils.mapper;

import com.viadee.sonarquest.skilltree.dto.UserSkillGroupDTO;
import com.viadee.sonarquest.skilltree.entities.UserSkillGroup;
import org.springframework.stereotype.Component;

@Component
public class UserSkillGroupDtoEntitiyMapper {

    public UserSkillGroupDTO entityToDto(UserSkillGroup userSkillGroup) {
        UserSkillGroupDTO dto = new UserSkillGroupDTO();
        if (userSkillGroup != null) {


            dto.setId(userSkillGroup.getId());
            dto.setName(userSkillGroup.getName());
        }
        return dto;
    }

    public UserSkillGroup dtoToEntity(UserSkillGroupDTO dto) {
        UserSkillGroup userSkillGroup = new UserSkillGroup();
        if (dto != null) {
            userSkillGroup.setId(dto.getId());
            userSkillGroup.setName(dto.getName());
        }
        return userSkillGroup;
    }
}
