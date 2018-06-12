package com.viadee.sonarQuest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.dtos.UiDesignDto;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.UiDesign;
import com.viadee.sonarQuest.repositories.UiDesignRepository;

@Service
public class UiDesignService {
	
	@Autowired
	private UiDesignRepository uiDesignRepository;

	public UiDesignDto toUiDesignDto(UiDesign ui) {
		UiDesignDto uiDto = null;
		
		if(ui != null) {
			uiDto = new UiDesignDto(ui.getId(),ui.getName(), ui.getDeveloper());
		}
		
		return uiDto;
	}
	
	public UiDesignDto createUiDesign(Developer d, String designName) {
		UiDesign ui = new UiDesign(designName, d);
		this.uiDesignRepository.save(ui);
		return this.toUiDesignDto(ui);
	}
	

	public UiDesignDto updateUiDesign(UiDesign ui, Developer d, String designName) {
		UiDesignDto uiDto = null;
		
		if (ui != null) {
        	ui.setName(designName);
        	ui = uiDesignRepository.save(ui);
        	uiDto = this.toUiDesignDto(ui);
        } else {
        	uiDto = this.createUiDesign(d, designName);
        }
		return uiDto;
	}
	
}
