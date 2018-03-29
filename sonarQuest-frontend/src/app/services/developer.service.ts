import { World } from './../Interfaces/World';
import { WorldService } from './world.service';
import { Developer } from './../Interfaces/Developer.d';
import { ReplaySubject } from 'rxjs/ReplaySubject';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment";
import { HttpModule, Http, Response, RequestOptions, Headers } from "@angular/http";
@Injectable()
export class DeveloperService {

  developerSubject: Subject<Developer> = new ReplaySubject(1);
  avatar$ = this.developerSubject.asObservable();
  developersSubject: Subject<Developer[]> = new ReplaySubject(1);
  developers$ = this.developersSubject.asObservable();

  constructor(
    public http: Http,
    private worldService: WorldService
  ) {
  }

  getMyAvatar(): Observable<Developer> {
    this.http.get(`${environment.endpoint}/developer/1`)
      .map(this.extractData)
      .subscribe(
        developer => {
          this.developerSubject.next(developer)
          this.worldService.worldSubject.next(developer.world)
        },
        err => this.developerSubject.error(err)
      );
    return this.developerSubject;
  }

  getDevelopers(): Observable<Developer[]> {
    this.http.get(`${environment.endpoint}/developer`)
      .map(this.extractData)
      .subscribe(
        value => { this.developersSubject.next(value) },
        err => { this.developersSubject.error(err) }
      );
    return this.developersSubject
  }

  updateCurrentWorldToDeveloper(world: any, developer: any): Observable<Developer>{
    this.http.get(`${environment.endpoint}/developer/${developer.id}/updateWorld/${world.id}`)
      .map(this.extractData)
      .subscribe(
        developer => { 
          this.developerSubject.next(developer)
          this.worldService.worldSubject.next(developer.world)
        },
        err   => { 
          this.developerSubject.error(err)
        }
      );
      return this.developerSubject;          
  }


  createDeveloper(developer: any): Promise<Developer> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/developer/`, developer, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  updateDeveloper(developer: Developer): Promise<Developer> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.put(`${environment.endpoint}/developer/${developer.id}`, developer, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  deleteDeveloper(developer: Developer): Promise<any> {
    return this.http.delete(`${environment.endpoint}/developer/${developer.id}`)
      .toPromise()
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
