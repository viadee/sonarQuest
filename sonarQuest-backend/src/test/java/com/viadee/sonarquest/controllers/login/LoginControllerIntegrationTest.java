package com.viadee.sonarquest.controllers.login;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
