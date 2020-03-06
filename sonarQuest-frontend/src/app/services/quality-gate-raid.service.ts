import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HighScore } from './../Interfaces/HighScore';
import { environment } from 'environments/environment';
import { QualityGateRaid } from 'app/Interfaces/QualityGateRaid';
import { Observable } from 'rxjs';
import { QualityGateRaidRewardHistory } from 'app/Interfaces/QualityGateRaidRewardHistory';

@Injectable({
  providedIn: 'root'
})
export class QualityGateRaidService {

  constructor(private httpClient: HttpClient) {
  }

  /**
   * Find quality gate by world
   */
  findByWorld(worldId: any): Observable<QualityGateRaid>  {
    const url = `${environment.endpoint}/qualitygateraid/world/${worldId}`;
    return this.httpClient.get<QualityGateRaid>(url);
  }

  /**
   * Get history data from quality gate
   * @param worldId
   */
  getHistory(worldId: any): Observable<QualityGateRaidRewardHistory[]>  {
    const url = `${environment.endpoint}/qualitygateraid/getHistory/${worldId}`;
    return this.httpClient.get<QualityGateRaidRewardHistory[]>(url);
  }

  /**
   * Update or create new quality gate
   * @param raid
   */
  updateQualityGateRaid(raid: QualityGateRaid): Observable<QualityGateRaid> {
    const url = `${environment.endpoint}/qualitygateraid/updateQualityGateRaid`;
    return this.httpClient.put<QualityGateRaid>(url, raid);
  }

  /**
   * Get the actual error-free day score from quality gate
   * @param raid
   */
  calculateActualScore(raid: QualityGateRaid): Observable<HighScore>  {
    const url = `${environment.endpoint}/qualitygateraid/calculateActualScore`;
    return this.httpClient.post<HighScore>(url, raid);
  }
}
