import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Raid } from 'app/Interfaces/Raid';

@Injectable({
  providedIn: 'root'
})
export class RaidService {

  constructor(private httpClient: HttpClient) {
  }

  createRaid(raid: Raid) {
    const url = `${environment.endpoint}/raid/`;
    return this.httpClient.post<Raid>(url, raid).toPromise().catch(this.handleError);
  }

  findRaidById(raidId: any): Observable<Raid>  {
    const url = `${environment.endpoint}/raid/${raidId}`;
    return this.httpClient.get<Raid>(url);
  }

  findAllRaidsByWorld(worldId: number): Observable<Raid[]>  {
    const url = `${environment.endpoint}/raid/world/${worldId}`;
    return this.httpClient.get<Raid[]>(url);
  }

  private handleError(error: HttpErrorResponse | any) {
    let errMsg: string;
    if (error instanceof HttpErrorResponse) {
      errMsg = `${error.status} - ${error.statusText || ''}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Promise.reject(errMsg);
  }
}
