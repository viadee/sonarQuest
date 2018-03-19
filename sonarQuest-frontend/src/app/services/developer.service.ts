import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment";
import { HttpModule, Http, Response, RequestOptions, Headers } from "@angular/http";
import { Developer } from "../Interfaces/Developer";

@Injectable()
export class DeveloperService {

  developerSubject: Subject<Developer>   = new Subject();
  developers:       Subject<Developer[]> = new Subject();
  developers$                            = this.developers.asObservable();

  constructor(public http: Http) {  }

  getMyAvatar(): Observable<Developer> {
    this.http.get(`${environment.endpoint}/developer/1`)
      .map(this.extractData)
      .subscribe(
        result => this.developerSubject.next(result),
        err => this.developerSubject.error(err)
      );
    return this.developerSubject;
  }

  getDevelopers(): Observable<Developer[]>{
    this.http.get(`${environment.endpoint}/developer`)
      .map(this.extractData)
      .subscribe(
        value => {this.developers.next(value)},
        err   => {this.developers.error(err)}
      );
     return this.developers
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

  deleteDeveloper(developer: Developer): Promise<any>{
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
