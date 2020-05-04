import { Quest } from './../Interfaces/Quest';
import { QuestState } from 'app/Interfaces/QuestState';
import { Adventure } from 'app/Interfaces/Adventure';
import { World } from 'app/Interfaces/World';
import { Task } from 'app/Interfaces/Task';
import { Participation } from 'app/Interfaces/Participation';
import { Raid } from 'app/Interfaces/Raid';

export class QuestModel implements Quest {
   
    private _id: number;
    private _title: string;
    private _story: string;
    private _creatorName: string;
    private _status: QuestState;
    private _gold: number;
    private _xp: number;
    private _image: string;
    private _visible: boolean;
    private _startdate: Date;
    private _enddate: Date;
    private _world: World;
    private _adventure: Adventure;
    private _tasks: Task[];
    private _participations: Participation[];
    private _participants: string[];
    private _raid: Raid;
    private _questProgress: number;

    constructor(id: number, title: string, story: string, creatorName: string, status: QuestState, gold: number,
        xp: number, image: string, visible: boolean, startdate: Date, enddate: Date,
        world: World, adventure: Adventure, tasks: Task[], participations: Participation[]) {
        this._id = id;
        this._title = title;
        this._story = story;
        this._creatorName = creatorName;
        this._status = status;
        this._gold = gold;
        this._xp = xp;
        this._image = image;
        this._visible = visible;
        this._startdate = startdate;
        this._enddate = enddate;
        this._world = world;
        this._adventure = adventure;
        this._tasks = tasks;
        this._participations = participations;
    }

    public get story(): string {
        return this._story;
    }
    public set story(value: string) {
        this._story = value;
    }

    public get creatorName(): string {
        return this._creatorName;
    }
    public set creatorName(value: string) {
        this._creatorName = value;
    }
    
    public get status(): QuestState {
        return this._status;
    }
    public set status(value: QuestState) {
        this._status = value;
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
    
    public get image(): string {
        return this._image;
    }
    public set image(value: string) {
        this._image = value;
    }
    
    public get visible(): boolean {
        return this._visible;
    }
    public set visible(value: boolean) {
        this._visible = value;
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
    
    public get world(): World {
        return this._world;
    }
    public set world(value: World) {
        this._world = value;
    }
    
    public get adventure(): Adventure {
        return this._adventure;
    }
    public set adventure(value: Adventure) {
        this._adventure = value;
    }
    
    public get tasks(): Task[] {
        return this._tasks;
    }
    public set tasks(value: Task[]) {
        this._tasks = value;
    }
    
    public get participations(): Participation[] {
        return this._participations;
    }
    public set participations(value: Participation[]) {
        this._participations = value;
    }
    
    public get participants(): string[] {
        return this._participants;
    }
    public set participants(value: string[]) {
        this._participants = value;
    }

    public get id(): number {
        return this._id;
    }
    public set id(value: number) {
        this._id = value;
    }

    public get title(): string {
        return this._title;
    }
    public set title(value: string) {
        this._title = value;
    }

    public get raid(): Raid {
        return this._raid;
    }
    public set raid(value: Raid) {
        this._raid = value;
    }

    public get questProgress(): number {
        return this._questProgress;
    }
    public set questProgress(value: number) {
        this._questProgress = value;
    }
}