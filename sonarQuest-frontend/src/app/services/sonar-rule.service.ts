import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { HttpClient } from '@angular/common/http';
import { Response } from '@angular/http';
import { SonarRule } from 'app/Interfaces/SonarRule';
import { Subject, ReplaySubject, Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})
export class SonarRuleService {
  private unassignedSonarRules: SonarRule[];
  private unassignedSonarRulesSubject: Subject<SonarRule[]> = new ReplaySubject(1);
  unassignedSonarRules$ = this.unassignedSonarRulesSubject.asObservable();

  constructor(public http: HttpClient) { }

  loadUnassignedSonarRules(): Observable<SonarRule[]> {
    this.http.get<SonarRule[]>(`${environment.endpoint}/sonarrule/unassignedRules`).subscribe(
      result => {
        this.unassignedSonarRulesSubject.next(result);
        this.unassignedSonarRules = result
      },
      err => this.unassignedSonarRulesSubject.error(err)
    );
    return this.unassignedSonarRulesSubject.asObservable();
  }
  getlastAddedRule(): SonarRule[] {
    return this.unassignedSonarRules;
  }
}
