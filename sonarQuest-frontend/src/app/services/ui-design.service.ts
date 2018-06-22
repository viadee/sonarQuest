import {UiDesign} from './../Interfaces/UiDesign';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class UiDesignService {

  constructor(
    private http: HttpClient) {
  }

  getUiDesign(): Observable<UiDesign> {
    return this.http.get<UiDesign>(`${environment.endpoint}/ui`);
  }

  updateUiDesign(designName: String): Promise<UiDesign> {
    return this.http.put<UiDesign>(`${environment.endpoint}/ui`, designName).toPromise();
  }

}
