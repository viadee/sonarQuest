import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {ReplaySubject, Subject, Observable} from 'rxjs';
import { UserSkill } from '../Interfaces/UserSkill';
import {environment} from '../../environments/environment';
@Injectable({
    providedIn: 'root'
})
export class SkillTreeService {

     private userSkillsSubject: Subject<UserSkill[]> = new ReplaySubject(1);
  userskills$ = this.userSkillsSubject.asObservable();

    constructor(public http: HttpClient) { }

    getUserSkills(): Observable<UserSkill[]> {
    this.http.get<UserSkill[]>(`${environment.endpoint}/userskill/`)
      .subscribe(
        result => this.userSkillsSubject.next(result),
        err => this.userSkillsSubject.error(err)
      );
    return this.userSkillsSubject.asObservable();
  }

   getData(): void {
    this.getUserSkills();
  }
}
