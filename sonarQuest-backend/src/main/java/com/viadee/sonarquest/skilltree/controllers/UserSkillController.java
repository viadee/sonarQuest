package com.viadee.sonarquest.skilltree.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.skilltree.dto.UserSkillDTO;
import com.viadee.sonarquest.skilltree.entities.UserSkill;
import com.viadee.sonarquest.skilltree.repositories.UserSkillRepository;
import com.viadee.sonarquest.skilltree.services.UserSkillService;
import com.viadee.sonarquest.skilltree.utils.mapper.UserSkillDtoEntityMapper;

@RestController
@RequestMapping("/userskill")
public class UserSkillController {

	@Autowired
	private UserSkillRepository userSkillRepository;

	@Autowired
	private UserSkillService userSkillService;

	@Autowired
	private UserSkillDtoEntityMapper userSkillMapper;
	

	@GetMapping
	public List<UserSkill> getAllUserSkills() {
		return userSkillRepository.findAll();
	}

	@GetMapping(value = "/bygroup")
	public List<UserSkill> getAllUserSkillsFromGroup(@RequestParam(value = "id") final Long id) {
		return userSkillRepository.findUserSkillsByGroup(id);
	}

	@GetMapping(value = "/team")
	public List<UserSkillDTO> getUserSkillsFromTeam(@RequestParam(value = "mails") final String mailString) {
		List<String> mails;
		if (mailString.indexOf(',') != -1) {
			mails = new ArrayList<>(Arrays.asList(mailString.split(",")));
		} else {
			mails = new ArrayList<>();
			mails.add(mailString);
		}
		return userSkillService.findUserSkillsFromTeam(mails);
	}

	@GetMapping(value = "/roots/")
	public List<UserSkillDTO> getAllRootUserSkills() {
		return userSkillRepository.findAllRootUserSkills(true).stream().map(userSkillMapper::entityToDto)
				.collect(Collectors.toList());
	}

	@PutMapping(value = "/update")
	@ResponseStatus(HttpStatus.OK)
	public UserSkill updateUserSkill(@RequestBody UserSkill userSkill) {
		return userSkillService.updateUserSkill(userSkill);
	}

	@PostMapping(value = "/learn")
	public void learnSkill(@RequestParam(value = "mail") String mail,
			@RequestParam(value = "key") final String key) {
		mail = mail.replaceAll("[\n|\r|\t]", "");
		userSkillService.learnUserSkill(mail, key);
	}

	@PostMapping(value = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	public UserSkill createUserSkill(Principal principal ,@RequestParam(value = "groupid") final Long groupid,
			@RequestBody UserSkill userSkill) {
		return userSkillService.createUserSkill(userSkill, groupid, principal);
	}

}
