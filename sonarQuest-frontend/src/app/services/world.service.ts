import { Developer } from './../Interfaces/Developer.d';
import { DeveloperService } from './developer.service';
import { ReplaySubject } from 'rxjs/ReplaySubject';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { Injectable } from '@angular/core';
import {World} from '../Interfaces/World';
import {environment} from '../../environments/environment';
import {Http, RequestOptions, Response, Headers} from '@angular/http';

@Injectable()
export class WorldService {


  worldSubject: Subject<World>    = new ReplaySubject(1);
  currentWorld$                   = this.worldSubject.asObservable();
  worldsSubject: Subject<World[]> = new ReplaySubject(1);
  worlds$                         = this.worldsSubject.asObservable();
  developer: Developer;

  constructor(
    public http: Http
  ) { 
    this.getWorlds();
  }

  getWorlds(): Observable<World[]> {
    this.http.get(`${environment.endpoint}/world`)
      .map(this.extractData)
      .subscribe(
        value => {this.worldsSubject.next(value)},
        err   => {this.worldsSubject.next(err)}
      );
      return this.worldsSubject
  }


  updateWorld(world: World): Promise<any>{
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.put(`${environment.endpoint}/world/${world.id}`, world, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }



  private extractData(res: Response) {
    let body = res.json();
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
