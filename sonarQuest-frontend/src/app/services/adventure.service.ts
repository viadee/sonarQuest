import {Subject} from 'rxjs/Subject';
import {Adventure} from './../Interfaces/Adventure';
import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs/Observable';
import {World} from '../Interfaces/World';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class AdventureService {

  private adventureSubject;

  private freeAdventuresSubject: Subject<Adventure[]> = new ReplaySubject(1);
  private myAdventuresSubject: Subject<Adventure[]> = new ReplaySubject(1);


  constructor(private httpClient: HttpClient) {
    this.adventureSubject = new ReplaySubject(1);
  }

  getAdventuresForWorld(world: World): Observable<Adventure[]> {
    const url = `${environment.endpoint}/adventure/world/${world.id}`;
    this.httpClient.get<Adventure[]>(url).subscribe(
      adventures => this.adventureSubject.next(adventures),
      err => this.adventureSubject.error(err)
    );
    return this.adventureSubject.asObservable();
  }


  getFreeAdventures(world: World): Observable<Adventure[]> {
    const url = `${environment.endpoint}/adventure/getFree/${world.id}`;
    this.httpClient.get<Adventure[]>(url)
      .subscribe(
        result => this.freeAdventuresSubject.next(result),
        err => this.freeAdventuresSubject.error(err)
      );
    return this.freeAdventuresSubject.asObservable();
  }

  getMyAdventures(world: World): Observable<Adventure[]> {
    this.httpClient.get<Adventure[]>(`${environment.endpoint}/adventure/getJoined/${world.id}`)
      .subscribe(
        result => this.myAdventuresSubject.next(result),
        err => this.myAdventuresSubject.error(err)
      );
    return this.myAdventuresSubject.asObservable();
  }

  leaveAdventure(adventure: Adventure): Promise<any> {
    return this.httpClient.post<Adventure>(`${environment.endpoint}/adventure/${adventure.id}/leave`, null, null)
      .toPromise()
  }

  joinAdventure(adventure: Adventure): Promise<any> {
    return this.httpClient.post<Adventure>(`${environment.endpoint}/adventure/${adventure.id}/join`, null, null)
      .toPromise()
  }

  createAdventure(adventure: any): Promise<Adventure> {
    return this.httpClient.post<Adventure>(`${environment.endpoint}/adventure/`, adventure)
      .toPromise()
      .catch(this.handleError);
  }

  updateAdventure(adventure: Adventure): Promise<any> {
    return this.httpClient.put<Adventure>(`${environment.endpoint}/adventure/${adventure.id}`, adventure)
      .toPromise()
      .catch(this.handleError);
  }

  addQuest(adventure: any, quest: any): Promise<any> {
    return this.httpClient.post<Adventure>(`${environment.endpoint}/adventure/${adventure.id}/addQuest/${quest.id}`, null)
      .toPromise()
      .catch(this.handleError);
  }

  refreshAdventures(world: World) {
    this.getAdventuresForWorld(world);
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
