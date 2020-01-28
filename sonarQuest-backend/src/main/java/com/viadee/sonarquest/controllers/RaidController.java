package com.viadee.sonarquest.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.dto.RaidDTO;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.services.RaidService;
import com.viadee.sonarquest.util.ProgressDTO;

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

	@GetMapping(value = "/{id}")
	public RaidDTO findRaidById(@PathVariable(value = "id") final Long id) {
		Raid raidDAO = raidService.findRaidById(id);
		// RaidDTO from raid and set progress details
		RaidDTO raidDTO = new RaidDTO(raidDAO);
		raidDTO.setRaidProgress(raidService.calculateRaidProgress(raidDAO));
		return raidDTO;
	}

	@GetMapping(value = "/world/{id}")
	public List<RaidDTO> getAllRaidsFromWorld(@PathVariable(value = "id") Long world_id) {
		List<Raid> raids = raidService.findAllRaidsFromWorld(world_id);
		List<RaidDTO> raidDTOs = new ArrayList<RaidDTO>();
		
		for (Raid raid : raids) {
			RaidDTO raidDTO = new RaidDTO(raid);
			ProgressDTO progressCalc = raidService.calculateRaidProgress(raid);
			raidDTO.setRaidProgress(progressCalc);
			raidDTOs.add(raidDTO);
		}
		return raidDTOs; 
	}

	public void deleteRaid(Long raidId) {
	}

	public void solveRaid() {
	}

	public Quest addRaidToQuest(@PathVariable(value = "raidId") final Long raidId,
			@PathVariable(value = "questId") final Long questId) {
		return raidService.addRaidToQuest(raidId, questId);
	}

	@PostMapping(value = "/{raidId}/join")
	@ResponseStatus(HttpStatus.CREATED)
	public Raid joinRaid(final Principal principal, @PathVariable(value = "raidId") final Long raidId) {
//		final String username = principal.getName();
//		final User user = userService.findByUsername(username);
//		return adventureService.addUserToAdventure(adventureId, user.getId());
		return null;
	}

	/**
	 * 
	 * @param adventureId The id of the adventure
	 * @param developerId The id of the developer to remove
	 * @return Gives the adventure where the Developer was removed
	 */
	@PostMapping(value = "/{raidId}/leave")
	public Raid leaveRaid(final Principal principal, @PathVariable(value = "raidId") final Long raidId) {
//		final String username = principal.getName();
//		final User user = userService.findByUsername(username);
//		return adventureService.removeUserFromAdventure(adventureId, user.getId());
		return null;
	}

}
