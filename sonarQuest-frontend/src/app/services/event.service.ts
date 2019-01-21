import { User } from './../Interfaces/User';
import { WorldService } from './world.service';
import { World } from './../Interfaces/World';
import { Observable } from 'rxjs/Observable';
import { ReplaySubject } from 'rxjs/ReplaySubject';
import { Subject } from 'rxjs/Subject';
import { Injectable } from '@angular/core';
import { RequestOptions, Response, Headers } from '@angular/http';
import { environment } from "../../environments/environment";
import { Event } from './../Interfaces/Event';
import {HttpClient} from '@angular/common/http';


@Injectable()
export class EventService {
  private eventsSubject;
  public  events$;


  currentWorld: World;
  avatar: User;

  
  messages: Subject<any>;

  constructor(
    public http: HttpClient,
    public worldService: WorldService
    ) {
      this.eventsSubject = new Subject;
      this.events$ = this.eventsSubject.asObservable();

      this.currentWorld = worldService.getCurrentWorld(); 
  }

   sendMsg(msg) {
    this.messages.next(msg);
  }



  getEvents(): Observable<Event[]>{
    //this.http.get(`${environment.endpoint}/event/world/${this.currentWorld.id}`)
    console.log("getEvents()")
    this.http.get(`${environment.endpoint}/event/world/1`)
      .subscribe(
        result => this.eventsSubject.next(result),
        err    => this.eventsSubject.error(err)
      ) 
      console.log(this.events$);
    return this.eventsSubject;
  }

  

  sendChat(message: string): Promise<any> {
    //return this.http.post(`${environment.endpoint}/event/world/${this.currentWorld.id}/sendChat/${this.avatar.id}`, message)
    return this.http.post(`${environment.endpoint}/event/world/1/sendChat/1`, message)
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
