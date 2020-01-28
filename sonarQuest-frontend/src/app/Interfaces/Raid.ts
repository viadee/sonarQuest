import { ProgressDTO } from './ProgressDTO';
import { Monster } from 'app/Interfaces/Monster';
import { World } from './World';
import { AdventureState } from './AdventureState';
import { Quest } from './Quest';

export interface Raid {
    id: number,
    title: string,
    description: string,
    status: AdventureState,
    visible: boolean,
    gold: number,
    xp: number,
    goldLoss: number,
    xpLoss: number,
    quests: Quest[],
    users: any,
    startdate: Date,
    enddate: Date,
    monsterImage: string,
    monsterName: string,
    world: World,
    raidProgress: ProgressDTO;
    monster: Monster;
}
