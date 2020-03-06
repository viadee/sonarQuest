import { HighScore } from './../Interfaces/HighScore';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { Raid } from 'app/Interfaces/Raid';
import { Observable, Subject, ReplaySubject } from 'rxjs';
import { QualityGateRaidRewardHistory } from 'app/Interfaces/QualityGateRaidRewardHistory';
import { QualityGateRaid } from 'app/Interfaces/QualityGateRaid';

@Injectable({
  providedIn: 'root'
})
export class QualityGateServiceService {

  private currentQualityGateSubject: Subject<QualityGateRaid> = new ReplaySubject(1);
  currentQualityGate$ = this.currentQualityGateSubject.asObservable();

  constructor(private httpClient: HttpClient) {
  }

  /**
   * Find quality gate by world
   */
  findByWorld(worldId: any): Observable<QualityGateRaid>  {
    const url = `${environment.endpoint}/qualityraid/world/${worldId}`;
    return this.httpClient.get<QualityGateRaid>(url);
  }

  /**
   * Create quality gate from given raid
   * @param raid
   */
  createQualityGateRaid(raid: QualityGateRaid) {
    const url = `${environment.endpoint}/qualityraid/`;
    return this.httpClient.post<QualityGateRaid>(url, raid);
  }

  /**
   * Create default quality gate from world id
   * @param worldId
   */
  createQualityGateRaidFromWorld(worldId: any): Observable<QualityGateRaid> {
    const url = `${environment.endpoint}/qualityraid/createQualityGateRaidFromWorld/${worldId}`;
    return this.httpClient.put<QualityGateRaid>(url, null);
  }

  /**
   * Get history data from quality gate
   * @param worldId
   */
  getHistory(worldId: any): Observable<QualityGateRaidRewardHistory[]>  {
    const url = `${environment.endpoint}/qualityraid/getHistory/${worldId}`;
    return this.httpClient.get<QualityGateRaidRewardHistory[]>(url);
  }

  /**
   * Update or create new quality gate
   * @param raid
   */
  updateQualityGateRaid(raid: QualityGateRaid): Observable<QualityGateRaid> {
    const url = `${environment.endpoint}/qualityraid/updateQualityGateRaid`;
    return this.httpClient.put<QualityGateRaid>(url, raid);
  }

  getHighScore(raid: QualityGateRaid): Observable<HighScore>  {
    const url = `${environment.endpoint}/qualityraid/getRaidHighScore`;
    return this.httpClient.post<HighScore>(url, raid);
  }

}
