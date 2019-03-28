import { ImageService } from 'app/services/image.service';
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

  private eventsSubject: Subject<Event[]> = new ReplaySubject(1); 
  public  events$ = this.eventsSubject.asObservable();

  /*
  private eventSubject: Subject<Event> = new Subject; 
  public  event$ = this.eventSubject.asObservable();
  */

  currentWorld: World;
  user: User;
  messages: Subject<any>;
  events: Event[];


  constructor(
    public http: HttpClient,
    public worldService: WorldService,
    public userService: UserService,
    public imageService: ImageService
    ) {
      worldService.currentWorld$.subscribe(world=> {  
        this.currentWorld = world
        this.getEventsOfCurrentWorld()
      });

      userService.user$.subscribe(user =>{ this.user = user })


      this.events$.subscribe(events => {
        this.events = events;
      })
  }

  getEventsOfCurrentWorld(): Observable<Event[]>{
    this.http.get<Event[]>(`${environment.endpoint}/event/currentWorld`).subscribe(
        result => this.eventsSubject.next(result),
        err    => this.eventsSubject.error(err)
      ) 
    return this.eventsSubject;
  }

  public addEvent(event){
    event = this.getImageForMessage(event)  
    this.events.push(event)

    this.eventsSubject.next(this.events)
  }
  

  sendChat(message: string): Promise<Event> {
    
    return this.http.post<Event>(`${environment.endpoint}/event/sendChat`, message)
      .toPromise()
      .catch(this.handleError);
  }

  something() {
    console.log("something()")
    var message = "Sender"
    this.http.post<string>(`${environment.endpoint}/event/something`, message).subscribe(s => {
      console.log(s)
    })
  }
  /*
  getEvents(){
    return this.events
  }
  */

  getImageForMessages(events: Event[]): Event[]{
    events.forEach(event => {
        this.getImageForMessage(event)                                                        
    });
    return events;
  }

  getImageForMessage(event: Event): Event{
    if (event.type == "MESSAGE" && (typeof event.image === "string")){
      this.userService.getImageForUser(event.user).subscribe(blob => {
        this.imageService.createImageFromBlob2(blob).subscribe(image => {
          event.image = image
        });
      });
    } 
    return event;
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
