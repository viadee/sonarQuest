import { UserService } from './user.service';
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
  user: User;

  
  messages: Subject<any>;

  constructor(
    public http: HttpClient,
    public worldService: WorldService,
    public userService: UserService
    ) {
      this.eventsSubject = new Subject;
      this.events$ = this.eventsSubject.asObservable();

      this.currentWorld = worldService.getCurrentWorld(); 

      this.userService.user$.subscribe(user =>{ this.user = user })
  }

   sendMsg(msg) {
    this.messages.next(msg);
  }



  getEvents(): Observable<Event[]>{
    //this.http.get(`${environment.endpoint}/event/world/${this.currentWorld.id}`)
    this.http.get(`${environment.endpoint}/event/world/1`)
      .subscribe(
        result => this.eventsSubject.next(result),
        err    => this.eventsSubject.error(err)
      ) 
    return this.eventsSubject;
  }
  

  sendChat(message: string): Promise<Event> {
    return this.http.post<Event>(`${environment.endpoint}/event/world/1/sendChat/${this.user.id}`, message)
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
