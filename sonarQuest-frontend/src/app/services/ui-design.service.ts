import {UiDesign} from './../Interfaces/UiDesign';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {Response} from '@angular/http';
import {UserService} from './user.service';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class UiDesignService {

  private uiDesign: UiDesign;

  constructor(
    private http: HttpClient,
    private userService: UserService
  ) {
    this.userService.onUserChange().subscribe(() => this.loadUiDesign());
  }

  loadUiDesign(): void {
    this.http.get<UiDesign>(`${environment.endpoint}/ui`)
      .subscribe(
        uiDesign => this.uiDesign = uiDesign,
        () => this.uiDesign = null
      );
  }

  getUiDesign(): UiDesign {
    return this.uiDesign;
  }

  updateUiDesign(designName: String): Promise<UiDesign> {
    return this.http.put<UiDesign>(`${environment.endpoint}/ui`, designName)
      .toPromise()
      .catch(this.handleError);
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
