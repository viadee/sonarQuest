package com.viadee.sonarquest.skilltree.services;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.controllers.WebSocketController;
import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.RoleName;
import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.UserRepository;
import com.viadee.sonarquest.services.StandardTaskService;
import com.viadee.sonarquest.skilltree.dto.UserSkillDTO;
import com.viadee.sonarquest.skilltree.entities.SkillTreeUser;
import com.viadee.sonarquest.skilltree.entities.SonarRule;
import com.viadee.sonarquest.skilltree.entities.UserSkill;
import com.viadee.sonarquest.skilltree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skilltree.repositories.SonarRuleRepository;
import com.viadee.sonarquest.skilltree.repositories.UserSkillGroupRepository;
import com.viadee.sonarquest.skilltree.repositories.UserSkillRepository;
import com.viadee.sonarquest.skilltree.repositories.UserSkillToSkillTreeUserRepository;
import com.viadee.sonarquest.skilltree.utils.mapper.UserSkillDtoEntityMapper;

@Service
public class UserSkillService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSkillService.class);

    private static final String MAPKEY_USERSKILLTOSKILLTREEUSER = "ustsku";
    private static final String MAPKEY_RECURSIONCOUNT = "recursionCount";

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private SonarRuleRepository sonarRuleRepository;

    @Autowired
    private SkillTreeUserService skillTreeUserService;

    @Autowired
    private UserSkillToSkillTreeUserRepository userSkillToSkillTreeUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSkillDtoEntityMapper mapper;

    @Autowired
    private UserSkillGroupRepository userSkillGroupRepository;

    @Autowired
    private WebSocketController webSocketController;

    @Autowired
    private StandardTaskService standardTaskService;

    private int recursionCount = 0;
    private List<Map<String, Object>> resultFollowingCalculateScore = new ArrayList<>();
    private List<Map<String, Object>> resultPreviousCalculateScore = new ArrayList<>();

    @Transactional
    public List<UserSkillDTO> findUserSkillsFromTeam(List<String> mails) {

        List<UserSkillDTO> userSkills = new ArrayList<>();
        for (String mail : mails) {
            if (mail != null || !mail.isEmpty() || !mail.equalsIgnoreCase("null")) {

                SkillTreeUser skillTreeUser = skillTreeUserService.findByMail(mail);
                if (skillTreeUser != null) {
                    for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : skillTreeUser
                            .getUserSkillToSkillTreeUser()) {
                        UserSkillDTO userSkillDTO = mapper.entityToDto(userSkillToSkillTreeUser.getUserSkill());
                        userSkillDTO.setScore(userSkillToSkillTreeUser.getScore());
                        if (userSkills.stream().filter(dto -> userSkillDTO.getId().equals(dto.getId())).findAny()
                                .orElse(null) == null) {
                            userSkills.add(userSkillDTO);

                        }
                    }

                }

            }
        }

        return userSkills;
    }

    public Double getScoringForRuleFromTeam(String ruleKey, List<String> mails) {
        SonarRule rule = sonarRuleRepository.findSonarRuleByKey(ruleKey);
        if (rule != null) {
            UserSkill userSkill = rule.getUserSkill();
            if (userSkill != null) {
                Double teamScore = null;
                int amountDevelopersInTeam = 0;
                for (String mail : mails) {
                    if (mail != null || !mail.isEmpty() || !mail.equalsIgnoreCase("null")) {
                        SkillTreeUser skillTreeUser = skillTreeUserService.findByMail(mail);
                        if (skillTreeUser != null && userRepository.findByMail(mail).getRole().getName().equals(RoleName.DEVELOPER)) {
                            amountDevelopersInTeam++;
                            UserSkillToSkillTreeUser userSkillToSkillTreeUser = findUserSkillToSkillTreeUserByUserSkillAndUser(
                                    userSkill, skillTreeUser);
                            if (userSkillToSkillTreeUser != null && userSkillToSkillTreeUser.getScore() != null) {
                                if (teamScore == null) {
                                    teamScore = 0.0;
                                }
                                teamScore = teamScore + userSkillToSkillTreeUser.getScore();
                            }
                        }
                    }
                }
                if (teamScore != null) {
                    return teamScore / amountDevelopersInTeam;
                }
            }

        }
        return null;
    }

    private UserSkillToSkillTreeUser findUserSkillToSkillTreeUserByUserSkillAndUser(UserSkill userSkill,
                                                                                    SkillTreeUser skillTreeUser) {
        return skillTreeUser.getUserSkillToSkillTreeUser().stream()
                .filter(uststu -> uststu.getUserSkill().getId().equals(userSkill.getId())).findFirst().orElse(null);
    }

    @Transactional
    public UserSkill createUserSkill(final UserSkill newUserSkill, final Long groupID, final Principal principal) {
        UserSkill userSkill = newUserSkill;
        List<UserSkill> newFollowingUserSkills = new ArrayList<>();
        List<UserSkill> newPreviousUserSkills = new ArrayList<>();
        List<SonarRule> newSonarRules = new ArrayList<>();

        for (UserSkill followingUserSkill : userSkill.getFollowingUserSkills()) {
            newFollowingUserSkills.add(userSkillRepository.findOne(followingUserSkill.getId()));
        }
        for (UserSkill previousUserSkill : userSkill.getPreviousUserSkills()) {
            newPreviousUserSkills.add(userSkillRepository.findOne(previousUserSkill.getId()));
        }
        for (SonarRule sonarRule : userSkill.getSonarRules()) {
            newSonarRules.add(sonarRuleRepository.findOne(sonarRule.getId()));
        }
        userSkill.setFollowingUserSkills(newFollowingUserSkills);
        userSkill.setPreviousUserSkills(newPreviousUserSkills);
        userSkill.setSonarRules(newSonarRules);
        userSkill.setUserSkillGroup(userSkillGroupRepository.findOne(groupID));

        for (UserSkill followingUserSkill : userSkill.getFollowingUserSkills()) {
            followingUserSkill.addPreviousUserSkill(userSkill);
        }
        for (UserSkill previousUserSkill : userSkill.getPreviousUserSkills()) {
            previousUserSkill.addFollowingUserSkill(userSkill);
        }
        for (SonarRule sonarRule : userSkill.getSonarRules()) {
            sonarRule.setUserSkill(userSkill);
        }
        userSkill = userSkillRepository.save(userSkill);
        LOGGER.info("Creating new userskill '{}'", userSkill.getName());

        List<SkillTreeUser> users = skillTreeUserService.findAll();
        for (SkillTreeUser user : users) {
            UserSkillToSkillTreeUser userSkillToSkillTreeUser = new UserSkillToSkillTreeUser();
            userSkillToSkillTreeUser.setLearnedOn(null);
            userSkillToSkillTreeUser.setRepeats(0);
            userSkillToSkillTreeUser.setScore(null);
            userSkillToSkillTreeUser.setSkillTreeUser(user);
            userSkillToSkillTreeUser.setUserSkill(userSkill);
            userSkillToSkillTreeUserRepository.save(userSkillToSkillTreeUser);
            user.addUserSkillToSkillTreeUser(userSkillToSkillTreeUser);
            skillTreeUserService.save(user);
        }
        User user = userRepository.findByUsername(principal.getName());
        if (user != null) {
            webSocketController.onCreateUserSkill(newUserSkill, user);
        }
        this.recalculateWholeUserSkillScore();
        return userSkill;
    }

    protected Double calculateUserSkillScore(UserSkill userSkill, SkillTreeUser skillTreeUser) {
        double score = 0;
        double distanzFollowing = 0;
        double distanzPrevious = 0;
        UserSkillToSkillTreeUser userSkillToSkillTreeUser = findUserSkillToSkillTreeUserByUserSkillAndUser(userSkill, skillTreeUser);
        if (userSkillToSkillTreeUser != null && userSkillToSkillTreeUser.getRepeats() >= 1) {
            return score;
        }
        UserSkillToSkillTreeUser userSkillToSkillTreeUserFollwing = null;
        getDistanzFollowing(userSkill.getFollowingUserSkills(), skillTreeUser);
        for (Map<String, Object> resultFollowing : resultFollowingCalculateScore) {
            if (resultFollowing.get(MAPKEY_USERSKILLTOSKILLTREEUSER) != null) {
                if ((int) resultFollowing.get(MAPKEY_RECURSIONCOUNT) == 0) {
                    distanzFollowing = 0;
                    userSkillToSkillTreeUserFollwing = (UserSkillToSkillTreeUser) resultFollowing.get(MAPKEY_USERSKILLTOSKILLTREEUSER);
                    break;
                } else if (distanzFollowing == 0 || distanzFollowing > (int) resultFollowing.get(MAPKEY_RECURSIONCOUNT)) {
                    distanzFollowing = (int) resultFollowing.get(MAPKEY_RECURSIONCOUNT);
                    userSkillToSkillTreeUserFollwing = (UserSkillToSkillTreeUser) resultFollowing.get(MAPKEY_USERSKILLTOSKILLTREEUSER);
                }
            }
        }

        UserSkillToSkillTreeUser userSkillToSkillTreeUserPrevious = null;
        getDistanzPrevious(userSkill.getPreviousUserSkills(), skillTreeUser);
        for (Map<String, Object> resultPrevious : resultPreviousCalculateScore) {
            if (resultPrevious.get(MAPKEY_USERSKILLTOSKILLTREEUSER) != null) {
                if ((int) resultPrevious.get(MAPKEY_RECURSIONCOUNT) == 0) {
                    distanzPrevious = 0;
                    userSkillToSkillTreeUserPrevious = (UserSkillToSkillTreeUser) resultPrevious.get(MAPKEY_USERSKILLTOSKILLTREEUSER);
                    break;
                } else if (distanzPrevious == 0 || distanzPrevious > (int) resultPrevious.get(MAPKEY_RECURSIONCOUNT)) {
                    distanzPrevious = (int) resultPrevious.get(MAPKEY_RECURSIONCOUNT);
                    userSkillToSkillTreeUserPrevious = (UserSkillToSkillTreeUser) resultPrevious.get(MAPKEY_USERSKILLTOSKILLTREEUSER);
                }
            }
        }
        resultFollowingCalculateScore.clear();
        resultPreviousCalculateScore.clear();

        return calculateScore(userSkillToSkillTreeUserFollwing, userSkillToSkillTreeUserPrevious, distanzFollowing,
                distanzPrevious);
    }

    private Double calculateScore(UserSkillToSkillTreeUser userSkillToSkillTreeUserFollwing,
                                  UserSkillToSkillTreeUser userSkillToSkillTreeUserPrevious, double distanzFollowing,
                                  double distanzPrevious) {
        if (userSkillToSkillTreeUserFollwing != null && userSkillToSkillTreeUserPrevious == null) {

            return distanzFollowing / (Double.valueOf(userSkillToSkillTreeUserFollwing.getRepeats())
                    / Double.valueOf(userSkillToSkillTreeUserFollwing.getUserSkill().getRequiredRepetitions()));

        } else if (userSkillToSkillTreeUserFollwing == null && userSkillToSkillTreeUserPrevious != null) {
            return distanzPrevious / (Double.valueOf(userSkillToSkillTreeUserPrevious.getRepeats())
                    / Double.valueOf(userSkillToSkillTreeUserPrevious.getUserSkill().getRequiredRepetitions()));
        } else if (userSkillToSkillTreeUserFollwing != null && userSkillToSkillTreeUserPrevious != null) {
            if (distanzFollowing > distanzPrevious) {
                return distanzPrevious / (Double.valueOf(userSkillToSkillTreeUserPrevious.getRepeats())
                        / Double.valueOf(userSkillToSkillTreeUserPrevious.getUserSkill().getRequiredRepetitions()));
            } else if (distanzFollowing < distanzPrevious) {
                return distanzFollowing / (Double.valueOf(userSkillToSkillTreeUserFollwing.getRepeats())
                        / Double.valueOf(userSkillToSkillTreeUserFollwing.getUserSkill().getRequiredRepetitions()));
            } else {
                double weightFollowing = Double.valueOf(userSkillToSkillTreeUserFollwing.getRepeats())
                        / Double.valueOf(userSkillToSkillTreeUserFollwing.getUserSkill().getRequiredRepetitions());
                double weightPrevious = Double.valueOf(userSkillToSkillTreeUserPrevious.getRepeats())
                        / Double.valueOf(userSkillToSkillTreeUserPrevious.getUserSkill().getRequiredRepetitions());
                if (weightFollowing > weightPrevious) {
                    return distanzFollowing / (Double.valueOf(userSkillToSkillTreeUserFollwing.getRepeats())
                            / Double.valueOf(userSkillToSkillTreeUserFollwing.getUserSkill().getRequiredRepetitions()));
                } else {
                    return distanzPrevious / (Double.valueOf(userSkillToSkillTreeUserPrevious.getRepeats())
                            / Double.valueOf(userSkillToSkillTreeUserPrevious.getUserSkill().getRequiredRepetitions()));
                }
            }
        }
        return null;
    }

    private void getDistanzFollowing(List<UserSkill> userSkills, SkillTreeUser skillTreeUser) {
        recursionCount++;
        Map<String, Object> result;
        for (UserSkill userSkill : userSkills) {
            // Necessary due to the recursion. It may be that the first recursion count ++
            // is not triggered
            if (recursionCount == 0) {
                recursionCount++;
            }
            UserSkillToSkillTreeUser userSkillToSkillTreeUser = findUserSkillToSkillTreeUserByUserSkillAndUser(
                    userSkill, skillTreeUser);
            if (userSkillToSkillTreeUser != null && userSkillToSkillTreeUser.getRepeats() >= 1) {
                result = new HashMap<>();
                result.put(MAPKEY_USERSKILLTOSKILLTREEUSER, userSkillToSkillTreeUser);
                result.put(MAPKEY_RECURSIONCOUNT, recursionCount);
                recursionCount = 0;
                resultFollowingCalculateScore.add(result);
            }

            getDistanzFollowing(userSkill.getFollowingUserSkills(), skillTreeUser);

        }
        result = new HashMap<>();
        result.put(MAPKEY_USERSKILLTOSKILLTREEUSER, null);
        result.put(MAPKEY_RECURSIONCOUNT, 0);
        recursionCount = 0;
        resultFollowingCalculateScore.add(result);
    }

    private void getDistanzPrevious(List<UserSkill> userSkills, SkillTreeUser skillTreeUser) {
        recursionCount++;
        Map<String, Object> result;
        for (UserSkill userSkill : userSkills) {
            // Necessary due to the recursion. It may be that the first recursion count ++
            // is not triggered
            if (recursionCount == 0) {
                recursionCount++;
            }
            UserSkillToSkillTreeUser userSkillToSkillTreeUser = findUserSkillToSkillTreeUserByUserSkillAndUser(
                    userSkill, skillTreeUser);

            if (userSkillToSkillTreeUser != null && userSkillToSkillTreeUser.getRepeats() >= 1) {
                result = new HashMap<>();
                result.put(MAPKEY_USERSKILLTOSKILLTREEUSER, userSkillToSkillTreeUser);
                result.put(MAPKEY_RECURSIONCOUNT, recursionCount);
                recursionCount = 0;
                resultPreviousCalculateScore.add(result);
            }
            getDistanzPrevious(userSkill.getPreviousUserSkills(), skillTreeUser);

        }

        result = new HashMap<>();
        result.put(MAPKEY_USERSKILLTOSKILLTREEUSER, null);
        result.put(MAPKEY_RECURSIONCOUNT, 0);
        recursionCount = 0;
        resultPreviousCalculateScore.add(result);
    }

    @Transactional
    public UserSkill updateUserSkill(UserSkill userSkill) {
        UserSkill oldUserSkill = userSkillRepository.findOne(userSkill.getId());
        if (oldUserSkill.getRequiredRepetitions() != userSkill.getRequiredRepetitions()) {
            List<UserSkillToSkillTreeUser> userSkillToSkillTreeUsers = userSkillToSkillTreeUserRepository
                    .findUserSkillToSkillTreeUsersByUserSkill(userSkill);
            for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : userSkillToSkillTreeUsers) {
                if (userSkillToSkillTreeUser.getRepeats() >= userSkill.getRequiredRepetitions() && userSkillToSkillTreeUser.getLearnedOn() == null) {
                    userSkillToSkillTreeUser.setLearnedOn(new Timestamp(System.currentTimeMillis()));
                    userSkillToSkillTreeUserRepository.save(userSkillToSkillTreeUser);
                    LOGGER.info("The user with the mail {} has learned the Skill {}, because the required skill repetitions have changed", userSkillToSkillTreeUser.getSkillTreeUser().getMail(), userSkill.getName());
                } else if (userSkillToSkillTreeUser.getRepeats() < userSkill.getRequiredRepetitions()) {
                    userSkillToSkillTreeUser.setLearnedOn(null);
                    userSkillToSkillTreeUserRepository.save(userSkillToSkillTreeUser);
                    LOGGER.info("The user with the mail {} did not learn the skill {} because the required skill retries have changed", userSkillToSkillTreeUser.getSkillTreeUser().getMail(), userSkill.getName());
                }
            }
            oldUserSkill.setRequiredRepetitions(userSkill.getRequiredRepetitions());
            userSkill = oldUserSkill;

        }
        if (userSkillRepository.save(userSkill) != null) {
            recalculateWholeUserSkillScore();
            LOGGER.info("UserSkill with ID: {} has been updated", userSkill.getId());
            return userSkill;
        }
        return null;
    }

    protected void recalculateWholeUserSkillScore() {
        List<UserSkillToSkillTreeUser> userSkillToSkillTreeUsers = userSkillToSkillTreeUserRepository.findAll();
        for (UserSkillToSkillTreeUser skillToSkillTreeUser : userSkillToSkillTreeUsers) {
            skillToSkillTreeUser.setScore(calculateUserSkillScore(skillToSkillTreeUser.getUserSkill(),
                    skillToSkillTreeUser.getSkillTreeUser()));
            userSkillToSkillTreeUserRepository.save(skillToSkillTreeUser);
        }
    }

    public SkillTreeUser learnUserSkill(String mail, String key) {
        SkillTreeUser user = skillTreeUserService.findByMail(mail);
        UserSkill learnedUserSkill = null;
        if (user == null) {
            LOGGER.info("SkillTreeUser with mail: {}  does not exist yet - creating it...", mail);
            user = skillTreeUserService.createSkillTreeUser(mail);
        }

        for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : user.getUserSkillToSkillTreeUser()) {
            boolean breakFlag = false;
            for (SonarRule rule : userSkillToSkillTreeUser.getUserSkill().getSonarRules()) {
                if (rule.getKey().equals(key)) {
                    userSkillToSkillTreeUser.setRepeats(userSkillToSkillTreeUser.getRepeats() + 1);
                    if (userSkillToSkillTreeUser.getRepeats() >= userSkillToSkillTreeUser.getUserSkill()
                            .getRequiredRepetitions() && userSkillToSkillTreeUser.getLearnedOn() == null) {
                        userSkillToSkillTreeUser.setLearnedOn(new Timestamp(System.currentTimeMillis()));
                        LOGGER.info("SkillTreeUser with mail: {} has learned UserSkill '{}'", mail,
                                userSkillToSkillTreeUser.getUserSkill().getName());
                        learnedUserSkill = userSkillToSkillTreeUser.getUserSkill();
                        webSocketController.onLearnUserSkill(learnedUserSkill,
                                userRepository.findByMail(user.getMail()));

                    }
                    breakFlag = true;
                }
            }
            if(breakFlag){
                break ;
            }
        }
        updateSkillTreeScoring(user);
        User sqUser = userRepository.findByMail(mail);
        if (sqUser != null) {
            for (World world : sqUser.getWorlds()) {
                standardTaskService.updateGetByWorldCache(world);
                standardTaskService.updateGetFreeByWorldCache(world);

            }
        }
        skillTreeUserService.save(user);
        return user;
    }

    protected void updateSkillTreeScoring(SkillTreeUser skillTreeUser) {
        for (UserSkillToSkillTreeUser userSkillToSkillTreeUser : skillTreeUser.getUserSkillToSkillTreeUser()) {
            userSkillToSkillTreeUser
                    .setScore(calculateUserSkillScore(userSkillToSkillTreeUser.getUserSkill(), skillTreeUser));
            userSkillToSkillTreeUserRepository.save(userSkillToSkillTreeUser);
        }
    }

    public void learnUserSkillFromTask(StandardTask task) {
        final Participation participation = task.getParticipation();
        if (participation != null) {
            String mail = participation.getUser().getMail();
            if (mail != null) {
                learnUserSkill(mail, task.getIssueRule());
            }
        } else {
            LOGGER.info("No SQUser participations found for task {}, so no skills are learned", task.getKey());
        }
    }

}
