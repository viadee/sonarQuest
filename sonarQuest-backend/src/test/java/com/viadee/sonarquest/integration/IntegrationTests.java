package com.viadee.sonarquest.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ SonarQuestApplicationIT.class, UserManagementIT.class })

public class IntegrationTests {

}
