import {World} from './../Interfaces/World';
import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {environment} from '../../environments/environment';
import {SpecialTask} from '../Interfaces/SpecialTask';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {Task} from '../Interfaces/Task';
import {StandardTask} from '../Interfaces/StandardTask';

@Injectable()
export class SpecialTaskService {

  private specialTaskSubject;

  constructor(public http: HttpClient) {
    this.specialTaskSubject = new ReplaySubject(1);
  }

  getSpecialTasksForWorld(world: World): Observable<SpecialTask[]> {
    this.http.get<SpecialTask[]>(`${environment.endpoint}/task/special/world/${world.id}`).subscribe(
      result => this.specialTaskSubject.next(result),
      err => this.specialTaskSubject.error(err)
    )
    return this.specialTaskSubject.asObservable();
  }

  createSpecialTask(specialTask: any): Promise<SpecialTask> {
    specialTask.world.quests = [];
    specialTask.world.tasks = [];
    return this.http.post<SpecialTask>(`${environment.endpoint}/task/special`, specialTask)
      .toPromise()
      .catch(this.handleError);
  }

  updateSpecialTask(specialTask: any): Promise<SpecialTask> {
    return this.http.put<SpecialTask>(`${environment.endpoint}/task/special`, specialTask)
      .toPromise()
      .catch(this.handleError);
  }

  deleteSpecialTask(specialTask: any): Promise<any> {
    return this.http.delete(`${environment.endpoint}/task/${specialTask.id}`)
      .toPromise()
      .then()
      .catch(this.handleError);
  }

  solveSpecialTask(specialTask: any): Promise<SpecialTask> {
      return this.http.put<SpecialTask>(`${environment.endpoint}/task/${specialTask.id}/solveSpecialTask/`, specialTask)
      .toPromise()
      .catch(this.handleError);
  }

  public getFreeSpecialTasksForWorldExcept(world: World, excludetTasks: Task[]): Promise<SpecialTask[]> {
    return this.http.get<SpecialTask[]>(`${environment.endpoint}/task/special/world/${world.id}`)
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
