package com.viadee.sonarquest.services;

import org.springframework.stereotype.Service;

@Service
public class StandardTaskEvaluationService {
    
    private enum SeverityWithXp {
        BLOCKER(10),
        CRITICAL(7),
        MAJOR(5),
        MINOR(2),
        DEFAULT(1);
        
        Long xp;
        
        private SeverityWithXp(int xp) {
            this.xp = Long.valueOf(xp);
        }
        
        static SeverityWithXp fromString(String severityName) {
            for (SeverityWithXp sev : values()) {
                if (sev.toString().equals(severityName)) {
                    return sev;
                }
            }
            return DEFAULT;
        }
    }

    public Long evaluateXP(String sev) {
        SeverityWithXp severity = SeverityWithXp.fromString(sev);
        return severity.xp;
    }

    public Long evaluateGoldAmount(String debtString) {
        Long debt = getDebt(debtString);
        return roundUp(debt, 10);
    }

    public Long getDebt(String debtString) {
        Long debt = 0L;
        if (debtString != null) {
            debtString = debtString.replaceAll("[^0-9]", "");
            debt = (long) Integer.parseInt(debtString);
        }
        return debt;
    }

    private static long roundUp(long num, long divisor) {
        return (num + divisor - 1) / divisor;
    }

}
