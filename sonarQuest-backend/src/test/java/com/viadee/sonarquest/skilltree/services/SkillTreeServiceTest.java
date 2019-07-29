package com.viadee.sonarquest.skilltree.services;

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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.skilltree.dto.skilltreediagram.SkillTreeDiagramDTO;
import com.viadee.sonarquest.skilltree.dto.skilltreediagram.SkillTreeObjectDTO;
import com.viadee.sonarquest.skilltree.entities.SkillTreeUser;
import com.viadee.sonarquest.skilltree.entities.UserSkill;
import com.viadee.sonarquest.skilltree.entities.UserSkillGroup;
import com.viadee.sonarquest.skilltree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skilltree.repositories.UserSkillGroupRepository;
import com.viadee.sonarquest.skilltree.repositories.UserSkillRepository;

@RunWith(MockitoJUnitRunner.class)
public class SkillTreeServiceTest {

	@Mock
	private UserSkillGroupRepository userSkillGroupRepository;

	@Mock
	private SkillTreeUserService skillTreeUserService;

	@Mock
	private UserSkillRepository userSkillRepository;

	@InjectMocks
	private SkillTreeService skillTreeService;

	@Test
	public void testGenerateGroupSkillTree() {
		// Given
		List<UserSkillGroup> groupMocks = genrateUserSkillGroupMocks();
		when(userSkillGroupRepository.findAll()).thenReturn(groupMocks);

		// When
		SkillTreeDiagramDTO skillTreeFromMethod = skillTreeService.generateGroupSkillTree();

		// Then
		assertNotNull(skillTreeFromMethod);
		assertTrue(skillTreeFromMethod.getLinks().size() > 0);
		assertTrue(skillTreeFromMethod.getLinks().size() == 9);

		assertTrue(skillTreeFromMethod.getNodes().size() > 0);
		assertTrue(skillTreeFromMethod.getNodes().size() == 10);
	}

	@Test
	public void generateSkillTreeForUserByGroupID_WithMail() {
		/*
		 * Given
		 */

		// Generate SkillTreeUser with all Dependencies
		Long id = 1L;
		String mail = "test@test.de";
		SkillTreeUser user = new SkillTreeUser();
		user.setId(Long.valueOf(1L));
		user.setMail(mail);
		generateUserSkillToSkillTreeUserMocks(user, generateUserSkillMocks());

		when(skillTreeUserService.findByMail(mail)).thenReturn(user);

		// Mock root UserSkill from group
		List<UserSkill> rootUserSkill = generateRootUserSkillFromList(user.getUserSkillToSkillTreeUser());
		when(userSkillRepository.findRootUserSkillByGroupID(id, true)).thenReturn(rootUserSkill);

		// Mock UserSkills from group
		List<UserSkill> allUserSkillsFromGroupMock = getUserSkillMocksFromList(user.getUserSkillToSkillTreeUser());
		allUserSkillsFromGroupMock.add(rootUserSkill.get(0));
		when(userSkillRepository.findUserSkillsByGroup(id)).thenReturn(allUserSkillsFromGroupMock);

		/*
		 * When
		 */
		SkillTreeDiagramDTO skillTreeFromMethod = skillTreeService.generateSkillTreeForUserByGroupID(id, mail);

		/*
		 * Then
		 */

		assertNotNull(skillTreeFromMethod);

		assertTrue(skillTreeFromMethod.getLinks().size() > 0);
		assertTrue(skillTreeFromMethod.getLinks().size() == 10);

		assertTrue(skillTreeFromMethod.getNodes().size() > 0);
		assertTrue(skillTreeFromMethod.getNodes().size() == 11);

		assertNotNull(
				skillTreeFromMethod.getNodes().stream().filter(SkillTreeObjectDTO::isRoot).findFirst().orElse(null));
		for (SkillTreeObjectDTO object : skillTreeFromMethod.getNodes()) {
			if (object.isRoot()) {
				assertEquals(100,object.getLearnCoverage());
				break;
			}

		}
	}

	@Test
	public void generateSkillTreeForUserByGroupID_WithoutMail() {
		/*
		 * Given
		 */
		Long id = 1L;
		String mail = "";

		// Mock UserSkills from group
		List<UserSkill> allUserSkillsFromGroupMock = generateUserSkillMocks();
		UserSkill rootUserSkill = new UserSkill();
		rootUserSkill.setId(0L);
		rootUserSkill.setRequiredRepetitions(3);
		rootUserSkill.setName("RootUserSkill");
		rootUserSkill.addFollowingUserSkill(allUserSkillsFromGroupMock.get(0));
		rootUserSkill.setUserSkillGroup(new UserSkillGroup(Long.valueOf(1L), "Group1", false));
		rootUserSkill.setRoot(true);
		allUserSkillsFromGroupMock.get(0).addPreviousUserSkill(rootUserSkill);
		allUserSkillsFromGroupMock.add(rootUserSkill);
		when(userSkillRepository.findUserSkillsByGroup(id)).thenReturn(allUserSkillsFromGroupMock);

		/*
		 * When
		 */
		SkillTreeDiagramDTO skillTreeFromMethod = skillTreeService.generateSkillTreeForUserByGroupID(id, mail);

		/*
		 * Then
		 */

		assertNotNull(skillTreeFromMethod);

		assertTrue(skillTreeFromMethod.getLinks().size() > 0);
		assertTrue(skillTreeFromMethod.getLinks().size() == 10);

		assertTrue(skillTreeFromMethod.getNodes().size() > 0);
		assertTrue(skillTreeFromMethod.getNodes().size() == 11);

		assertNotNull(
				skillTreeFromMethod.getNodes().stream().filter(SkillTreeObjectDTO::isRoot).findFirst().orElse(null));
		for (SkillTreeObjectDTO object : skillTreeFromMethod.getNodes()) {
			assertEquals(0,object.getLearnCoverage());

		}
	}

