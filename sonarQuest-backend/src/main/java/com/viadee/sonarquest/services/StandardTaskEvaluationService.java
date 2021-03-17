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
        
        SeverityWithXp(int xp) {
            this.xp = (long) xp;
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

    public long evaluateXP(String sev) {
        SeverityWithXp severity = SeverityWithXp.fromString(sev);
        return severity.xp;
    }

    public long evaluateGoldAmount(String debtString) {
        long debt = getDebt(debtString);
        return (debt + 9) / 10;
    }

    public long getDebt(String debtString) {
        return debtString == null ? 0 : Integer.parseInt(debtString.replaceAll("[^0-9]", ""));
    }

}
