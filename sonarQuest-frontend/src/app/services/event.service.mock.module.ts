import {Injectable, NgModule} from '@angular/core';
import {EventService} from "./event.service";
import {Observable} from "rxjs";
import {Event} from "../Interfaces/Event";
import {EventDto} from "../Interfaces/EventDto";
import {UserDto} from "../Interfaces/UserDto";
import {EventUserDto} from "../Interfaces/EventUserDto";

@Injectable()
export class EventServiceMock {

  public  events$ = new Observable<Event[]>();
  public  eventUserDto$ = new Observable<EventUserDto>();
  public  eventDtos$ = new Observable<EventDto[]>();
  public  userDtos$  = new Observable<UserDto[]>();

  getEventsOfCurrentWorld(): Observable<Event[]>{
    return new Observable<Event[]>();
  }

  public addEvent(event){}


  sendChat(message: string): Promise<Event> {
    return new Promise<Event>(() => {});
  }

  something() {}

  getImageForMessages(events: Event[]): Event[]{
    return events;
  }

  getImageForMessage(event: Event): Event{
    return event;
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: EventService, useClass: EventServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class EventServiceTestingModule {

}
