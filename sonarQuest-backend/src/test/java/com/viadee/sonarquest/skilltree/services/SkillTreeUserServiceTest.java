package com.viadee.sonarquest.skilltree.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.viadee.sonarquest.skilltree.entities.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.skilltree.repositories.SkillTreeUserRepository;
import com.viadee.sonarquest.skilltree.repositories.UserSkillRepository;
import com.viadee.sonarquest.skilltree.repositories.UserSkillToSkillTreeUserRepository;

@RunWith(MockitoJUnitRunner.class)
public class SkillTreeUserServiceTest {

	@Mock
	private SkillTreeUserRepository skillTreeUserRepository;

	@Mock
	private UserSkillRepository userSkillRepository;

	@Mock
	private UserSkillToSkillTreeUserRepository userSkillToSkillTreeUserRepository;

	@InjectMocks
	private SkillTreeUserService skillTreeUserService;

	private SkillTreeUser user;
	private String mail;

	@Before
	public void init() {
		// Mock SkillTreeUser
		mail = "test@test.de";
		user = new SkillTreeUser();
		user.setId(Long.valueOf(1L));
		user.setMail(mail);
		generateUserSkillToSkillTreeUserMocks(user, generateUserSkillMocks());
	}

	@Test
	public void testCreateSkillTreeUser() {
		/*
		 * Given
		 */

		// Simulate that no user is found
		when(skillTreeUserRepository.findByMail(mail)).thenReturn(null);

		// Mock User
		when(skillTreeUserRepository.save(Matchers.any(SkillTreeUser.class))).thenReturn(new SkillTreeUser(mail))
				.thenReturn(user);

		// Mock list of UserSkills that are not root
		List<UserSkill> userSkills = generateUserSkillMocks();
		when(userSkillRepository.findAllRootUserSkills(false)).thenReturn(userSkills);

		// "Disable" repository save
		when(userSkillToSkillTreeUserRepository.save(Matchers.any(UserSkillToSkillTreeUser.class)))
				.thenReturn(new UserSkillToSkillTreeUser());

		/*
		 * When
		 */

		SkillTreeUser userFromMethod = skillTreeUserService.createSkillTreeUser(mail);

		/*
		 * Then
		 */
		assertNotNull(userFromMethod);
		assertTrue(userFromMethod.getUserSkillToSkillTreeUser().size() == user.getUserSkillToSkillTreeUser().size());
		assertEquals(user.getMail(), user.getMail());

	}

	@Test
	public void updateSkillTreeUser_newUser() {
		/*
		 * Given
		 */
		String newMail = "testNew@test.de";

		// Simulate that no user is found
		when(skillTreeUserRepository.findByMail(mail)).thenReturn(null);

		// Mock User
		when(skillTreeUserRepository.save(Matchers.any(SkillTreeUser.class))).thenReturn(new SkillTreeUser(mail))
				.thenReturn(user);

		// Mock list of UserSkills that are not root
		List<UserSkill> userSkills = generateUserSkillMocks();
		when(userSkillRepository.findAllRootUserSkills(false)).thenReturn(userSkills);

		// "Disable" repository save
		when(userSkillToSkillTreeUserRepository.save(Matchers.any(UserSkillToSkillTreeUser.class)))
				.thenReturn(new UserSkillToSkillTreeUser());
		/*
		 * When
		 */
		SkillTreeUser updatedUser = skillTreeUserService.updateSkillTreeUser(mail, newMail);

		/*
		 * Then
		 */
		assertNotNull(updatedUser);

	}

	@Test
	public void updateSkillTreeUser_oldUserMailExist() {
		/*
		 * Given
		 */
		String newMail = "testNew@test.de";

		when(skillTreeUserRepository.findByMail(mail)).thenReturn(user);

		when(skillTreeUserRepository.findByMail(newMail)).thenReturn(new SkillTreeUser());

		/*
		 * When
		 */
		SkillTreeUser updatedUser = skillTreeUserService.updateSkillTreeUser(mail, newMail);
		/*
		 * Then
		 */
		assertNull(updatedUser);

	}

	@Test
	public void updateSkillTreeUser_oldUserMailDoesNotExist() {
		/*
		 * Given
		 */
		String newMail = "testNew@test.de";

		when(skillTreeUserRepository.findByMail(mail)).thenReturn(user);

		when(skillTreeUserRepository.findByMail(newMail)).thenReturn(null);

		/*
		 * When
		 */
		SkillTreeUser updatedUser = skillTreeUserService.updateSkillTreeUser(mail, newMail);
		/*
		 * Then
		 */
		assertNotNull(updatedUser);
		assertEquals(updatedUser.getMail(), user.getMail());
	}

	/*
	 * HELPER
	 */

	private List<UserSkill> generateUserSkillMocks() {
		List<UserSkill> skillMocks = new ArrayList<UserSkill>();
		for (int i = 0; i < 10; i++) {
			UserSkill userSkill = new UserSkill("Test Description", "TestSkill" + (i + 1), false,
					new UserSkillGroup(Long.valueOf(1L), "Group1", false), 3);
			userSkill.setId(Long.valueOf(i));
			skillMocks.add(userSkill);

			if (i > 0) {
				skillMocks.get(i - 1).addFollowingUserSkill(skillMocks.get(i));
				skillMocks.get(i).addPreviousUserSkill(skillMocks.get(i - 1));
			}
		}
		return skillMocks;
	}

	private List<UserSkillToSkillTreeUser> generateUserSkillToSkillTreeUserMocks(SkillTreeUser user,
			List<UserSkill> userSkills) {
		List<UserSkillToSkillTreeUser> userSkillToSkillTreeUsers = new ArrayList<UserSkillToSkillTreeUser>();
		for (int i = 0; i < userSkills.size(); i++) {
			UserSkillToSkillTreeUser userSkillToSkillTreeUser = new UserSkillToSkillTreeUser();
			userSkillToSkillTreeUser.setId(Long.valueOf(Long.valueOf(i)));
			try {
				userSkillToSkillTreeUser.setLearnedOn(getTimestamp());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			userSkillToSkillTreeUser.setRepeats(3);
			userSkillToSkillTreeUser.setScore(1.0);
			userSkillToSkillTreeUser.setSkillTreeUser(user);
			userSkillToSkillTreeUser.setUserSkill(userSkills.get(i));
			userSkills.get(i).addUserSkillToSkillTreeUsers(userSkillToSkillTreeUser);
			user.addUserSkillToSkillTreeUser(userSkillToSkillTreeUser);
			userSkillToSkillTreeUsers.add(userSkillToSkillTreeUser);
		}
		return userSkillToSkillTreeUsers;

	}

	private Timestamp getTimestamp() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse("01/01/2000");
		long time = date.getTime();
		return new Timestamp(time);
	}
}
