package com.viadee.sonarquest.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.dto.HighScoreDTO;
import com.viadee.sonarquest.entities.QualityGateRaid;
import com.viadee.sonarquest.entities.QualityGateRaidRewardHistory;
import com.viadee.sonarquest.interfaces.HighScore;
import com.viadee.sonarquest.services.QualityGateHighScoreService;
import com.viadee.sonarquest.services.QualityGateRaidRewardHistoryService;
import com.viadee.sonarquest.services.QualityGateRaidService;

@RestController
@RequestMapping("/qualitygateraid")
public class QualityGateRaidController {

	@Autowired
	private QualityGateRaidService qualityGateRaidService;
	
	@Autowired
	private QualityGateRaidRewardHistoryService historyService;
	
	@Autowired
	private QualityGateHighScoreService qualityGateHighScoreService;

	@PutMapping(value = "/updateQualityGateRaid")
	public QualityGateRaid updateQualityGateRaid(@RequestBody final QualityGateRaid raid) {
		return qualityGateRaidService.updateQualityGateRaid(raid);
	}
	
	@GetMapping(value = "/world/{id}")
	public QualityGateRaid getQualityRaidFromWorld(@PathVariable(value = "id") Long world_id) {
		return qualityGateRaidService.findByWorld(world_id);
	}
	
	@PostMapping(value = "/calculateActualScore")
	public HighScore calculateActualScore(@RequestBody QualityGateRaid raid) {
		return new HighScoreDTO(Date.valueOf(LocalDate.now()), qualityGateHighScoreService.calculateActualScore(raid));
	}
	
	@GetMapping(value = "/getHistory/{id}")
	public List<QualityGateRaidRewardHistory> getHistory(@PathVariable(value = "id") Long raid_id) {
		// get last seven days history data from quality gate
		return historyService.readQualityGateRaidRewardHistories(LocalDate.now().minusDays(7), LocalDate.now(), raid_id);
	}
}
