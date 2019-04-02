import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ReplaySubject, Subject, Observable } from 'rxjs';
import { UserSkill } from '../Interfaces/UserSkill';
import { environment } from '../../environments/environment';
import { User } from 'app/Interfaces/User';
import { World } from 'app/Interfaces/World';
@Injectable({
  providedIn: 'root'
})
export class SkillTreeService {

  private userSkillsSubject: Subject<UserSkill[]> = new ReplaySubject(1);
  private userSkillTreeSubject: Subject<{ nodes: [], links: [] }> = new ReplaySubject(1);
  private userSkillTreeForUserSubject: Subject<{ nodes: [], links: [] }> = new ReplaySubject(1);
  private userSkillTreeForTeamSubject: Subject<{ nodes: [], links: [] }> = new ReplaySubject(1);
  private userSkillGroupTreeSubject: Subject<{ nodes: [], links: [] }> = new ReplaySubject(1);

  userSkills$ = this.userSkillsSubject.asObservable();
  userSkillGroupTree$ = this.userSkillGroupTreeSubject.asObservable();
  userSkillTree$ = this.userSkillTreeSubject.asObservable();
  userSkillTreeForUser$ = this.userSkillTreeForUserSubject.asObservable();
  userSkillTreeForTeam$ = this.userSkillTreeForTeamSubject.asObservable();

  constructor(public http: HttpClient) { }

 
 /*  getUserSkills(): Observable<UserSkill[]> {
    this.http.get<UserSkill[]>(`${environment.endpoint}/userskill/roots/`)
      .subscribe(
        result => this.userSkillsSubject.next(result),
        err => this.userSkillsSubject.error(err)
      );
    return this.userSkillsSubject.asObservable();
  } */

  getUserSkillTreeFromTeam(id, world: World): Observable<{ nodes: [], links: [] }> {
    const params = new HttpParams().set('id', id).set('worldID', String( world.id));
    this.http.get<{ nodes: [], links: [] }>(`${environment.endpoint}/sqskilltree/fromgroup/user/`, {params: params})
      .subscribe(
        result => this.userSkillTreeForTeamSubject.next(result),
        err => this.userSkillTreeForTeamSubject.error(err)
      );
    return this.userSkillTreeForTeamSubject.asObservable();
  } 
  getUserSkillTreeFromUser(id, user: User): Observable<{ nodes: [], links: [] }> {
    const params = new HttpParams().set('id', id).set('mail', user.mail);
        this.http.get<{ nodes: [], links: [] }>(`${environment.endpoint}/skilltree/fromgroup/user/`, {params: params})
      .subscribe(
        result => this.userSkillTreeForUserSubject.next(result),
        err => this.userSkillTreeForUserSubject.error(err)
      );
    return this.userSkillTreeForUserSubject.asObservable();
  }

  getUserSkillTree(id): Observable<{ nodes: [], links: [] }> {
    const params = new HttpParams().set('id', id)
    this.http.get<{ nodes: [], links: [] }>(`${environment.endpoint}/skilltree/fromgroup/user/`, {params: params})
      .subscribe(
        result => this.userSkillTreeSubject.next(result),
        err => this.userSkillTreeSubject.error(err)
      );
    return this.userSkillTreeSubject.asObservable();
  }

  getUserSkillGroupTree(): Observable<{ nodes: [], links: [] }> {
    this.http.get<{ nodes: [], links: [] }>(`${environment.endpoint}/skilltree/overview/`)
      .subscribe(
        result => this.userSkillGroupTreeSubject.next(result),
        err => this.userSkillGroupTreeSubject.error(err)
      );
    return this.userSkillGroupTreeSubject.asObservable();
  }

  getData(): void {
   // this.getUserSkills();
    this.getUserSkillGroupTree();
  }
}
