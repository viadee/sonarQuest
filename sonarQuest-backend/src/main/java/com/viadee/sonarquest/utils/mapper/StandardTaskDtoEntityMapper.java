package com.viadee.sonarquest.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viadee.sonarquest.dto.StandardTaskDTO;
import com.viadee.sonarquest.entities.StandardTask;

@Component
public class StandardTaskDtoEntityMapper {

	@Autowired
	private WorldDtoEntityMapper worldMapper;

	public StandardTaskDTO enitityToDto(StandardTask entity) {
		StandardTaskDTO dto = new StandardTaskDTO();

		// Task values
		dto.setEnddate(entity.getEnddate());
		dto.setGold(entity.getGold());
		dto.setId(entity.getId());
		dto.setKey(entity.getKey());
		dto.setStartdate(entity.getStartdate());
		dto.setStatus(entity.getStatus());
		dto.setTitle(entity.getTitle());
		if (entity.getWorld() != null) {
			dto.setWorldDTO(worldMapper.entityToDto(entity.getWorld()));

		}
		dto.setXp(entity.getXp());

		// StandardTask values
		dto.setComponent(entity.getComponent());
		dto.setDebt(entity.getDebt());
		dto.setIssueKey(entity.getIssueKey());
		dto.setIssueRule(entity.getIssueRule());
		dto.setSeverity(entity.getSeverity());
		dto.setType(entity.getType());

		return dto;
	}

}
