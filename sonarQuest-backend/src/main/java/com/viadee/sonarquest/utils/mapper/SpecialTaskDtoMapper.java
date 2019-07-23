package com.viadee.sonarquest.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viadee.sonarquest.dto.SpecialTaskDTO;
import com.viadee.sonarquest.entities.SpecialTask;

@Component
public class SpecialTaskDtoMapper {
	@Autowired
	private WorldDtoEntityMapper worldMapper;

	public SpecialTaskDTO entityToDto(SpecialTask entity) {
		SpecialTaskDTO dto = new SpecialTaskDTO();
		// Task values
		dto.setEnddate(entity.getEnddate());
		dto.setGold(entity.getGold());
		dto.setId(entity.getId());
		dto.setKey(entity.getKey());
		dto.setStartdate(entity.getStartdate());
		dto.setStatus(entity.getStatus());
		dto.setTitle(entity.getTitle());
		dto.setWorldDTO(worldMapper.entityToDto(entity.getWorld()));
		dto.setXp(entity.getXp());

		// SpecialTask values
		dto.setMessage(entity.getMessage());
		return dto;
	}

}
