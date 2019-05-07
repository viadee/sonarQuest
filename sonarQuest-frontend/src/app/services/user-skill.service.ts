import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { HttpParams, HttpClient } from '@angular/common/http';
import { UserSkill } from 'app/Interfaces/UserSkill';
import { Observable, Subject, ReplaySubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserSkillService {
  private userSkillsSubject: Subject<UserSkill[]> = new ReplaySubject(1);
  userSkills$ = this.userSkillsSubject.asObservable();
  private userSkillsFromGroup: UserSkill[];

  constructor(private httpClient: HttpClient) { }

  loadUserSkillsFromGroup(id): Observable<UserSkill[]> {
    const params = new HttpParams().set('id', id);
    this.httpClient.get<UserSkill[]>(`${environment.endpoint}/userskill/bygroup`, { params: params })
      .subscribe(
        result => { this.userSkillsSubject.next(result), this.userSkillsFromGroup = result },
        err => this.userSkillsSubject.error(err)
      );
    return this.userSkillsSubject.asObservable();
  }

  public getUserSkillFromGroup(): UserSkill[] {
    return this.userSkillsFromGroup;
  }


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

  getNumberOfIcons(value: number): number[] {
    const items: number[] = [];
    let forCount = 0;
    console.log(value)
    switch (true) {
      case (value == null): {
        forCount = 0;
        break;
      }
      case (value <= 3): {
        forCount = 1;
        break;
      }
      case (value <= 5): {
        forCount = 2;
        break;
      }
      case (value <= 7): {
        forCount = 3;
        break;
      }
      case (value <= 9): {
        forCount = 4;
        break;
      }

      default: {
        forCount = 5;
        break;
      }
    }

    for (let i = 1; i <= forCount; i++) {
      items.push(i);
    }
    return items;
  }
}
