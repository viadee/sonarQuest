import { ProgressDTO } from './../Interfaces/ProgressDTO';
import { World } from './../Interfaces/World';
import { Raid } from './../Interfaces/Raid';
import { AdventureState } from 'app/Interfaces/AdventureState';
import { Quest } from 'app/Interfaces/Quest';
import { Monster } from 'app/Interfaces/Monster';
import { Task } from 'app/Interfaces/Task';
import { RaidLeaderboard } from 'app/Interfaces/RaidLeaderboard';

export class RaidModel implements Raid {
    raidLeaderboardList: RaidLeaderboard[];
    monster: Monster;
    tasks: Task[];
    id: number;
    title: string;
    status: AdventureState;
    visible: boolean;
    gold: number;
    xp: number;
    quests: Quest[];
    raidLeaderboadList: RaidLeaderboard[];
    monsterImage: string;
    monsterName: string;
    users: any;
    startdate: Date;
    enddate: Date;
    world: World;
    raidProgress: ProgressDTO;

    constructor(title: string, monsterName: string, monsterImage: string, gold: number, xp: number, quests: Quest[], world: World) {
        this.title = title;
        this.monsterImage = monsterImage;
        this.monsterName = monsterName;
        this.gold = gold;
        this.xp = xp;
        this.quests = quests;
        this.world = world;
        if (quests == null){
            this.quests = new Array<Quest>();
        }
    }
}