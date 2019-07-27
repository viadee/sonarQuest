import {Subject, Observable, ReplaySubject} from 'rxjs';
import {Adventure} from './../Interfaces/Adventure';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {World} from '../Interfaces/World';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';

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

  leaveAdventure(adventure: Adventure): Promise<Adventure> {
    return this.httpClient.post<Adventure>(`${environment.endpoint}/adventure/${adventure.id}/leave`, null)
      .toPromise()
  }

  joinAdventure(adventure: Adventure): Promise<Adventure> {
    return this.httpClient.post<Adventure>(`${environment.endpoint}/adventure/${adventure.id}/join`, null)
      .toPromise();
  }

  createAdventure(adventure: any): Promise<Adventure> {
    return this.httpClient.post<Adventure>(`${environment.endpoint}/adventure/`, adventure)
      .toPromise()
      .catch(this.handleError);
  }

  solveAdventure(adventure: any): Promise<Adventure> {
    return this.httpClient.put<Adventure>(`${environment.endpoint}/adventure/${adventure.id}/solveAdventure`, adventure)
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

  deleteAdventure(adventure: Adventure): Promise<any> {
    return this.httpClient.delete(`${environment.endpoint}/adventure/${adventure.id}`)
      .toPromise()
  }

  private handleError(error: HttpErrorResponse | any) {
    let errMsg: string;
    if (error instanceof HttpErrorResponse) {
      errMsg = `${error.status} - ${error.statusText || ''}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Promise.reject(errMsg);
  }

}
