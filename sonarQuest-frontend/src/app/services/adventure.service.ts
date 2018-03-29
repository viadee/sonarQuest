import { Subject } from 'rxjs/Subject';
import { Adventure } from './../Interfaces/Adventure';
import { Injectable } from '@angular/core';
import {Http, Headers, Response, RequestOptions} from "@angular/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs/Observable";
import {Developer} from "../Interfaces/Developer";
import {World} from "../Interfaces/World";
import {ReplaySubject} from "rxjs/ReplaySubject";

@Injectable()
export class AdventureService {

  private adventureSubject;

  private freeAdventuresSubject: Subject<Adventure[]> = new ReplaySubject(1);
  freeAdventures$ = this.freeAdventuresSubject.asObservable();
  private   myAdventuresSubject: Subject<Adventure[]> = new ReplaySubject(1);
  myAdventure$    = this.myAdventuresSubject.asObservable();

  constructor(public http: Http) {
    this.adventureSubject = new ReplaySubject(1);
  }

  getAdventuresForWorld(world:World): Observable<Adventure[]> {
    this.http.get(`${environment.endpoint}/adventure/world/${world.id}`)
      .map(this.extractData)
      .subscribe(
        result => this.adventureSubject.next(result),
        err => this.adventureSubject.error(err)
      ) 
    return this.adventureSubject.asObservable();
  }


  getFreeAdventures(world: World, developer: Developer): Observable<Adventure[]>{
    this.http.get(`${environment.endpoint}/adventure/getFree/${developer.id}/${world.id}`)
      .map(this.extractData)
      .subscribe(
        result => this.freeAdventuresSubject.next(result),
        err    => this.freeAdventuresSubject.error(err)
      ) 
    return this.freeAdventuresSubject.asObservable();
  }

  getMyAdventures(world: World, developer: Developer): Observable<Adventure[]>{
    this.http.get(`${environment.endpoint}/adventure/getJoined/${developer.id}/${world.id}`)
      .map(this.extractData)
      .subscribe(
        result => this.myAdventuresSubject.next(result),
        err    => this.myAdventuresSubject.error(err)
      ) 
    return this.myAdventuresSubject.asObservable();
  }

  leaveAdventure(adventure: Adventure, developer: Developer): Promise<any> {
    return this.http.delete(`${environment.endpoint}/adventure/${adventure.id}/deleteDeveloper/${developer.id}`)
      .toPromise()
  }

  joinAdventure(adventure: Adventure, developer: Developer): Promise<any> {
     return this.http.post(`${environment.endpoint}/adventure/${adventure.id}/addDeveloper/${developer.id}`, null, null)
     .toPromise()
  }

  createAdventure(adventure: any): Promise<Adventure> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/adventure/`, adventure, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  updateAdventure(adventure: Adventure): Promise<any>{
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.put(`${environment.endpoint}/adventure/${adventure.id}`, adventure, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  addQuest(adventure: any, quest:any): Promise<any>{
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/adventure/${adventure.id}/addQuest/${quest.id}`, null, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  refreshAdventures(world: World){
    this.getAdventuresForWorld(world);
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
