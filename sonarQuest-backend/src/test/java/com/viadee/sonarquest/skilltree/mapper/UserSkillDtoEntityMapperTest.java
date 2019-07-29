package com.viadee.sonarquest.skilltree.mapper;

import com.viadee.sonarquest.skilltree.dto.UserSkillDTO;
import com.viadee.sonarquest.skilltree.entities.SonarRule;
import com.viadee.sonarquest.skilltree.entities.UserSkill;
import com.viadee.sonarquest.skilltree.utils.mapper.UserSkillDtoEntityMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class UserSkillDtoEntityMapperTest {

    @InjectMocks
    private UserSkillDtoEntityMapper mapper;

    @Test
    public void testEntityToDto(){
        /*
        Given
         */
        //Mock UserSkill to convert into DTO
        UserSkill userSkill = new UserSkill();

        userSkill.setId(1L);
        userSkill.setName("Test UserSkill");

        SonarRule rule1 = new SonarRule();
        rule1.setId(1L);
        rule1.setName("Rule 1");
        rule1.setKey("Test:Key1");
        rule1.setUserSkill(userSkill);

        SonarRule rule2 = new SonarRule();
        rule2.setId(2L);
        rule2.setName("Rule 2");
        rule2.setKey("Test:Key2");
        rule2.setUserSkill(userSkill);

        userSkill.addSonarRule(rule1);
        userSkill.addSonarRule(rule2);
        /*
        When
         */

        UserSkillDTO dto = mapper.entityToDto(userSkill);

        /*
        Then
         */

        assertNotNull(dto);
        assertEquals(userSkill.getId(),dto.getId());
        assertEquals(userSkill.getSonarRules().get(0).getKey(),dto.getRuleKey().get(0));
        assertEquals(userSkill.getSonarRules().get(1).getKey(),dto.getRuleKey().get(1));

    }

}
