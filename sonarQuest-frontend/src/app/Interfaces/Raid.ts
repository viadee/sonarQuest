import { Task } from 'app/Interfaces/Task';
import { BaseRaid } from './BaseRaid';
import { ProgressDTO } from './ProgressDTO';
import { Quest } from './Quest';
import { RaidLeaderboard } from './RaidLeaderboard';
import { Monster } from './Monster';
//TODO rename in RAIDDTO
export interface Raid extends BaseRaid {
    status: any,
    quests: Quest[],
    raidLeaderboardList: RaidLeaderboard[];
    enddate: Date,
    tasks: Task[],
    raidProgress: ProgressDTO;
    monster: Monster;
}
