import {Quest} from './../Interfaces/Quest';
import {Task} from './../Interfaces/Task';
import {Injectable} from '@angular/core';
import {Response} from '@angular/http';

import {environment} from '../../environments/environment';
import {World} from '../Interfaces/World';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {StandardTaskService} from './standard-task.service';
import {SpecialTaskService} from './special-task.service';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class TaskService {

  constructor(public http: HttpClient,
              private standardTaskService: StandardTaskService,
              private specialTaskService: SpecialTaskService) {
  }

  public getTasksForQuest(quest: Quest): Promise<Task[]> {
    return this.http.get<Task[]>(`${environment.endpoint}/task/quest/${quest.id}`)
      .toPromise()
      .catch(this.handleError);
  }

  public getFreeTasksForWorld(world: World): Promise<Task[]> {
    return this.http.get<Task[]>(`${environment.endpoint}/task/getFreeForWorld/${world.id}`)
      .toPromise()
      .catch(this.handleError);
  }

  addToQuest(task: any, quest: any): Promise<Task> {
    return this.http.post<Task>(`${environment.endpoint}/task/${task.id}/addToQuest/${quest.id}`, null)
      .toPromise()
      .catch(this.handleError);
  }

  deleteFromQuest(task: any): Promise<any> {
    return this.http.delete(`${environment.endpoint}/task/${task.id}/deleteFromQuest`)
      .toPromise()
  }

  solveTaskManually(task: any): Promise<any> {
    return this.http.put(`${environment.endpoint}/task/${task.id}/solveManually`, task.id)
      .toPromise()
  }

  refreshTasks(world: World) {
    this.standardTaskService.getStandardTasksForWorld(world);
    this.specialTaskService.getSpecialTasksForWorld(world);
  }

  calculateGoldAmountOfTasks(tasks: Task[]): number {
    return tasks.map(task => task.gold).reduce(function (a, b) {
      return a + b;
    }, 0);
  }

  calculateXpAmountOfTasks(tasks: Task[]): number {
    return tasks.map(task => task.xp).reduce(function (a, b) {
      return a + b;
    }, 0);
  }

  identifyNewAndDeselectedTasks(oldTasks: Task[], currentTasks: Task[]): Array<Task[]> {
    const newTasks = this.taskDifference(currentTasks, oldTasks);
    const deselectedTasks = this.taskDifference(oldTasks, currentTasks)
    return [newTasks, deselectedTasks];
  }

  addParticipation(task: Task, quest: Quest): Promise<Task> {
    return this.http.post<Task>(`${environment.endpoint}/task/${task.id}/addParticipation/${quest.id}`, null)
      .toPromise()
      .catch(this.handleError);
  }

  removeParticipation(task: any): Promise<any> {
    return this.http.delete(`${environment.endpoint}/task/${task.id}/deleteParticipation`)
      .toPromise()
  }

  private taskDifference(array1: Array<Task>, array2: Array<Task>) {
    return array1.filter(x => !array2.includes(x));
  }

  private handleError(error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Promise.reject(errMsg);
  }


}
