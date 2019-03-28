import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ReplaySubject, Subject, Observable } from 'rxjs';
import { UserSkill } from '../Interfaces/UserSkill';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class SkillTreeService {

  private userSkillsSubject: Subject<UserSkill[]> = new ReplaySubject(1);
  private userSkillTreeSubject: Subject<{ nodes: [], links: [] }> = new ReplaySubject(1);
  private userSkillGroupTreeSubject: Subject<{ nodes: [], links: [] }> = new ReplaySubject(1);
  userSkills$ = this.userSkillsSubject.asObservable();
  userSkillGroupTree$ = this.userSkillGroupTreeSubject.asObservable();
  userSkillTree$ = this.userSkillTreeSubject.asObservable();

  constructor(public http: HttpClient) { }

  //TODO evtl. ueberflüssig
  getUserSkills(): Observable<UserSkill[]> {
    this.http.get<UserSkill[]>(`${environment.endpoint}/userskill/roots/`)
      .subscribe(
        result => this.userSkillsSubject.next(result),
        err => this.userSkillsSubject.error(err)
      );
    return this.userSkillsSubject.asObservable();
  }

  getUserSkillTree(id): Observable<{ nodes: [], links: [] }> {
    const params = new HttpParams().set('id', id)
    this.http.get<{ nodes: [], links: [] }>(`${environment.endpoint}/skilltree/fromgroup/`, {params: params})
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
