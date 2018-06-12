import {World} from './../Interfaces/World';
import {Injectable} from '@angular/core';
import {Http, RequestOptions, Headers, Response} from '@angular/http';
import {environment} from '../../environments/environment';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/publishLast';
import 'rxjs/add/operator/share';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {StandardTask} from '../Interfaces/StandardTask';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class StandardTaskService {

  private standardTaskSubject;

  constructor(public http: HttpClient) {
    this.standardTaskSubject = new ReplaySubject(1);
  }

  getStandardTasksForWorld(world: World): Observable<StandardTask[]> {
    this.http.get<StandardTask[]>(`${environment.endpoint}/task/standard/world/${world.id}`).subscribe(
      result => this.standardTaskSubject.next(result),
      err => this.standardTaskSubject.error(err)
    )
    return this.standardTaskSubject.asObservable();
  }

  updateStandardTask(task: any): Promise<any> {
    return this.http.put<StandardTask>(`${environment.endpoint}/task/${task.id}`, task)
      .toPromise()
      .catch(this.handleError);
  }

  refreshStandardTask(world: World): Promise<any> {
    return this.http.get<StandardTask>(`${environment.endpoint}/task/updateStandardTasks/${world.id}`)
      .toPromise().catch(this.handleError);
  }

  createStandardTask(standardTask: any): Promise<StandardTask> {
    standardTask.world.quests = [];
    standardTask.world.tasks = [];
    return this.http.post<StandardTask>(`${environment.endpoint}/task/standard`, standardTask)
      .toPromise()
      .catch(this.handleError);
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
