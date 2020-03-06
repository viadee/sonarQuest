package com.viadee.sonarquest.controllers.login;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LoginControllerIntegrationTest {

    @Autowired
    private LoginController controller;

    @Test
    public void testInfo() {
	final String info = controller.info();
	assertEquals("Dies ist eine Login Seite", info);
    }

}
