package com.viadee.sonarquest.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viadee.sonarquest.dto.SonarQuestStatusDTO;
import com.viadee.sonarquest.dto.StandardTaskDTO;
import com.viadee.sonarquest.entities.StandardTask;

@Component
public class StandardTaskDTOEntityMapper {
	
	@Autowired
	private WorldDtoEntityMapper worldMapper;
	
	public StandardTaskDTO entityToDto(StandardTask standardTask) {
		StandardTaskDTO dto = new StandardTaskDTO();
		dto.setId(standardTask.getId());
		dto.setComponent(standardTask.getComponent());
		dto.setDebt(standardTask.getDebt());
		dto.setEnddate(standardTask.getEnddate());
		dto.setGold(standardTask.getGold());
		dto.setIssueKey(standardTask.getIssueKey());
		dto.setIssueRule(standardTask.getIssueRule());
		dto.setKey(standardTask.getKey());
		dto.setScoring(standardTask.getScoring());
		dto.setSeverity(standardTask.getSeverity());
		dto.setStartdate(standardTask.getStartdate());
		dto.setStatus(SonarQuestStatusDTO.getStatusFromText(standardTask.getStatus().getText()));
		dto.setTitle(standardTask.getTitle());
		dto.setType(standardTask.getType());
		dto.setWorld(worldMapper.entityToDto(standardTask.getWorld()));
		dto.setXp(standardTask.getXp());
		return dto;
	}

}
