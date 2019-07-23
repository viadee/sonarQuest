package com.viadee.sonarquest.skillTree.services;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.services.ArtefactService;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeDiagramDTO;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeLinksDTO;
import com.viadee.sonarquest.skillTree.dto.skillTreeDiagram.SkillTreeObjectDTO;
import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillGroup;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skillTree.repositories.UserSkillGroupRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillRepository;

@Service
public class SkillTreeService {

	@Autowired
	private SkillTreeUserService skillTreeUserService;

	@Autowired
	private UserSkillGroupRepository userSkillGroupRepository;

	@Autowired
	private UserSkillRepository userSkillRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(ArtefactService.class);

	@Transactional
	public SkillTreeDiagramDTO generateGroupSkillTree() {
		List<UserSkillGroup> userSkillGroups = userSkillGroupRepository.findAll();
		SkillTreeDiagramDTO skillTreeDiagramDTO = new SkillTreeDiagramDTO();

		for (UserSkillGroup userSkillGroup : userSkillGroups) {
			skillTreeDiagramDTO.addNode(new SkillTreeObjectDTO(String.valueOf(userSkillGroup.getId()),
					String.valueOf(userSkillGroup.getName()), userSkillGroup.getIcon(), userSkillGroup.isRoot()));
			for (UserSkillGroup followingUserSkillGroup : userSkillGroup.getFollowingUserSkillGroups()) {
				skillTreeDiagramDTO.addLine(new SkillTreeLinksDTO(String.valueOf(userSkillGroup.getId()),
						String.valueOf(followingUserSkillGroup.getId())));
			}
		}
		return skillTreeDiagramDTO;

	}

	@Transactional
	public SkillTreeDiagramDTO generateSkillTreeForUserByGroupID(Long id, String mail) {
		SkillTreeDiagramDTO skillTreeDiagramDTO = new SkillTreeDiagramDTO();

		/**
		 * Generate Skill-Tree for admin => mail == null
		 */
		if (mail == null || mail.isEmpty()) {
			List<UserSkill> userSkills = userSkillRepository.findUserSkillsByGroup(id);
			for (UserSkill userSkill : userSkills) {
				skillTreeDiagramDTO.addNode(this.buildSkillTreeObject(new UserSkillToSkillTreeUser(userSkill, 0)));
				for (UserSkill followingUserSkill : userSkill.getFollowingUserSkills()) {
					skillTreeDiagramDTO.addLine(new SkillTreeLinksDTO(String.valueOf(userSkill.getId()),
							String.valueOf(followingUserSkill.getId())));
				}
			}
		} else {
			/**
			 * Generate Skill-Tree for user
			 */
			SkillTreeUser user = skillTreeUserService.findByMail(mail);
			if (user == null) {
				LOGGER.info("User with mail: {}  does not exist yet - creating it...", mail);
				user = skillTreeUserService.createSkillTreeUser(mail);
			}
			/**
			 * Handle root userskills
			 */
			List<UserSkill> userSkills = userSkillRepository.findRootUserSkillByGroupID(id, true);
			for (UserSkill userSKill : userSkills) {
				SkillTreeObjectDTO object = this.buildSkillTreeObject(
						new UserSkillToSkillTreeUser(userSKill, getColorForRootObjectFromUserByGroupID(user, id),
								isGroupLearnedCompletlyFromUserByGroupID(user, id), user));

				// -1 because the root UserSkill doesn't matter
				object.setLearnCoverage(calculateCoverage(this.getAmountOfLearndSkillsFromUserByGroupID(user, id),
						Double.valueOf(userSkillRepository.findUserSkillsByGroup(id).size() - 1)));
				skillTreeDiagramDTO.addNode(object);
				for (UserSkill followingUserSkill : userSKill.getFollowingUserSkills()) {
					skillTreeDiagramDTO.addLine(new SkillTreeLinksDTO(String.valueOf(userSKill.getId()),
							String.valueOf(followingUserSkill.getId())));
				}
			}

			/**
			 * Generate Skill-Tree nodes for userskills which are not root
			 */
			for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : user.getUserSkillToSkillTreeUser()) {
				if (userSkillToSkillTreeUser.getUserSkill().getUserSkillGroup().getId().equals(id)) {
					SkillTreeObjectDTO object = this.buildSkillTreeObject(userSkillToSkillTreeUser);
					object.setLearnCoverage(calculateCoverage(Double.valueOf(userSkillToSkillTreeUser.getRepeats()),
							Double.valueOf(userSkillToSkillTreeUser.getUserSkill().getRequiredRepetitions())));
					skillTreeDiagramDTO.addNode(object);
					for (UserSkill followingUserSkill : userSkillToSkillTreeUser.getUserSkill()
							.getFollowingUserSkills()) {
						skillTreeDiagramDTO.addLine(
								new SkillTreeLinksDTO(String.valueOf(userSkillToSkillTreeUser.getUserSkill().getId()),
										String.valueOf(followingUserSkill.getId())));
					}
				}
			}

		}

