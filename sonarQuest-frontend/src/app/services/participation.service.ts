import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {Quest} from '../Interfaces/Quest';
import {environment} from '../../environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {Participation} from '../Interfaces/Participation';

@Injectable()
export class ParticipationService {

  private participationUpdateSource = new Subject<string>();
  participationUpdated$ = this.participationUpdateSource.asObservable();

  constructor(public http: HttpClient) {
  }

  createParticipation(quest: Quest): Promise<Participation> {
    return this.http.post<Participation>(`${environment.endpoint}/participation/${quest.id}`, null)
      .toPromise()
      .catch(this.handleError);
  }

  public getParticipations(quest: Quest): Promise<Participation[]> {
    return this.http.get<Participation[]>(`${environment.endpoint}/participation/${quest.id}/all`).toPromise();
  }

  public getAllMyParticipations(): Promise<Participation[]> {
    return this.http.get<Participation[]>(`${environment.endpoint}/participation/allMyParticipations`).toPromise();
  }

  announceParticipationUpdate() {
    this.participationUpdateSource.next();
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
