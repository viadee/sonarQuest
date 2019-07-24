package com.viadee.sonarquest.skillTree.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.math.MathContext;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.entities.Role;
import com.viadee.sonarquest.entities.RoleName;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.UserRepository;
import com.viadee.sonarquest.skillTree.dto.UserSkillDTO;
import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillGroup;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skillTree.repositories.SonarRuleRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillGroupRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillToSkillTreeUserRepository;
import com.viadee.sonarquest.skillTree.utils.mapper.UserSkillDtoEntityMapper;

import ch.qos.logback.core.boolex.Matcher;

@RunWith(MockitoJUnitRunner.class)
public class UserSkillServiceTest {

	@Mock
	private SkillTreeUserService skillTreeUserService;

	@Mock
	private UserSkillDtoEntityMapper mapper;

	@Mock
	private SonarRuleRepository sonarRuleRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserSkillRepository userSkillRepository;

	@Mock
	private UserSkillGroupRepository userSkillGroupRepository;

	@Mock
	private UserSkillToSkillTreeUserRepository userSkillToSkillTreeUserRepository;

	@Mock
	private UserSkillService userSkillService;

	@InjectMocks
	private UserSkillService service;

	@InjectMocks
	private UserSkillDtoEntityMapper usedMapper;

	private String mail1;
	private String mail2;
	private SkillTreeUser user1;
	private SkillTreeUser user2;
	private List<String> mails;
	private UserSkillGroup userSkillGroup;

	@Before
	public void init() {
		mail1 = "test1@test.de";
		user1 = new SkillTreeUser();
		user1.setId(Long.valueOf(1L));
		user1.setMail(mail1);
		generateUserSkillToSkillTreeUserMocks(user1, generateUserSkillMocks());

		mail2 = "test2@test.de";
		user2 = new SkillTreeUser();
		user2.setId(Long.valueOf(2L));
		user2.setMail(mail1);
		generateUserSkillToSkillTreeUserMocks(user2, generateUserSkillMocks());

		mails = new ArrayList<String>();
		mails.add(mail1);
		mails.add(mail2);

		// Mock UserSkillGrouop
		userSkillGroup = new UserSkillGroup(1L, "Test UserSkillGroup", false);
	}

	@Test
	public void findUserSkillsFromTeam_MailNotNull() {
		/*
		 * Given
		 */

		when(skillTreeUserService.findByMail(mail2)).thenReturn(user2);

		when(mapper.entityToDto(Matchers.any(UserSkill.class)))
				.thenReturn(usedMapper.entityToDto(user1.getUserSkillToSkillTreeUser().get(0).getUserSkill()))
				.thenReturn(usedMapper.entityToDto(user2.getUserSkillToSkillTreeUser().get(1).getUserSkill()));

		/*
		 * When
		 */
		List<UserSkillDTO> userSkillDtos = service.findUserSkillsFromTeam(mails);
		/*
		 * Then
		 */
		assertNotNull(userSkillDtos);
		assertTrue(userSkillDtos.size() == 2);
		assertEquals(user1.getUserSkillToSkillTreeUser().get(0).getUserSkill().getName(),
				userSkillDtos.get(0).getName());
		assertEquals(user2.getUserSkillToSkillTreeUser().get(1).getUserSkill().getName(),
				userSkillDtos.get(1).getName());

	}

	@Test
	public void testGetScoringForRuleFromTeam() {
		/*
		 * Given
		 */

		// Mock SonarRule
		SonarRule sonarRule = new SonarRule();
		sonarRule.setId(1L);
		sonarRule.setAddedAt(getTimestamp());
		sonarRule.setKey("Test:Key");
		sonarRule.setName("Test SonarRule");
		sonarRule.setUserSkill(user1.getUserSkillToSkillTreeUser().get(0).getUserSkill());

		when(sonarRuleRepository.findSonarRuleByKey(anyString())).thenReturn(sonarRule);
		when(skillTreeUserService.findByMail(mail1)).thenReturn(user1);
		when(skillTreeUserService.findByMail(mail2)).thenReturn(user2);

		// Mock User
		User user = new User();
		Role role = new Role();
		role.setName(RoleName.DEVELOPER);
		user.setRole(role);
		when(userRepository.findByMail(anyString())).thenReturn(user);

		/*
		 * When
		 */
		Double scoreFromMethod = service.getScoringForRuleFromTeam(sonarRule.getKey(), mails);

		assertNotNull(scoreFromMethod);
		assertEquals(scoreFromMethod, 2, 5);
	}

