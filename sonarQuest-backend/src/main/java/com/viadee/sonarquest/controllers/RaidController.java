package com.viadee.sonarquest.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.dto.RaidDTO;
import com.viadee.sonarquest.dto.SolvedTaskHistoryDTO;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.services.RaidService;

@RestController
@RequestMapping("/raid")
public class RaidController {

	@Autowired
	private RaidService raidService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Raid createRaid(@RequestBody Raid raid) {
		return raidService.createRaid(raid);
	}
	
	@PutMapping(value = "/updateRaid")
	public RaidDTO updateRaid(@RequestBody final Raid raid) {
		return new RaidDTO(raidService.updateRaid(raid));
	}

	@GetMapping(value = "/{id}")
	public RaidDTO findRaidById(@PathVariable(value = "id") final Long id) {
		Raid raidDAO = raidService.findRaidById(id);
		return new RaidDTO(raidDAO, raidService.calculateRaidProgress(raidDAO));
	}
	
	@GetMapping(value = "/visibleRaids/world/{id}")
	public List<RaidDTO> findAllVisibleRaidsFromWorld(@PathVariable(value = "id") Long world_id) {
		List<Raid> raids = raidService.findVisibleRaidsFromWorld(world_id);
		List<RaidDTO> raidDTOs = new ArrayList<RaidDTO>();

		for (Raid raid : raids) {
			RaidDTO raidDTO = new RaidDTO(raid, raidService.calculateRaidProgress(raid));
			raidDTOs.add(raidDTO);
		}
		return raidDTOs;
	}

	@GetMapping(value = "/world/{id}")
	public List<RaidDTO> findAllRaidsFromWorld(@PathVariable(value = "id") Long world_id) {
		List<Raid> raids = raidService.findAllRaidsFromWorld(world_id);
		List<RaidDTO> raidDTOs = new ArrayList<RaidDTO>();

		for (Raid raid : raids) {
			RaidDTO raidDTO = new RaidDTO(raid, raidService.calculateRaidProgress(raid));
			raidDTOs.add(raidDTO);
		}
		return raidDTOs;
	}

	@DeleteMapping(value = "/{id}")
	public void deleteRaid(@PathVariable(value = "id") final Long id) {
		raidService.deleteRaid(id);
	}

	@PutMapping(value = "/solveRaidManually")
	public RaidDTO solveRaidManually(@RequestBody final Raid raid) {
		return new RaidDTO(raidService.solveRaidManually(raid.getId()));
	}

	public Quest addRaidToQuest(@PathVariable(value = "raidId") final Long raidId,
			@PathVariable(value = "questId") final Long questId) {
		return raidService.addRaidToQuest(raidId, questId);
	}
	
	@GetMapping(value = "/getSolvedTaskHistoryList/{id}")
	public List<SolvedTaskHistoryDTO> getSolvedTaskHistoryList(@PathVariable(value = "id") final Long raidId) {
		return raidService.getSolvedTaskHistory(raidId);
	}

}
