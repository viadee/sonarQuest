package com.viadee.sonarquest.skilltree.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.skilltree.entities.SkillTreeUser;
import com.viadee.sonarquest.skilltree.entities.UserSkill;
import com.viadee.sonarquest.skilltree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skilltree.repositories.SkillTreeUserRepository;
import com.viadee.sonarquest.skilltree.repositories.UserSkillRepository;
import com.viadee.sonarquest.skilltree.repositories.UserSkillToSkillTreeUserRepository;

@Service
public class SkillTreeUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SkillTreeUserService.class);

	@Autowired
	private SkillTreeUserRepository skillTreeUserRepository;

	@Autowired
	private UserSkillRepository userSkillRepository;

	@Autowired
	private UserSkillToSkillTreeUserRepository userSkillToSkillTreeUserRepository;

	public SkillTreeUser createSkillTreeUser(String mail) {
		SkillTreeUser user = null;
		if (skillTreeUserRepository.findByMail(mail) == null) {
			user = skillTreeUserRepository.save(new SkillTreeUser(mail));
			List<UserSkill> userSkills = userSkillRepository.findAllRootUserSkills(false);
			for (UserSkill userSkill : userSkills) {
				UserSkillToSkillTreeUser userSkillToSkillTreeUser = new UserSkillToSkillTreeUser(null, 0, userSkill,
						user, null);
				userSkillToSkillTreeUserRepository.save(userSkillToSkillTreeUser);
				user.addUserSkillToSkillTreeUser(userSkillToSkillTreeUser);
			}
			skillTreeUserRepository.save(user);
		}

		return user;
	}

	public SkillTreeUser updateSkillTreeUser(final String oldMail, final String newMail) {
		SkillTreeUser user = findByMail(oldMail);
		if (user != null) {
			if (findByMail(newMail) == null) {
				user.setMail(newMail);
				save(user);
			} else {
				LOGGER.info("SkillTreeUser with mail '{}' already exist ", newMail);
				return null;
			}
		} else {
			return createSkillTreeUser(newMail);
		}
		return user;
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