		return skillTreeDiagramDTO;
	}

	private Timestamp isGroupLearnedCompletlyFromUserByGroupID(SkillTreeUser user, Long id) {
		int counter = 0;
		List<UserSkillToSkillTreeUser> userSkillToUserByGroupID = user.getUserSkillToSkillTreeUser().stream()
				.filter(userSkillToUser -> userSkillToUser.getUserSkill().getUserSkillGroup().getId().equals(id))
				.collect(Collectors.toList());
		for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : userSkillToUserByGroupID) {
			if (userSkillToSkillTreeUser.getLearnedOn() != null) {
				counter++;
			}
		}
		if (userSkillToUserByGroupID.size() == counter) {
			return new Timestamp(System.currentTimeMillis());
		}
		return null;
	}

	private Timestamp isGroupLearnedCompletlyFromTeamByGroupID(List<String> mails, Long id) {
		List<UserSkillToSkillTreeUser> userSkillToUserByGroupID = null;
		int counter = 0;
		for (String mail : mails) {
			SkillTreeUser user = skillTreeUserService.findByMail(mail);
			if (user != null) {
				userSkillToUserByGroupID = user.getUserSkillToSkillTreeUser().stream().filter(
						userSkillToUser -> userSkillToUser.getUserSkill().getUserSkillGroup().getId().equals(id))
						.collect(Collectors.toList());
				for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : userSkillToUserByGroupID) {
					if (userSkillToSkillTreeUser.getLearnedOn() != null) {
						counter++;
					}
				}
			}
		}
		if (userSkillToUserByGroupID != null) {
			if (userSkillToUserByGroupID.size() * mails.size() == counter) {
				return new Timestamp(System.currentTimeMillis());
			}
		}

		return null;
	}

	private int getColorForRootObjectFromUserByGroupID(SkillTreeUser user, Long id) {
		List<UserSkillToSkillTreeUser> userSkillToUserByGroupID = user.getUserSkillToSkillTreeUser().stream()
				.filter(userSkillToUser -> userSkillToUser.getUserSkill().getUserSkillGroup().getId().equals(id))
				.collect(Collectors.toList());
		for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : userSkillToUserByGroupID) {
			if (userSkillToSkillTreeUser.getLearnedOn() != null) {
				return 1;
			}
		}
		return 0;
	}

	private int getColorForRootObjectFromTeamByGroupID(List<String> mails, Long id) {
		List<UserSkillToSkillTreeUser> userSkillToUserByGroupID = null;
		for (String mail : mails) {
			SkillTreeUser user = skillTreeUserService.findByMail(mail);
			if (user != null && user.getUserSkillToSkillTreeUser() != null) {
				userSkillToUserByGroupID = user.getUserSkillToSkillTreeUser().stream().filter(
						userSkillToUser -> userSkillToUser.getUserSkill().getUserSkillGroup().getId().equals(id))
						.collect(Collectors.toList());
				for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : userSkillToUserByGroupID) {
					if (userSkillToSkillTreeUser.getLearnedOn() != null) {
						return 1;
					}
				}
			}
		}
		return 0;
	}

	private Double getAmountOfLearndSkillsFromUserByGroupID(SkillTreeUser user, Long id) {
		List<UserSkillToSkillTreeUser> userSkillToUserByGroupID = user.getUserSkillToSkillTreeUser().stream()
				.filter(userSkillToUser -> userSkillToUser.getUserSkill().getUserSkillGroup().getId().equals(id))
				.collect(Collectors.toList());
		Double counter = 0.0;
		for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : userSkillToUserByGroupID) {
			if (userSkillToSkillTreeUser.getLearnedOn() != null) {
				counter++;
			}
		}
		return counter;
	}

	private Double getAmountOfLearndSkillsFromTeamByGroupID(List<String> mails, Long id) {
		List<UserSkillToSkillTreeUser> userSkillToUserByGroupID = null;
		Map<Long, Integer> completyLearnedUserSkills = new HashMap<Long, Integer>();
		Double counter = 0.0;
		for (String mail : mails) {
			SkillTreeUser user = skillTreeUserService.findByMail(mail);
			if (user != null) {
				userSkillToUserByGroupID = user.getUserSkillToSkillTreeUser().stream().filter(
						userSkillToUser -> userSkillToUser.getUserSkill().getUserSkillGroup().getId().equals(id))
						.collect(Collectors.toList());

				for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : userSkillToUserByGroupID) {
					if (userSkillToSkillTreeUser.getLearnedOn() != null) {
						Long userSkillId = userSkillToSkillTreeUser.getUserSkill().getId();
						if (completyLearnedUserSkills.containsKey(userSkillId)) {
							int count = completyLearnedUserSkills.get(userSkillId);
							count++;
							completyLearnedUserSkills.put(userSkillId, count);
						} else {
							completyLearnedUserSkills.put(userSkillId, 1);
						}
					}
				}
			}

		}
		for (Entry<Long, Integer> entry : completyLearnedUserSkills.entrySet()) {
			if (entry.getValue().equals(mails.size())) {
				counter++;
			}
		}
		return counter;
	}

	@Transactional
	public SkillTreeDiagramDTO generateSkillTreeForTeamByGroupID(Long id, List<String> mails) {
		SkillTreeDiagramDTO skillTreeDiagramDTO = new SkillTreeDiagramDTO();
		Map<Long, Integer> learnedUserSkills = new HashMap<Long, Integer>();
		List<UserSkill> userSkills = userSkillRepository.findRootUserSkillByGroupID(id, true);
		if (mails != null) {
			int loopCount = 0;

			/**
			 * Generate Skill-Tree Nodes from UserSkill which are not root
			 **/
			for (String mail : mails) {
				if (mail != null) {
					if (!mail.isEmpty() || !mail.equalsIgnoreCase("null")) {

						SkillTreeUser user = skillTreeUserService.findByMail(mail);
						if (user == null) {
							LOGGER.info("User with mail: {}  does not exist yet - creating it...", mail);
							user = skillTreeUserService.createSkillTreeUser(mail);
						}

						List<UserSkillToSkillTreeUser> userSkillToSkillTreeUsers = user.getUserSkillToSkillTreeUser()
								.stream().filter(skillToUser -> skillToUser.getUserSkill().getUserSkillGroup().getId()
										.equals(id))
								.collect(Collectors.toList());
						for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : userSkillToSkillTreeUsers) {
							if (loopCount == 0) {
								skillTreeDiagramDTO.addNode(this.buildSkillTreeObject(userSkillToSkillTreeUser));
								for (UserSkill followingUserSkill : userSkillToSkillTreeUser.getUserSkill()
										.getFollowingUserSkills()) {
									skillTreeDiagramDTO.addLine(new SkillTreeLinksDTO(
											String.valueOf(userSkillToSkillTreeUser.getUserSkill().getId()),
											String.valueOf(followingUserSkill.getId())));
								}
							}
							if (userSkillToSkillTreeUser.getLearnedOn() != null) {
								UserSkill userSkill = userSkillToSkillTreeUser.getUserSkill();
								if (learnedUserSkills.containsKey(userSkill.getId())) {
									int count = learnedUserSkills.get(userSkill.getId());
									count++;
									learnedUserSkills.put(userSkill.getId(), count);
								} else {
									learnedUserSkills.put(userSkill.getId(), 1);
								}
							}

						}
					}
				}
				loopCount++;
			}

			/**
			 * Generate Skill-Tree nodes for root userskills
			 */
			for (UserSkill userSKill : userSkills) {

				SkillTreeObjectDTO object = this.buildSkillTreeObject(
						new UserSkillToSkillTreeUser(userSKill, getColorForRootObjectFromTeamByGroupID(mails, id),
								isGroupLearnedCompletlyFromTeamByGroupID(mails, id), new SkillTreeUser()));
				// -1 because the root UserSkill doesn't matter
				object.setLearnCoverage(calculateCoverage(this.getAmountOfLearndSkillsFromTeamByGroupID(mails, id),
						Double.valueOf(userSkillRepository.findUserSkillsByGroup(id).size() - 1)));
				skillTreeDiagramDTO.addNode(object);
				for (UserSkill followingUserSkill : userSKill.getFollowingUserSkills()) {
					skillTreeDiagramDTO.addLine(new SkillTreeLinksDTO(userSKill.getId(), followingUserSkill.getId()));
				}
			}

			/**
			 * Calculate theming and learnCoverage for root userskills
			 */
			for (SkillTreeObjectDTO entry : skillTreeDiagramDTO.getNodes()) {
				if (!entry.isRoot()) {
					int learnedUserSKillFrequency = 0;
					Long entryID = Long.parseLong(entry.getId());
					if (learnedUserSkills.containsKey(entryID)) {
						learnedUserSKillFrequency = learnedUserSkills.get(entryID);
					}
					if (learnedUserSKillFrequency > 0 && learnedUserSKillFrequency < mails.size()) {
						entry.setBackgroundColor("#FFA000");
						entry.setTextColor("#262626");
					} else if (learnedUserSKillFrequency == mails.size()) {
						entry.setBackgroundColor("#4CAF50");
						entry.setTextColor("#262626");
					} else {
						entry.setBackgroundColor("#c0c0c0");
						entry.setTextColor("#262626");
					}
					entry.setLearnCoverage(
							calculateCoverage(Double.valueOf(learnedUserSKillFrequency), Double.valueOf(mails.size())));
				}
			}
		}
		return skillTreeDiagramDTO;

	}

	private int calculateCoverage(Double numerator, Double denominator) {
		Double percentage = (numerator / denominator) * 100;
		int coverage = (int) Math.round(percentage);
		if (coverage > 100) {
			return 100;
		}
		return coverage;
	}

	private SkillTreeObjectDTO buildSkillTreeObject(UserSkillToSkillTreeUser userSkillToSkillTreeUser) {
		SkillTreeObjectDTO skillTreeObjectDTO = new SkillTreeObjectDTO();
		skillTreeObjectDTO.setId(String.valueOf(userSkillToSkillTreeUser.getUserSkill().getId()));
		skillTreeObjectDTO.setLabel(String.valueOf(userSkillToSkillTreeUser.getUserSkill().getName()));
		skillTreeObjectDTO.setDescription(userSkillToSkillTreeUser.getUserSkill().getDescription());
		skillTreeObjectDTO.setRepeats(userSkillToSkillTreeUser.getRepeats());
		skillTreeObjectDTO.setRequiredRepetitions(userSkillToSkillTreeUser.getUserSkill().getRequiredRepetitions());
		skillTreeObjectDTO.setGroupIcon(userSkillToSkillTreeUser.getUserSkill().getUserSkillGroup().getIcon());
		skillTreeObjectDTO.setRoot(userSkillToSkillTreeUser.getUserSkill().isRoot());
		for (SonarRule rule : userSkillToSkillTreeUser.getUserSkill().getSonarRules()) {
			skillTreeObjectDTO.addRuleKey(rule.getKey(), rule.getName());
		}
		if (userSkillToSkillTreeUser.getSkillTreeUser() != null) {
			if (userSkillToSkillTreeUser.getLearnedOn() != null) {
				skillTreeObjectDTO.setBackgroundColor("#4CAF50");
				skillTreeObjectDTO.setTextColor("#262626");
			} else if (userSkillToSkillTreeUser.getRepeats() > 0) {
				skillTreeObjectDTO.setBackgroundColor("#FFA000");
				skillTreeObjectDTO.setTextColor("#262626");
			} else {
				skillTreeObjectDTO.setBackgroundColor("#c0c0c0");
				skillTreeObjectDTO.setTextColor("#262626");
			}
		} else {
			skillTreeObjectDTO.setBackgroundColor("#c0c0c0");
			skillTreeObjectDTO.setTextColor("#262626");
		}

		return skillTreeObjectDTO;
	}

}
