import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment";
import { HttpModule, Http, Response } from "@angular/http";
import { Developer } from "../Interfaces/Developer";

@Injectable()
export class DeveloperService {

  developerSubject: Subject<Developer>;

  constructor(public http: Http) {
    this.developerSubject= new Subject();
  }

  getMyAvatar(): Observable<Developer> {
    this.http.get(`${environment.endpoint}/developer/1`)
      .map(this.extractData)
      .subscribe(
        result => this.developerSubject.next(result),
        err => this.developerSubject.error(err)
      );
    return this.developerSubject;

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
