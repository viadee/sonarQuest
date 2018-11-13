import {Subject} from 'rxjs/Subject';
import {Response} from '@angular/http';
import {Injectable} from '@angular/core';
import {Quest} from '../Interfaces/Quest';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
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
