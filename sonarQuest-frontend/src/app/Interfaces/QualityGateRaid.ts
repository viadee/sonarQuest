import { Condition } from './Condition';
import { BaseRaid } from './BaseRaid';
import { HighScore } from './HighScore';
import { SonarQubeProjectStatusType } from 'app/Enums/SonarQubeProjectStatusType';

export interface QualityGateRaid extends BaseRaid, HighScore {
    sonarQubeStatus: SonarQubeProjectStatusType,
    conditions: Condition[]
}