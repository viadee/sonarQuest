import { Reward } from './Reward';
import { World } from './World';

export interface BaseRaid extends Reward {
    id: number,
    title: string,
    visible: boolean,
    gold: number,
    xp: number,
    monsterImage: string,
    monsterName: string,
    world: World
}