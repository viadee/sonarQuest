import { Quest } from './../Interfaces/Quest';
import { Developer } from './../Interfaces/Developer.d';
import { Task } from './../Interfaces/Task';
import { Injectable } from '@angular/core';
import { Http, RequestOptions, Response, Headers } from "@angular/http";

import { environment } from "../../environments/environment";
import { World } from "../Interfaces/World";
import { Observable } from 'rxjs/Observable';
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch";
import { StandardTaskService } from "./standard-task.service";
import { SpecialTaskService } from "./special-task.service";

@Injectable()
export class TaskService {


  constructor(public http: Http,
    private standardTaskService: StandardTaskService,
    private specialTaskService: SpecialTaskService, ) { }

 /*  getTasks(): Observable<any> {
    return this.http.get(`${environment.endpoint}/task`)
      .map(this.extractData).catch(this.handleError)

  } */

  getFreeTasksForWorld(world: World): Promise<any> {
    return this.http.get(`${environment.endpoint}/task/getFreeForWorld/${world.id}`)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  addToQuest(task: any, quest: any): Promise<any> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/task/${task.id}/addToQuest/${quest.id}`, null, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  deleteFromQuest(task: any): Promise<any> {
    return this.http.delete(`${environment.endpoint}/task/${task.id}/deleteFromQuest`)
      .toPromise()
  }



  updateTask(task: any): Promise<any> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.put(`${environment.endpoint}/task/${task.id}`, task, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  refreshTasks(world: World) {
    this.standardTaskService.getStandardTasksForWorld(world);
    this.specialTaskService.getSpecialTasksForWorld(world);
  }

  updateStandardTasksForWorld(world: World) {
    return this.http.get(`${environment.endpoint}/task/updateStandardTasks/${world.id}`)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
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
    let newTasks = this.taskDifference(currentTasks, oldTasks);
    let deselectedTasks = this.taskDifference(oldTasks, currentTasks)
    return [newTasks, deselectedTasks];
  }

  addParticipation(task: Task, developer: Developer, quest: Quest){
    return this.http.post(`${environment.endpoint}/task/${task.id}/addParticipation/${quest.id}/${developer.id}`,null)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  removeParticipation(task: any): Promise<any> {
    return this.http.delete(`${environment.endpoint}/task/${task.id}/deleteParticipation`)
      .toPromise()
  }

  


  private taskDifference(array1: Array<Task>, array2: Array<Task>) {
    return array1.filter(x => !array2.includes(x));
  }

  private extractData(res: Response) {
    const body = res.json();
    return body || {};
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
