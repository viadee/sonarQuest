package com.viadee.sonarquest.skilltree.mapper;

import com.viadee.sonarquest.skilltree.dto.SonarRuleDTO;
import com.viadee.sonarquest.skilltree.dto.UserSkillDTO;
import com.viadee.sonarquest.skilltree.entities.SonarRule;
import com.viadee.sonarquest.skilltree.entities.UserSkill;
import com.viadee.sonarquest.skilltree.entities.UserSkillGroup;
import com.viadee.sonarquest.skilltree.utils.mapper.SonarRuleDtoEntityMapper;
import com.viadee.sonarquest.skilltree.utils.mapper.UserSkillDtoEntityMapper;
import com.viadee.sonarquest.skilltree.utils.mapper.UserSkillGroupDtoEntitiyMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Mock
    private UserSkillGroupDtoEntitiyMapper userSkillGroupDtoEntitiyMapper;

    @Mock
    private SonarRuleDtoEntityMapper sonarRuleDtoEntityMapper;

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
        UserSkillGroup group = new UserSkillGroup();
        group.setId(1L);
        group.setName("Test UserSkillGroup");
        userSkill.setUserSkillGroup(group);

        rule.setUserSkill(userSkill);
        UserSkillDTO finalUserSkillDto = usedUserSkillMapper.entityToDto(userSkill);

        when(userSkillMapper.entityToDto(Matchers.any(UserSkill.class))).thenReturn(finalUserSkillDto);

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
