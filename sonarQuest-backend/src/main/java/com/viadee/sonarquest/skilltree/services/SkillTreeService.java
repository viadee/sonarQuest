package com.viadee.sonarquest.skilltree.services;

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

import com.viadee.sonarquest.skilltree.dto.skilltreediagram.SkillTreeDiagramDTO;
import com.viadee.sonarquest.skilltree.dto.skilltreediagram.SkillTreeLinksDTO;
import com.viadee.sonarquest.skilltree.dto.skilltreediagram.SkillTreeObjectDTO;
import com.viadee.sonarquest.skilltree.entities.SkillTreeUser;
import com.viadee.sonarquest.skilltree.entities.SonarRule;
import com.viadee.sonarquest.skilltree.entities.UserSkill;
import com.viadee.sonarquest.skilltree.entities.UserSkillGroup;
import com.viadee.sonarquest.skilltree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skilltree.repositories.UserSkillGroupRepository;
import com.viadee.sonarquest.skilltree.repositories.UserSkillRepository;

@Service
public class SkillTreeService {

    private static final String TEXTCOLOR = "#262626";
    private static final String UNLEARNEDCOLOR = "#c0c0c0";
    private static final String INPROGRESSCOLOR = "#FFA000";
    private static final String COMPLETECOLOR = "#4CAF50";

    @Autowired
    private SkillTreeUserService skillTreeUserService;

    @Autowired
    private UserSkillGroupRepository userSkillGroupRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SkillTreeService.class);

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
            mail = mail.replaceAll("[\n|\r|\t]", "");
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
                        Double.valueOf(userSkillRepository.findUserSkillsByGroup(id).size() - 1.0)));
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
        if (userSkillToUserByGroupID != null && (userSkillToUserByGroupID.size() * mails.size()) == counter) {
            return new Timestamp(System.currentTimeMillis());
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
        List<UserSkillToSkillTreeUser> userSkillToUserByGroupID ;
        Map<Long, Integer> completyLearnedUserSkills = new HashMap<>();
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
        Map<Long, Integer> learnedUserSkills = new HashMap<>();
        List<UserSkill> userSkills = userSkillRepository.findRootUserSkillByGroupID(id, true);
        if (mails != null) {
            int loopCount = 0;

            /**
             * Generate Skill-Tree Nodes from UserSkill which are not root
             **/
            for (String mail : mails) {
                if (mail != null || !mail.isEmpty() || !mail.equalsIgnoreCase("null")) {


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
                        Double.valueOf(userSkillRepository.findUserSkillsByGroup(id).size() - 1.0)));
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
                        entry.setBackgroundColor(INPROGRESSCOLOR);
                        entry.setTextColor(TEXTCOLOR);
                    } else if (learnedUserSKillFrequency == mails.size()) {
                        entry.setBackgroundColor(COMPLETECOLOR);
                        entry.setTextColor(TEXTCOLOR);
                    } else {
                        entry.setBackgroundColor(UNLEARNEDCOLOR);
                        entry.setTextColor(TEXTCOLOR);
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
                skillTreeObjectDTO.setBackgroundColor(COMPLETECOLOR);
                skillTreeObjectDTO.setTextColor(TEXTCOLOR);
            } else if (userSkillToSkillTreeUser.getRepeats() > 0) {
                skillTreeObjectDTO.setBackgroundColor(INPROGRESSCOLOR);
                skillTreeObjectDTO.setTextColor(TEXTCOLOR);
            } else {
                skillTreeObjectDTO.setBackgroundColor(UNLEARNEDCOLOR);
                skillTreeObjectDTO.setTextColor(TEXTCOLOR);
            }
        } else {
            skillTreeObjectDTO.setBackgroundColor(UNLEARNEDCOLOR);
            skillTreeObjectDTO.setTextColor(TEXTCOLOR);
        }

        return skillTreeObjectDTO;
    }

}
