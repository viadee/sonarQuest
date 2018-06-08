import {Subject} from 'rxjs/Subject';
import {Adventure} from './../Interfaces/Adventure';
import {Injectable} from '@angular/core';
import {Http, Headers, Response, RequestOptions} from '@angular/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs/Observable';
import {World} from '../Interfaces/World';
import {ReplaySubject} from 'rxjs/ReplaySubject';

@Injectable()
export class AdventureService {

  private adventureSubject;

  private freeAdventuresSubject: Subject<Adventure[]> = new ReplaySubject(1);
  private myAdventuresSubject: Subject<Adventure[]> = new ReplaySubject(1);

  constructor(public http: Http) {
    this.adventureSubject = new ReplaySubject(1);
  }

  getAdventuresForWorld(world: World): Observable<Adventure[]> {
    this.http.get(`${environment.endpoint}/adventure/world/${world.id}`)
      .map(this.extractData)
      .subscribe(
        result => this.adventureSubject.next(result),
        err => this.adventureSubject.error(err)
      );
    return this.adventureSubject.asObservable();
  }


  getFreeAdventures(world: World): Observable<Adventure[]> {
    this.http.get(`${environment.endpoint}/adventure/getFree/${world.id}`)
      .map(this.extractData)
      .subscribe(
        result => this.freeAdventuresSubject.next(result),
        err => this.freeAdventuresSubject.error(err)
      );
    return this.freeAdventuresSubject.asObservable();
  }

  getMyAdventures(world: World): Observable<Adventure[]> {
    this.http.get(`${environment.endpoint}/adventure/getJoined/${world.id}`)
      .map(this.extractData)
      .subscribe(
        result => this.myAdventuresSubject.next(result),
        err => this.myAdventuresSubject.error(err)
      );
    return this.myAdventuresSubject.asObservable();
  }

  leaveAdventure(adventure: Adventure): Promise<any> {
    return this.http.post(`${environment.endpoint}/adventure/${adventure.id}/leave`, null, null)
      .toPromise()
  }

  joinAdventure(adventure: Adventure): Promise<any> {
    return this.http.post(`${environment.endpoint}/adventure/${adventure.id}/join`, null, null)
      .toPromise()
  }

  createAdventure(adventure: any): Promise<Adventure> {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.post(`${environment.endpoint}/adventure/`, adventure, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  updateAdventure(adventure: Adventure): Promise<any> {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.put(`${environment.endpoint}/adventure/${adventure.id}`, adventure, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  addQuest(adventure: any, quest: any): Promise<any> {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.post(`${environment.endpoint}/adventure/${adventure.id}/addQuest/${quest.id}`, null, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  refreshAdventures(world: World) {
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
