package com.viadee.sonarquest.controllers.login;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerIntegrationTest {

    @Autowired
    private LoginController controller;

    @Test
    public void testInfo() {
	String info = controller.info();
	assertEquals("Dies ist eine Login Seite", info);
    }

}
