package com.viadee.sonarquest.utils.mapper;

import org.springframework.stereotype.Component;

import com.viadee.sonarquest.dto.WorldDTO;
import com.viadee.sonarquest.entities.World;

@Component
public class WorldDtoEntityMapper {

	public WorldDTO entityToDto(World entity) {
		WorldDTO dto = new WorldDTO();
		
		dto.setActive(entity.getActive());
		dto.setId(entity.getId());
		dto.setImage(entity.getImage());
		dto.setName(entity.getName());
		dto.setProject(entity.getProject());
		dto.setUsequestcards(entity.getUsequestcards());
		
		return dto;
	}
}
