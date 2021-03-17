package com.viadee.sonarquest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StandardTaskEvaluationServiceTest {

    private StandardTaskEvaluationService service;

    @BeforeEach
    public void before(){
        service = new StandardTaskEvaluationService();
    }

    @Test
    void evaluateGoldAmount() {
        String debtString = "15"; //15 min will be rounded up to 2 Gold
        Long goldAmount = service.evaluateGoldAmount(debtString);
        assertEquals(2, goldAmount);
    }
}