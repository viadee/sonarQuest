package com.viadee.sonarquest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.QualityRaid;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.services.QualityRaidService;

@RestController
@RequestMapping("/qualityraid")
public class QualityRaidController {

	@Autowired
	private QualityRaidService qualityRaidService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public QualityRaid createQualityRaid(@RequestBody final Raid raid) {
		return qualityRaidService.createQualityRaid(raid.getWorld().getId(), raid.getTitle(), raid.getGold(),
				raid.getXp());
	}

	@GetMapping(value = "/world/{id}")
	public QualityRaid getQualityRaidFromWorld(@PathVariable(value = "id") Long world_id) {
		return qualityRaidService.findByWorld(world_id);
	}
}
