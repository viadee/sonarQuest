package com.viadee.sonarquest.skillTree;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.repositories.SonarRuleRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillRepositroy;

@Service
public class UserSkillService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSkillService.class);

    
    @Autowired
    private UserSkillRepositroy userSkillRepositroy;
    
    @Autowired
    private SonarRuleRepository sonarRuleRepository;
    
    private UserSkill findById(final Long id) {
        return userSkillRepositroy.findOne(id);
    }
    
    
    @Transactional
    public UserSkill createUserSkill(final UserSkill userSkill) {
        LOGGER.info("Creating new userskill " + userSkill.getName());
        SonarRule rule = new SonarRule("testname","testkey");
       sonarRuleRepository.save(rule);
        userSkill.addSonarRule(rule);
       
        return userSkillRepositroy.save(userSkill);
    }
    
    
    public boolean delete(final Long userId) {
        final UserSkill skill = findById(userId);
        if(skill != null) {
            userSkillRepositroy.delete(skill);
            return true;
        }
        
        return false;
    }
      
}
