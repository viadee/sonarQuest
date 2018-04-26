package com.viadee.sonarQuest.services;

import org.springframework.stereotype.Service;

@Service
public class StandardTaskEvaluationService {


    public Long evaluateXP(String severity) {
        Long xp;
        switch (severity) {
            case "BLOCKER":
                xp = 10L;
                break;
            case "CRITICAL":
                xp = 7L;
                break;
            case "MAJOR":
                xp = 5L;
                break;
            case "MINOR":
                xp = 2L;
                break;
            default:
                xp = 1L;
                break;
        }
        return xp;
    }

    public Long evaluateGoldAmount(String debtString) {
        Long debt = getDebt(debtString);
        return roundUp(debt,10);
    }

    public Long getDebt(String debtString) {
        debtString = debtString.replaceAll("[^0-9]", "");
        return (long) Integer.parseInt(debtString);
    }

    private static long roundUp(long num, long divisor) {
        return (num + divisor - 1) / divisor;
    }

}
