import { Reward } from './Reward';
import { Condition } from './Condition';
import { BaseRaid } from './BaseRaid';
import { HighScore } from './HighScore';

export interface QualityGateRaid extends BaseRaid, HighScore {
    sonarQubeStatus: any,
    conditions: Condition[]
}