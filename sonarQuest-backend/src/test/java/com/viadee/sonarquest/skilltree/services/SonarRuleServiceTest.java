package com.viadee.sonarquest.skilltree.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.skilltree.entities.SonarRule;
import com.viadee.sonarquest.skilltree.repositories.SonarRuleRepository;

@RunWith(MockitoJUnitRunner.class)
public class SonarRuleServiceTest {

	@Mock
	SonarRuleRepository sonarRuleRepository;

	@InjectMocks
	private SonarRuleService sonarRuleService;

	private List<SonarRule> rules;

	@Before
	public void init() {
		// Mock SonarRules
		rules = new ArrayList<SonarRule>();
		SonarRule rule1 = new SonarRule();
		SonarRule rule2 = new SonarRule();
		SonarRule rule3 = new SonarRule();

		rule1.setId(1L);
		rule1.setAddedAt(getTimestamp("01/01/2000"));
		rule1.setKey("test:key1");
		rule1.setName("TestRule1");

		rule2.setId(2L);
		rule2.setAddedAt(getTimestamp("02/01/2000"));
		rule2.setKey("test:key2");
		rule2.setName("TestRule2");

		rule3.setId(3L);
		rule3.setAddedAt(getTimestamp("03/01/2000"));
		rule3.setKey("test:key2");
		rule3.setName("TestRule2");

		rules.add(rule1);
		rules.add(rule2);
		rules.add(rule3);
	}

	@Test
	public void testGetLastAddedDate_RulesFound() {
		/*
		 * Given
		 */

		when(sonarRuleRepository.findAll()).thenReturn(rules);

		/*
		 * When
		 */
		String dateString = sonarRuleService.getLastAddedDate();

		/*
		 * Then
		 */
		assertEquals("2000-01-03", dateString);

	}

	@Test
	public void testGetLastAddedDate_NoRulesFound() {
		/*
		 * Given
		 */
		rules.clear();
		when(sonarRuleRepository.findAll()).thenReturn(rules);

		/*
		 * When
		 */
		String dateString = sonarRuleService.getLastAddedDate();

		/*
		 * Then
		 */
		assertEquals(dateString, sonarRuleService.getLastRuleUpdateFromProperty());

	}

	/*
	 * HELPER
	 */
	private Timestamp getTimestamp(String dateString) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time = date.getTime();
		return new Timestamp(time);
	}
}
