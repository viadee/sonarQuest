import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import {HttpClient} from '@angular/common/http';
import {Response} from '@angular/http';
import { SonarRule } from 'app/Interfaces/SonarRule';
import { Subject, ReplaySubject, Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})
export class SonarRuleService {
  private lastAddedRule: SonarRule;
  private lastAddedSonarRuleSubject: Subject<SonarRule> = new ReplaySubject(1);
  lastAddedSonarRule$ = this.lastAddedSonarRuleSubject.asObservable();

  constructor(public http: HttpClient) { }

  loadLastAddedSonarRule(): Observable<SonarRule> {
    this.http.get<SonarRule>(`${environment.endpoint}/sonarrule/lastAdded`).subscribe(
      result => {this.lastAddedSonarRuleSubject.next(result); this.lastAddedRule = result},
      err => this.lastAddedSonarRuleSubject.error(err)
    );
    return this.lastAddedSonarRuleSubject.asObservable();
  }
  getlastAddedRule(): SonarRule{
return this.lastAddedRule;
  }
}
