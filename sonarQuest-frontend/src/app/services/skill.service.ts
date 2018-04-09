import { ReplaySubject } from 'rxjs/ReplaySubject';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import { RequestOptions, Http, Response, Headers } from '@angular/http';
import { environment } from "../../environments/environment";
import { Skill } from './../Interfaces/Skill';
import { Injectable } from '@angular/core';
import { Artefact } from '../Interfaces/Developer';

@Injectable()
export class SkillService {

  private skillsSubject: Subject<Skill[]> = new ReplaySubject(1);
  skills$ = this.skillsSubject.asObservable();

  constructor(public http: Http) {
    this.getSkills();
  }


  getSkills(): Observable<Skill[]> {
    this.http.get(`${environment.endpoint}/skill/`)
      .map(this.extractData)
      .subscribe(
        result => {
          this.skillsSubject.next(result)
        },
        err => this.skillsSubject.error(err)
      )
    return this.skillsSubject.asObservable();
  }

  getSkillsForArtefact(artefact: Artefact): Promise<Skill[]> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.get(`${environment.endpoint}/skill/artefact/${artefact.id}`)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  deleteSkill(skill: Skill): Promise<any> {
    return this.http.delete(`${environment.endpoint}/skill/${skill.id}`)
      .toPromise()
  }

  createSkill(skill: any): Promise<Skill> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/skill/`, skill, options)
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
