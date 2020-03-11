import { Task } from './../Interfaces/Task';
import { World } from 'app/Interfaces/World';

export class TaskModel implements Task {
    participant: string;
    id: number;
    title: string;
    status: string;
    gold: number;
    xp: number;
    component: string;
    severity: string;
    type: string;
    debt: number;
    message: string;
    world: World;
    issueKey: string;
    key: string;
    startdate: Date;
    enddate: Date;
}