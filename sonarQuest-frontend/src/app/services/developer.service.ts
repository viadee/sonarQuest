import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpModule, Http, Response} from "@angular/http";
import {Developer} from "../Interfaces/Developer";

@Injectable()
export class DeveloperService {

  myAvatar: Developer;

  constructor(public http: Http) {
  }

  getMyAvatar(): Promise<Developer> {
    if (this.myAvatar) {
      return Promise.resolve(this.myAvatar);
    } else {
      return this.http.get(`${environment.endpoint}/developer/1`)
        .toPromise()
        .then(response => {
          this.myAvatar = this.extractData(response);
          return this.myAvatar;
        })
        .catch(this.handleError);
    }
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
