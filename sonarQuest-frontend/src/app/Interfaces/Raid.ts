import { BaseRaid } from './BaseRaid';
import { ProgressDTO } from './ProgressDTO';
import { Quest } from './Quest';
import { RaidLeaderboard } from './RaidLeaderboard';


export interface Raid extends BaseRaid {
    status: any,
    quests: Quest[],
    raidLeaderboardList: RaidLeaderboard[];
    enddate: Date,
    raidProgress: ProgressDTO;
}
