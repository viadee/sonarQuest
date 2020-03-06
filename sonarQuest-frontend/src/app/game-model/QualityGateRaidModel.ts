import { QualityGateRaid } from './../Interfaces/QualityGateRaid';
import { Condition } from 'app/Interfaces/Condition';
import { HighScore } from 'app/Interfaces/HighScore';
import { World } from 'app/Interfaces/World';
export class QualityGateRaidModel implements QualityGateRaid {
    sonarQubeStatus: any;
    conditions: Condition[];
    highScore: HighScore;
    scoreDay: Date;
    scorePoints: number;
    id: number;
    title: string;
    visible: boolean;
    gold: number;
    xp: number;
    monsterImage: string;
    monsterName: string;
    world: World;

    constructor(world: World) {
        this.world = world;
    }
}