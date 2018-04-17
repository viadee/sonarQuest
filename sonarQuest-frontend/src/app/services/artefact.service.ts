import { Developer } from './../Interfaces/Developer.d';
import { RequestOptions, Http, Response, Headers } from '@angular/http';
import { ReplaySubject } from 'rxjs/ReplaySubject';
import { Subject } from 'rxjs/Subject';
import { Artefact } from './../Interfaces/Artefact';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment";

@Injectable()
export class ArtefactService {

  private artefactsSubject: Subject<Artefact[]> = new ReplaySubject(1);
  artefacts$ = this.artefactsSubject.asObservable();

  private artefactsforMarkteplaceSubject: Subject<Artefact[]> = new ReplaySubject(1);
  artefactsforMarkteplace$ = this.artefactsforMarkteplaceSubject.asObservable();

  constructor(public http: Http) { 
    this.getData()
  }

  getData():void{
    this.getArtefacts()
    this.getArtefactsforMarkteplace()
  }

  getArtefacts(): Observable<Artefact[]>{
    this.http.get(`${environment.endpoint}/artefact/`)
      .map(this.extractData)
      .subscribe(
        result => this.artefactsSubject.next(result),
        err    => this.artefactsSubject.error(err)
      ) 
    return this.artefactsSubject.asObservable();
  }

  getArtefactsforMarkteplace(): Observable<Artefact[]>{
    this.http.get(`${environment.endpoint}/artefact/forMarketplace/`)
      .map(this.extractData)
      .subscribe(
        result => this.artefactsforMarkteplaceSubject.next(result),
        err    => this.artefactsforMarkteplaceSubject.error(err)
      ) 
    return this.artefactsSubject.asObservable();
  }

  createArtefact(artefact: any): Promise<Artefact> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/artefact/`, artefact, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  updateArtefact(artefact: any): Promise<Artefact> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.put(`${environment.endpoint}/artefact/${artefact.id}`, artefact, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  buyArtefact(artefact: Artefact, developer: Developer): Promise<boolean> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.put(`${environment.endpoint}/artefact/${artefact.id}/boughtBy/${developer.id}`, options)
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

  setMinLevel(artefact: Artefact, min: number){
    artefact.minLevel.min = min
  }
}