	@Test
	public void testCreateUserSkill() {
		/*
		 * Given
		 */

		// Create UserSkill Mock
		UserSkill userSkill = new UserSkill();
		userSkill.setDescription("Test description1");
		userSkill.setId(11L);
		userSkill.addSonarRule(new SonarRule());
		userSkill.setName("Test UserSkill1");
		userSkill.setRequiredRepetitions(3);
		userSkill.setRoot(false);
		userSkill.setUserSkillGroup(userSkillGroup);

		// Create following UserSkill Mock
		UserSkill followingUserSkill = new UserSkill();
		followingUserSkill.setDescription("Test description2");
		followingUserSkill.setId(12L);
		followingUserSkill.addSonarRule(new SonarRule());
		followingUserSkill.setName("Test UserSkill2");
		followingUserSkill.setRequiredRepetitions(3);
		followingUserSkill.setRoot(false);
		followingUserSkill.setUserSkillGroup(userSkillGroup);

		// Create previous UserSkill Mock
		UserSkill previousUserSkill = new UserSkill();
		previousUserSkill.setDescription("Test description3");
		previousUserSkill.setId(13L);
		previousUserSkill.addSonarRule(new SonarRule());
		previousUserSkill.setName("Test UserSkill3");
		previousUserSkill.setRequiredRepetitions(3);
		previousUserSkill.setRoot(false);
		previousUserSkill.setUserSkillGroup(userSkillGroup);

		// Link UserSkills
		userSkill.addFollowingUserSkill(followingUserSkill);
		userSkill.addPreviousUserSkill(previousUserSkill);

		when(userSkillRepository.findOne(anyLong())).thenReturn(followingUserSkill).thenReturn(previousUserSkill);
		when(sonarRuleRepository.findOne(anyLong())).thenReturn(userSkill.getSonarRules().get(0));
		when(userSkillGroupRepository.findOne(anyLong())).thenReturn(userSkillGroup);
		when(userSkillRepository.save(Matchers.any(UserSkill.class))).thenReturn(userSkill);

		// Create List with mocked SkillTreeUsern
		List<SkillTreeUser> users = new ArrayList<SkillTreeUser>();
		users.add(user1);
		users.add(user2);
		when(skillTreeUserService.findAll()).thenReturn(users);

		when(userSkillToSkillTreeUserRepository.save(Matchers.any(UserSkillToSkillTreeUser.class))).thenReturn(null);
		when(skillTreeUserService.save(Matchers.any(SkillTreeUser.class))).thenReturn(null);

		when(userRepository.findByUsername(anyString())).thenReturn(null);

		// Mock Principal
		Principal mockPrincipal = Mockito.mock(Principal.class);

		// Not running recalculation
		doNothing().when(userSkillService).recalculateWholeUserSkillScore();
//		when(userSkillToSkillTreeUserRepository.findAll()).thenReturn(user2.getUserSkillToSkillTreeUser());
//		when(userSkillToSkillTreeUserRepository.save(Matchers.any(UserSkillToSkillTreeUser.class))).thenReturn(null);

		/*
		 * When
		 */

		UserSkill userSkillFromMethod = service.createUserSkill(userSkill, Long.valueOf(1), mockPrincipal);

		/*
		 * Then
		 */

		assertNotNull(userSkillFromMethod);
		assertTrue(
				userSkillFromMethod.getSonarRules().get(0).getUserSkill().getId().equals(userSkillFromMethod.getId()));
		assertTrue(user1.getUserSkillToSkillTreeUser().size() == 11);
	}

	@Test
	public void testCalculateUserSkillScore() {
		/*
		 * Given
		 */
		mockDataForCalculateUserSkillScoreTest();
		/*
		 * When
		 */
		Double score = service.calculateUserSkillScore(user1.getUserSkillToSkillTreeUser().get(2).getUserSkill(), user1);
		/*
		 * Then
		 */
		
		assertTrue(6.0 == score);
	}

