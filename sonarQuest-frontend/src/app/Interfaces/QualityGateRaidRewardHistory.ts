import { Reward } from './Reward';

export interface QualityGateRaidRewardHistory extends Reward {
    id: number,
    statusDate: Date,
    sonarQubeStatus: any
  }
