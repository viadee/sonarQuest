import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { HttpParams, HttpClient } from '@angular/common/http';
import { UserSkill } from 'app/Interfaces/UserSkill';

@Injectable({
  providedIn: 'root'
})
export class UserSkillService {

  constructor(private httpClient: HttpClient) { }

  updateUserSkill(userSkill: UserSkill): Promise<any> {
    return this.httpClient.put<UserSkill>(`${environment.endpoint}/userskill/update`, userSkill)
      .toPromise()
      .catch(this.handleError);
  }

  private handleError(error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      //const err = body.error || JSON.stringify(body);
     // errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Promise.reject(errMsg);
  }
}
