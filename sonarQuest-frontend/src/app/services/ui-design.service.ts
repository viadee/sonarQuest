import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Subject} from 'rxjs/Subject';

import {UiDesign} from './../Interfaces/UiDesign';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {Http, RequestOptions, Response, Headers} from '@angular/http';
import {UserService} from './user.service';

@Injectable()
export class UiDesignService {

  private uiSubject: Subject<UiDesign> = new ReplaySubject(1);
  public ui$ = this.uiSubject.asObservable();

  constructor(
    private http: Http,
    private userService: UserService
  ) {
    this.userService.avatar$.subscribe(d => {
      this.getUiDesign();
    })
  }

  getUiDesign(): Observable<UiDesign> {
    this.http.get(`${environment.endpoint}/ui`)
      .map(this.extractData)
      .subscribe(
        value => this.uiSubject.next(value),
        err => this.uiSubject.next(err)
      );
    return this.uiSubject
  }

  updateUiDesign(designName: String): Promise<any> {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.put(`${environment.endpoint}/ui`, designName, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
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