	@Test
	public void generateSkillTreeForTeamByGroupID() {
		/*
		 * Given
		 */
		Long id = 1L;
		List<String> mails = new ArrayList<String>();
		mails.add("test1@test.de");
		mails.add("test2@tes.de");
		mails.add("");

		// Mock SkillTreeUser
		SkillTreeUser user1 = new SkillTreeUser();
		user1.setId(Long.valueOf(1L));
		user1.setMail(mails.get(0));
		generateUserSkillToSkillTreeUserMocks(user1, generateUserSkillMocks());

		SkillTreeUser user2 = new SkillTreeUser();
		user2.setId(Long.valueOf(2L));
		user2.setMail(mails.get(1));
		generateUserSkillToSkillTreeUserMocks(user2, generateUserSkillMocks());

		SkillTreeUser user3 = new SkillTreeUser();
		user3.setId(Long.valueOf(3L));
		user3.setMail(mails.get(2));
		generateUserSkillToSkillTreeUserMocks(user3, generateUserSkillMocks());

		when(skillTreeUserService.findByMail(mails.get(0))).thenReturn(user1);
		when(skillTreeUserService.findByMail(mails.get(1))).thenReturn(user2);
		when(skillTreeUserService.findByMail(mails.get(2))).thenReturn(user3);

		// Mock root UserSkill from group
		List<UserSkill> rootUserSkill = generateRootUserSkillFromList(user1.getUserSkillToSkillTreeUser());
		when(userSkillRepository.findRootUserSkillByGroupID(id, true)).thenReturn(rootUserSkill);

		// Mock UserSkills from group
		List<UserSkill> allUserSkillsFromGroupMock = getUserSkillMocksFromList(user1.getUserSkillToSkillTreeUser());
		allUserSkillsFromGroupMock.add(rootUserSkill.get(0));
		when(userSkillRepository.findUserSkillsByGroup(id)).thenReturn(allUserSkillsFromGroupMock);

		/*
		 * When
		 */
		SkillTreeDiagramDTO skillTreeFromMethod = skillTreeService.generateSkillTreeForTeamByGroupID(id, mails);

		/*
		 * Then
		 */

		assertNotNull(skillTreeFromMethod);

		assertTrue(skillTreeFromMethod.getLinks().size() > 0);
		assertTrue(skillTreeFromMethod.getLinks().size() == 10);

		assertTrue(skillTreeFromMethod.getNodes().size() > 0);
		assertTrue(skillTreeFromMethod.getNodes().size() == 11);

		assertNotNull(
				skillTreeFromMethod.getNodes().stream().filter(SkillTreeObjectDTO::isRoot).findFirst().orElse(null));
		for (SkillTreeObjectDTO object : skillTreeFromMethod.getNodes()) {
			if (object.isRoot()) {
				assertEquals(100,object.getLearnCoverage());
				break;
			}

		}
	}

	/*
	 * HELPER
	 */

	private List<UserSkillGroup> genrateUserSkillGroupMocks() {
		List<UserSkillGroup> groupMocks = new ArrayList<UserSkillGroup>();
		for (int i = 0; i < 10; i++) {

			groupMocks.add(new UserSkillGroup(Long.valueOf(i + 1), "UserSkillGroup" + i + 1, i == 0 ? true : false));
			groupMocks.get(i).setIcon("ra-axes");
			if (i > 0) {
				groupMocks.get(i - 1).addFollowingUserSkillGroup(groupMocks.get(i));
			}
		}
		return groupMocks;
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

	private List<UserSkill> generateRootUserSkillFromList(List<UserSkillToSkillTreeUser> userSkillToSkillTreeUsers) {
		List<UserSkill> userSkills = new ArrayList<UserSkill>();
		UserSkill rootUserSkill = new UserSkill("Test Description", "RootSkill", true,
				new UserSkillGroup(Long.valueOf(1L), "Group1", false), 3);
		rootUserSkill.setId(0L);
		userSkillToSkillTreeUsers.get(0).getUserSkill().addPreviousUserSkill(rootUserSkill);
		rootUserSkill.addFollowingUserSkill(userSkillToSkillTreeUsers.get(0).getUserSkill());
		userSkills.add(rootUserSkill);
		return userSkills;
	}

	private List<UserSkill> getUserSkillMocksFromList(List<UserSkillToSkillTreeUser> userSkillToSkillTreeUsers) {
		List<UserSkill> userSkills = new ArrayList<UserSkill>();
		for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : userSkillToSkillTreeUsers) {
			userSkills.add(userSkillToSkillTreeUser.getUserSkill());
		}
		return userSkills;
	}

}
