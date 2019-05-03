package com.viadee.sonarquest.controllers;

import java.security.Principal;
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

import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.ArtefactRepository;
import com.viadee.sonarquest.services.ArtefactService;
import com.viadee.sonarquest.services.UserService;

@RestController
@RequestMapping("/artefact")
public class ArtefactController {

	@Autowired
	private ArtefactRepository artefactRepository;

	@Autowired
	private ArtefactService artefactService;

	@Autowired
	private UserService userService;

	@GetMapping
	public List<Artefact> getAllArtefacts() {
		return artefactService.getArtefacts();
	}

	@GetMapping(value = "/forMarketplace/")
	public List<Artefact> getArtefactsforMarketplace() {
		return artefactService.getArtefactsForMarketplace();
	}

	@GetMapping(value = "/{id}")
	public Artefact getArtefactById(@PathVariable(value = "id") final Long id) {
		return artefactService.getArtefact(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Artefact createArtefact(@RequestBody final Artefact artefact) {
		return artefactService.createArtefact(artefact);
	}

	@PutMapping(value = "/{id}")
	public Artefact updateArtefact(@PathVariable(value = "id") final Long id, @RequestBody final Artefact data) {
		Artefact artefact = artefactRepository.findOne(data.getId());
		if (artefact != null) {
			artefact.setDescription(data.getDescription());
			artefact.setIcon(data.getIcon());
			artefact.setMinLevel(data.getMinLevel());
			artefact.setName(data.getName());
			artefact.setPrice(data.getPrice());
			artefact.setQuantity(data.getQuantity());
			artefact.setSkills(data.getSkills());
			artefact.setUsers(data.getUsers());
			artefactService.updateArtefact(id, artefact);
		}
		return artefact;
	}

	@PutMapping(value = "/{artefact_id}/buy")
	public boolean buyArtefact(final Principal principal, @PathVariable(value = "artefact_id") final Long artefact_id) {
		User user = userService.findByUsername(principal.getName());
		user = userService.findById(user.getId());
		final Artefact artefact = artefactRepository.findOne(artefact_id);

		return artefactService.buyArtefact(artefact, user) != null;
	}

	@DeleteMapping(value = "/{id}")
	public boolean deleteArtefact(@PathVariable(value = "id") final Long id) {
		Artefact artefact = artefactRepository.findOne(id);
		if (artefact != null) {
			if (artefact.getUsers().size() != 0) {
				return false;
			} else {
				artefactRepository.delete(id);
				return true;
			}
		}
		return false;
	}

	@DeleteMapping(value = "/{id}/payout")
	public void payoutArtefact(@PathVariable(value = "id") final Long id) {
		Artefact artefact = artefactRepository.findOne(id);
		artefactService.payoutArtefact(artefact);
	}
	
	@PutMapping(value = "/{id}/removeFromMarketplace")
	public void removeArtefactFromMarketplace(@PathVariable(value = "id") final Long id) {
		Artefact artefact = artefactRepository.findOne(id);
		artefactService.removeArtefactFromMarketplace(artefact);
	}

}
