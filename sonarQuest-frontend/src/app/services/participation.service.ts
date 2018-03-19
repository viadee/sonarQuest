import { Subject } from 'rxjs/Subject';
import { RequestOptions, Headers, Response } from '@angular/http';
import { Developer } from './../Interfaces/Developer.d';
import { Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { Quest } from '../Interfaces/Quest';
import { environment } from "../../environments/environment";
@Injectable()
export class ParticipationService {

  private participationUpdateSource = new Subject<string>();
  participationUpdated$ = this.participationUpdateSource.asObservable();

  constructor(public http: Http) { }

  createParticipation(developer: Developer, quest: Quest): Promise<any> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/participation/${quest.id}/${developer.id}`, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  
  announceParticipationUpdate(){
    this.participationUpdateSource.next();
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