	private void mockDataForCalculateUserSkillScoreTest() {
		user1.getUserSkillToSkillTreeUser().clear();
		List<UserSkill> userSkills = new ArrayList<UserSkill>();

		// Create UserSkills
		for (int i = 0; i < 7; i++) {
			UserSkill userSkill = new UserSkill();
			userSkill.setDescription("Test description" + i);
			userSkill.setId(Long.valueOf(i));
			userSkill.addSonarRule(new SonarRule());
			userSkill.setName("Test UserSkill" + i);
			userSkill.setRequiredRepetitions(3);
			userSkill.setRoot(false);
			userSkill.setUserSkillGroup(userSkillGroup);
			userSkills.add(userSkill);
		}

		// Link UserSkills to build tree structure
		userSkills.get(0).addFollowingUserSkill(userSkills.get(1));
		userSkills.get(0).addFollowingUserSkill(userSkills.get(4));
		userSkills.get(1).addFollowingUserSkill(userSkills.get(2));
		userSkills.get(1).addFollowingUserSkill(userSkills.get(3));
		userSkills.get(4).addFollowingUserSkill(userSkills.get(5));
		userSkills.get(5).addFollowingUserSkill(userSkills.get(6));

		userSkills.get(1).addPreviousUserSkill(userSkills.get(0));
		userSkills.get(4).addPreviousUserSkill(userSkills.get(0));
		userSkills.get(2).addPreviousUserSkill(userSkills.get(1));
		userSkills.get(3).addPreviousUserSkill(userSkills.get(1));
		userSkills.get(5).addPreviousUserSkill(userSkills.get(4));
		userSkills.get(6).addPreviousUserSkill(userSkills.get(5));

		// Integrate new UserSkills in UserSkillToSkilLTreeUser-objects from user2
		for (int i = 0; i < userSkills.size(); i++) {
			UserSkillToSkillTreeUser userSkillToSkillTreeUser = new UserSkillToSkillTreeUser();
			userSkillToSkillTreeUser.setId(Long.valueOf(i + 10));
			userSkillToSkillTreeUser.setLearnedOn(null);
			userSkillToSkillTreeUser.setRepeats(i == 0 ? 1 : 0);
			userSkillToSkillTreeUser.setScore(5.0);
			userSkillToSkillTreeUser.setSkillTreeUser(user1);
			userSkillToSkillTreeUser.setUserSkill(userSkills.get(i));
			userSkills.get(i).addUserSkillToSkillTreeUsers(userSkillToSkillTreeUser);
			user1.addUserSkillToSkillTreeUser(userSkillToSkillTreeUser);
			user1.getUserSkillToSkillTreeUser().add(userSkillToSkillTreeUser);
		}

	}

	/*
	 * HELPER
	 */
	private List<UserSkillToSkillTreeUser> generateUserSkillToSkillTreeUserMocks(SkillTreeUser user,
			List<UserSkill> userSkills) {
		List<UserSkillToSkillTreeUser> userSkillToSkillTreeUsers = new ArrayList<UserSkillToSkillTreeUser>();
		for (int i = 0; i < userSkills.size(); i++) {
			UserSkillToSkillTreeUser userSkillToSkillTreeUser = new UserSkillToSkillTreeUser();
			userSkillToSkillTreeUser.setId(Long.valueOf(i));
			userSkillToSkillTreeUser.setLearnedOn(getTimestamp());
			userSkillToSkillTreeUser.setRepeats(3);
			userSkillToSkillTreeUser.setScore(5.0);
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

	private Timestamp getTimestamp() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = dateFormat.parse("01/01/2000");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time = date.getTime();
		return new Timestamp(time);
	}

	private List<UserSkill> getPreviousUserSkillsFromSkillTreeUser(SkillTreeUser user) {
		List<UserSkill> previousUserSkills = new ArrayList<UserSkill>();
		for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : user.getUserSkillToSkillTreeUser()) {
			if (userSkillToSkillTreeUser.getUserSkill().getPreviousUserSkills().size() > 0) {
				for (UserSkill prviousUserSkill : userSkillToSkillTreeUser.getUserSkill().getPreviousUserSkills()) {
					previousUserSkills.add(prviousUserSkill);
				}
			}
		}
		return previousUserSkills;
	}

	private List<UserSkill> getFollowingUserSkillsFromSkillTreeUser(SkillTreeUser user) {
		List<UserSkill> followingUserSkills = new ArrayList<UserSkill>();
		for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : user.getUserSkillToSkillTreeUser()) {
			if (userSkillToSkillTreeUser.getUserSkill().getFollowingUserSkills().size() > 0) {
				for (UserSkill prviousUserSkill : userSkillToSkillTreeUser.getUserSkill().getFollowingUserSkills()) {
					followingUserSkills.add(prviousUserSkill);
				}
			}
		}
		return followingUserSkills;
	}
}
