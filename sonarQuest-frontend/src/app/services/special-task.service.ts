import { Task } from 'app/Interfaces/Task';
import { World } from './../Interfaces/World';
import { Injectable } from '@angular/core';
import {Http, RequestOptions, Headers,Response} from "@angular/http";
import {environment} from "../../environments/environment";
import {SpecialTask} from "../Interfaces/SpecialTask";
import {ReplaySubject} from "rxjs/ReplaySubject";
import {Observer} from "rxjs/Observer";
import {Observable} from "rxjs/Observable";

@Injectable()
export class SpecialTaskService {

  private specialTaskSubject;

  constructor(public http: Http) {
    this.specialTaskSubject = new ReplaySubject(1);
  }

  getSpecialTasksForWorld(world: World): Observable<SpecialTask[]> {
    this.http.get(`${environment.endpoint}/task/world/${world.id}`)
      .map(response =>{
        let tasks=this.extractData(response);
        return tasks[0];
      }).subscribe(
      result => this.specialTaskSubject.next(result),
      err    => this.specialTaskSubject.error(err)
    )
    return this.specialTaskSubject.asObservable();
  }

  createSpecialTask(specialTask: any): Promise<any>{
    specialTask.world.quests = [];
    specialTask.world.tasks  = [];
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/task/special`, specialTask, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  updateSpecialTask(specialTask: any): Promise<any>{
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.put(`${environment.endpoint}/task/${specialTask.id}`, specialTask, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  deleteSpecialTask(specialTask: any): Promise<any>{
    return this.http.delete(`${environment.endpoint}/task/${specialTask.id}`)
      .toPromise()
      .then()
      .catch(this.handleError);
  }

  solveSpecialTask(specialTask: any): Promise<any>{
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.put(`${environment.endpoint}/task/${specialTask.id}/solveSpecialTask/`, specialTask, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
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
