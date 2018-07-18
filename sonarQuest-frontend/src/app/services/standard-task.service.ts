import {World} from './../Interfaces/World';
import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {environment} from '../../environments/environment';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/publishLast';
import 'rxjs/add/operator/share';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {StandardTask} from '../Interfaces/StandardTask';
import {HttpClient} from '@angular/common/http';
import {Task} from '../Interfaces/Task';

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
    );
    return this.standardTaskSubject.asObservable();
  }

  updateStandardTask(task: StandardTask): Promise<StandardTask> {
    console.log('update: ' + task);
    return this.http.put<StandardTask>(`${environment.endpoint}/task/standard`, task)
      .toPromise()
      .catch(this.handleError);
  }

  updateStandardTasksForWorld(world: World): Promise<Task[]> {
    return this.http.get<Task[]>(`${environment.endpoint}/task/updateStandardTasks/${world.id}`)
      .toPromise()
      .catch(this.handleError);
  }

  updateStandardTasksScoresForWorld(world: World): Promise<void> {
    return this.http.post<void>(`${environment.endpoint}/externalRessource/updateScores/${world.id}`, null)
      .toPromise()
      .catch(this.handleError);
  }

  public getFreeStandardTasksForWorldExcept(world: World, excludetTasks: Task[]): Promise<StandardTask[]> {
    return this.http.get<StandardTask[]>(`${environment.endpoint}/task/standard/world/${world.id}`)
      .toPromise().then(tasks => {
        const excludetTaskIds = excludetTasks.map(task => task.id);
        return tasks.filter(task => !excludetTaskIds.includes(task.id));
      })
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
