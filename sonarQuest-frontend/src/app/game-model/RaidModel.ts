import { ProgressDTO } from './../Interfaces/ProgressDTO';
import { World } from './../Interfaces/World';
import { Raid } from './../Interfaces/Raid';
import { AdventureState } from 'app/Interfaces/AdventureState';
import { Quest } from 'app/Interfaces/Quest';
import { Monster } from 'app/Interfaces/Monster';

export class RaidModel implements Raid {
    id: number;
    title: string;
    description: string;
    status: AdventureState;
    visible: boolean;
    gold: number;
    xp: number;
    goldLoss: number;
    xpLoss: number;
    quests: Quest[];
    monsterImage: string;
    monsterName: string;
    users: any;
    startdate: Date;
    enddate: Date;
    img: string;
    world: World;
    monster: Monster;
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

    /*
    public get id(): number {
        return this._id;
    }
    public set id(value: number) {
        this._id = value;
    }
    public getTitle(): string {
        return this.title;
    }
    public setTitle(value: string) {
        this.title = value;
    }
    public get story(): string {
        return this._story;
    }
    public set story(value: string) {
        this._story = value;
    }
    public get status(): AdventureState {
        return this._status;
    }
    public set status(value: AdventureState) {
        this._status = value;
    }
    public get visible(): boolean {
        return this._visible;
    }
    public set visible(value: boolean) {
        this._visible = value;
    }
    public get gold(): number {
        return this._gold;
    }
    public set gold(value: number) {
        this._gold = value;
    }
    public get xp(): number {
        return this._xp;
    }
    public set xp(value: number) {
        this._xp = value;
    }
    public get quests(): any {
        return this._quests;
    }
    public set quests(value: any) {
        this._quests = value;
    }
    public get users(): any {
        return this._users;
    }
    public set users(value: any) {
        this._users = value;
    }
    public get startdate(): Date {
        return this._startdate;
    }
    public set startdate(value: Date) {
        this._startdate = value;
    }
    public get enddate(): Date {
        return this._enddate;
    }
    public set enddate(value: Date) {
        this._enddate = value;
    }
    public get img(): string {
        return this._img;
    }
    public set img(value: string) {
        this._img = value;
    }
    */
}