import { HttpClient, HttpHeaders, HttpEvent } from '@angular/common/http';
import { World } from './../Interfaces/World';
import { WorldService } from './world.service';
import { Developer } from './../Interfaces/Developer.d';
import { ReplaySubject } from 'rxjs/ReplaySubject';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment";
import { HttpModule, Http, Response, RequestOptions, Headers, ResponseContentType } from "@angular/http";
import { DomSanitizer } from '@angular/platform-browser';
import { HttpRequest } from 'selenium-webdriver/http';
@Injectable()
export class DeveloperService {

  developerSubject: Subject<Developer> = new ReplaySubject(1);
  avatar$ = this.developerSubject.asObservable();
  developersSubject: Subject<Developer[]> = new ReplaySubject(1);
  developers$ = this.developersSubject.asObservable();

  constructor(
    public http: Http,
    public httpClient: HttpClient,
    private worldService: WorldService
  ) {
  }

  getMyAvatar(): Observable<Developer> {
    this.http.get(`${environment.endpoint}/developer/1`)
      .map(this.extractData)
      .subscribe(
        developer => {
          this.developerSubject.next(developer)
          this.worldService.worldSubject.next(developer.world)
        },
        err => this.developerSubject.error(err)
      );
    return this.developerSubject;
  }

  getDevelopers(): Observable<Developer[]> {
    this.http.get(`${environment.endpoint}/developer`)
      .map(this.extractData)
      .subscribe(
        value => { this.developersSubject.next(value) },
        err => { this.developersSubject.error(err) }
      );
    return this.developersSubject
  }

  updateCurrentWorldToDeveloper(world: any, developer: any): Observable<Developer> {
    this.http.get(`${environment.endpoint}/developer/${developer.id}/updateWorld/${world.id}`)
      .map(this.extractData)
      .subscribe(
        developer => {
          this.developerSubject.next(developer)
          this.worldService.worldSubject.next(developer.world)
        },
        err => {
          this.developerSubject.error(err)
        }
      );
    return this.developerSubject;
  }


  createDeveloper(developer: any): Promise<Developer> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/developer/`, developer, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  updateDeveloper(developer: Developer): Promise<Developer> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.put(`${environment.endpoint}/developer/${developer.id}`, developer, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }


  deleteDeveloper(developer: Developer): Promise<any> {
    return this.http.delete(`${environment.endpoint}/developer/${developer.id}`)
      .toPromise()
  }


  getLevel(xp: number): number {
    return this.calculateLevel(xp, 1);
  }


  getImage(developer: Developer): Observable<Blob> {
    const url = `${environment.endpoint}/developer/${developer.id}/avatar`
    return this.http
      .get(url, { responseType: ResponseContentType.Blob })
      .map((res: Response) => res.blob());
  }

  postImage(image: File, developer: Developer) {
    const url = `${environment.endpoint}/developer/${developer.id}/avatar`
    let formdata: FormData = new FormData();
    formdata.append('image', image);
    return this.http.post(url, formdata)

  }

 /*  pushFileToStorage(file: File) {
    const url = `${environment.endpoint}/developer/1/avatar`
    let formdata: FormData = new FormData();
    formdata.append('file', file);
    const req = new HttpRequest('POST', url, formdata);
  } */

  /* getFiles(): Observable<string[]> {
    return this.http.get('/getallfiles')
  } */


  /*   getImages(developer: Developer): Observable<any> {
      const url = `${environment.endpoint}/developer/avatars`
      return this.http
        .get(url, { responseType: ResponseContentType.Blob })
        .map((res: Response) => res.blob());
    }
   */

  private calculateLevel(xp: number, level: number): number {
    let step = 10;
    let xpForNextLevel = 0;

    for (let i = 1; i <= level; i++) {
      xpForNextLevel = xpForNextLevel + step;
    }

    //Termination condition: Level 200 or when XP is smaller than the required XP to the higher level
    if (level == 200 || (xp < xpForNextLevel)) {
      return level
    } else {
      return this.calculateLevel(xp, level + 1)
    }
  }

  private extractData(res: Response) {
    let body = res.json();
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
