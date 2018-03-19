import { Injectable } from '@angular/core';
import {World} from '../Interfaces/World';
import {environment} from '../../environments/environment';
import {Http, RequestOptions, Response, Headers} from '@angular/http';

@Injectable()
export class WorldService {

  public  worlds:       World[];
  private currentWorld: World;

  constructor(public http: Http) { 
    this.getWorlds()
  }

  getWorlds(): Promise<World[]> {
      return this.http.get(`${environment.endpoint}/world`)
        .toPromise()
        .then(response => {
          this.worlds = this.extractData(response);
          this.currentWorld= this.worlds[0];
          return this.worlds;
        })
        .catch(this.handleError);

  }
  
  getCurrentWorld(): World{
    return this.currentWorld
  }

  updateWorld(world: World): Promise<any>{
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    console.log(world)
    return this.http.put(`${environment.endpoint}/world/${world.id}`, world, options)
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
