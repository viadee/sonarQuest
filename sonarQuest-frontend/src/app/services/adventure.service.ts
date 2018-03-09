import { Injectable } from '@angular/core';
import {Http, Headers, Response, RequestOptions} from "@angular/http";
import {environment} from "../../environments/environment";
import {Adventure} from "../Interfaces/Adventure";
import {Observable} from "rxjs/Observable";
import {Developer} from "../Interfaces/Developer";
import {World} from "../Interfaces/World";
import {ReplaySubject} from "rxjs/ReplaySubject";

@Injectable()
export class AdventureService {

  private adventureSubject;

  constructor(public http: Http) {
    this.adventureSubject = new ReplaySubject(1);
  }

  getAdventures(): Observable<Adventure[]> {
    this.http.get(`${environment.endpoint}/adventure`)
      .map(this.extractData)
      .subscribe(
        result => this.adventureSubject.next(result),
        err => this.adventureSubject.error(err)
      ) 
    return this.adventureSubject.asObservable();
  }

  getAdventuresByDeveloperAndWorld(world: World, developer: Developer): Observable<Adventure[][]> {
    this.http.get(`${environment.endpoint}/adventure/${developer.id}/${world.id}`)
      .map(this.extractData)
      .subscribe(
        result => this.adventureSubject.next(result),
        err => this.adventureSubject.error(err)
      ) 
    return this.adventureSubject.asObservable();
  }

  leaveAdventure(adventure: Adventure, developer: Developer): Observable<Adventure[][]> {
    this.http.post(`${environment.endpoint}/adventure/${adventure.id}/deleteDeveloperAndGetFullList/${developer.id}`, null, null)
      .map(this.extractData)
      .subscribe( 
        result => this.adventureSubject.next(result),
        err => this.adventureSubject.error(err)
      ) 
    return this.adventureSubject.asObservable();
  }

  joinAdventure(adventure: Adventure, developer: Developer): Observable<Adventure[][]> {
     this.http.post(`${environment.endpoint}/adventure/${adventure.id}/addDeveloperAndGetFullList/${developer.id}`, null, null)
      .map(this.extractData)
      .subscribe(
        result => this.adventureSubject.next(result),
        err => this.adventureSubject.error(err)
      ) 
    return this.adventureSubject.asObservable();
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

  refreshAdventures(){
    this.getAdventures();
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
