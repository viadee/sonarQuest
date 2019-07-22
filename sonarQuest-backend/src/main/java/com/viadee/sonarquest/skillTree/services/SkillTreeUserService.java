package com.viadee.sonarquest.skillTree.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skillTree.repositories.SkillTreeUserRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillToSkillTreeUserRepository;

@Service
public class SkillTreeUserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SkillTreeUserService.class);

	@Autowired
	private SkillTreeUserRepository skillTreeUserRepository;
	
	@Autowired
	private UserSkillRepository userSkillRepository;
	
	@Autowired
	private  UserSkillToSkillTreeUserRepository userSkillToSkillTreeUserRepository;
	
	public SkillTreeUser createSkillTreeUser(String mail) {
		SkillTreeUser user = null;
		if (skillTreeUserRepository.findByMail(mail) == null) {
			user = skillTreeUserRepository.save(new SkillTreeUser(mail));
			List<UserSkill> userSkills = userSkillRepository.findAllRootUserSkills(false);
			for (UserSkill userSkill : userSkills) {
				UserSkillToSkillTreeUser userSkillToSkillTreeUser = userSkillToSkillTreeUserRepository
						.save(new UserSkillToSkillTreeUser(null, 0, userSkill, user, null));
				user.addUserSkillToSkillTreeUser(userSkillToSkillTreeUser);
			}
			skillTreeUserRepository.save(user);
		}

		return user;
	}
	
	public boolean updateSkillTreeUser(final String oldMail, final String newMail) {
		SkillTreeUser user = findByMail(oldMail);
		if (user != null) {
			if (findByMail(newMail) == null) {
				user.setMail(newMail);
				save(user);
			} else {
				LOGGER.info("SkillTreeUser with mail '{}' already exist ", newMail);
				return false;
			}
		} else {
			createSkillTreeUser(newMail);
		}
		return true;
	}
	
	public SkillTreeUser findByMail(String mail) {
		return skillTreeUserRepository.findByMail(mail);
	}
	
	public List<SkillTreeUser> findAll() {
		return skillTreeUserRepository.findAll();
	}

	public SkillTreeUser save(SkillTreeUser user) {
		return skillTreeUserRepository.save(user);
	}
}
