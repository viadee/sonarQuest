package com.viadee.sonarQuest.services;

import org.springframework.stereotype.Service;

@Service
public class StandardTaskEvaluationService {


    public Long evaluateXP(String severity) {
        Long xp = Long.valueOf(0);
        switch (severity) {
            case "BLOCKER":
                xp = Long.valueOf(10);
                break;
            case "CRITICAL":
                xp = Long.valueOf(7);
                break;
            case "MAJOR":
                xp = Long.valueOf(5);
                break;
            case "MINOR":
                xp = Long.valueOf(2);
                break;
            default:
                xp = Long.valueOf(1);
                break;
        }
        return xp;
    }

    public Long evaluateGoldAmount(String debtString) {
        Long debt = getDebt(debtString);
        Long goldAmount = roundUp(debt,10);
        return goldAmount;
    }

    public Long getDebt(String debtString) {
        debtString = debtString.replaceAll("[^0-9]", "");
        return Long.valueOf(Integer.parseInt(debtString));
    }

    private static long roundUp(long num, long divisor) {
        return (num + divisor - 1) / divisor;
    }

}
