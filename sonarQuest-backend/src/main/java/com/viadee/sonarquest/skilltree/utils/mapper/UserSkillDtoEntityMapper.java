package com.viadee.sonarquest.skilltree.utils.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viadee.sonarquest.skilltree.dto.UserSkillDTO;
import com.viadee.sonarquest.skilltree.entities.UserSkill;

@Component
public class UserSkillDtoEntityMapper {

    @Autowired
    private UserSkillGroupDtoEntitiyMapper userSkillGroupDtoEntitiyMapper;

    @Autowired
    private SonarRuleDtoEntityMapper sonarRuleDtoEntityMapper;

    public UserSkillDTO entityToDto(UserSkill userSkill) {
        UserSkillDTO userSkillDto = new UserSkillDTO();
        if (userSkill != null) {
            userSkillDto.setId(userSkill.getId());
            userSkillDto.setDescription(userSkill.getDescription());
            userSkillDto.setName(userSkill.getName());
            userSkillDto.setRequiredRepetitions(userSkill.getRequiredRepetitions());
            userSkillDto.setUserSkillGroup(userSkillGroupDtoEntitiyMapper.entityToDto(userSkill.getUserSkillGroup()));
            userSkillDto.setRoot(userSkill.isRoot());
            if (userSkill.getFollowingUserSkills() != null) {
                userSkillDto.setFollowingUserSkills(userSkill.getFollowingUserSkills().stream().map(this::entityToDto).collect(Collectors.toList()));
            }
            if (userSkill.getPreviousUserSkills() != null) {

                userSkillDto.setPreviousUserSkills(userSkill.getPreviousUserSkills().stream().map(this::entityToDto).collect(Collectors.toList()));
            }
            if (userSkill.getSonarRules() != null) {
                userSkillDto.setSonarRules(userSkill.getSonarRules().stream().map(sonarRuleDtoEntityMapper::entityToDto).collect(Collectors.toList()));
            }
        }

        return userSkillDto;
    }

    public UserSkill dtoToEntity(UserSkillDTO dto) {
        UserSkill userSkill = new UserSkill();
        if (dto != null) {
            userSkill.setId(dto.getId());
            userSkill.setUserSkillGroup(userSkillGroupDtoEntitiyMapper.dtoToEntity(dto.getUserSkillGroup()));
            userSkill.setRequiredRepetitions(dto.getRequiredRepetitions());
            userSkill.setName(dto.getName());
            userSkill.setDescription(dto.getDescription());
            userSkill.setRoot(dto.isRoot());
            if (dto.getFollowingUserSkills() != null) {
                userSkill.setFollowingUserSkills(dto.getFollowingUserSkills().stream().map(this::dtoToEntity).collect(Collectors.toList()));
            }
            if (dto.getPreviousUserSkills() != null) {
                userSkill.setPreviousUserSkills(dto.getPreviousUserSkills().stream().map(this::dtoToEntity).collect(Collectors.toList()));

            }
            if (dto.getSonarRules() != null) {
                userSkill.setSonarRules(dto.getSonarRules().stream().map(sonarRuleDtoEntityMapper::dtoToEntity).collect(Collectors.toList()));

            }
        }
        return userSkill;
    }

}
