package com.viadee.sonarquest.skillTree.mapper;

import com.viadee.sonarquest.skillTree.dto.SonarRuleDTO;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.utils.mapper.SonarRuleDtoEntityMapper;
import com.viadee.sonarquest.skillTree.utils.mapper.UserSkillDtoEntityMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SonarRuleDtoEntityMapperTest {

    @InjectMocks
    private SonarRuleDtoEntityMapper mapper;

    @Mock
    private UserSkillDtoEntityMapper userSkillMapper;

    @InjectMocks
    private UserSkillDtoEntityMapper usedUserSkillMapper;

    @Test
    public void testEntityToDto(){
        /*
        Given
         */
        //Mock SonarRule to convert into DTO
        SonarRule rule = new SonarRule();
        rule.setId(1L);
        rule.setKey("Test:Key1");
        rule.setName("Test Rule");
        rule.setAddedAt(getTimestamp());


        UserSkill userSkill = new UserSkill();
        userSkill.setId(1L);
        userSkill.setName("Test UserSkill");
        userSkill.addSonarRule(rule);

        rule.setUserSkill(userSkill);

        when(userSkillMapper.entityToDto(Matchers.any(UserSkill.class))).thenReturn(usedUserSkillMapper.entityToDto(userSkill));

        /*
        When
         */
        SonarRuleDTO dto = mapper.entityToDto(rule);
        /*
        Then
         */
        assertNotNull(dto);
        assertEquals(dto.getId(),rule.getId());
        assertEquals(dto.getUserSkilLDto().getId(),userSkill.getId());
    }

    /*
    HELPER
     */

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
}
