package com.viadee.sonarquest.utils.mapper;

import org.springframework.stereotype.Component;

import com.viadee.sonarquest.dto.WorldDTO;
import com.viadee.sonarquest.entities.World;

@Component
public class WorldDtoEntityMapper {
	
	public WorldDTO entityToDto(World world) {
		WorldDTO dto = new WorldDTO();
		dto.setId(world.getId());
		dto.setActive(world.getActive());
		dto.setImage(world.getImage());
		dto.setName(world.getName());
		dto.setProject(world.getProject());
		dto.setUsequestcards(world.getUsequestcards());
		return dto;
	}

}
