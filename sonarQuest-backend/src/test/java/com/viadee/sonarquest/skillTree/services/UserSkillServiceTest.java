package com.viadee.sonarquest.skillTree.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.skillTree.dto.UserSkillDTO;
import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillGroup;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skillTree.utils.mapper.UserSkillDtoEntityMapper;

@RunWith(MockitoJUnitRunner.class)
public class UserSkillServiceTest {

	@Mock
	private SkillTreeUserService skillTreeUserService;

	@Mock
	private UserSkillDtoEntityMapper mapper;

	@InjectMocks
	private UserSkillService userSkilLService;
	
	@InjectMocks
	private UserSkillDtoEntityMapper usedMapper;

	@Test
	public void findUserSkillsFromTeam_MailNotNull() {
		/*
		 * Given
		 */
		String mail1 = "test1@test.de";
		SkillTreeUser user1 = new SkillTreeUser();
		user1.setId(Long.valueOf(1L));
		user1.setMail(mail1);
		generateUserSkillToSkillTreeUserMocks(user1, generateUserSkillMocks());

		String mail2 = "test2@test.de";
		SkillTreeUser user2 = new SkillTreeUser();
		user2.setId(Long.valueOf(2L));
		user2.setMail(mail1);
		generateUserSkillToSkillTreeUserMocks(user2, generateUserSkillMocks());

		when(skillTreeUserService.findByMail(mail2)).thenReturn(user2);

		List<String> mails = new ArrayList<String>();
		mails.add(mail1);
		mails.add(mail2);

		when(mapper.entityToDto(Matchers.any(UserSkill.class)))
				.thenReturn(usedMapper.entityToDto(user1.getUserSkillToSkillTreeUser().get(0).getUserSkill()))
				.thenReturn(usedMapper.entityToDto(user2.getUserSkillToSkillTreeUser().get(1).getUserSkill()));

		/*
		 * When
		 */
		List<UserSkillDTO> userSkillDtos = userSkilLService.findUserSkillsFromTeam(mails);
		/*
		 * Then
		 */
		assertNotNull(userSkillDtos);
		assertTrue(userSkillDtos.size() == 2);
		assertEquals(user1.getUserSkillToSkillTreeUser().get(0).getUserSkill().getName(), userSkillDtos.get(0).getName());
		assertEquals(user2.getUserSkillToSkillTreeUser().get(1).getUserSkill().getName(), userSkillDtos.get(1).getName());

	}

	/*
	 * HELPER
	 */
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

	private Timestamp getTimestamp() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse("01/01/2000");
		long time = date.getTime();
		return new Timestamp(time);
	}
}
