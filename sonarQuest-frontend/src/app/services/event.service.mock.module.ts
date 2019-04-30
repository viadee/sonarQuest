import {Injectable, NgModule} from '@angular/core';
import {EventService} from "./event.service";
import {Observable} from "rxjs";
import {Event} from "../Interfaces/Event";

@Injectable()
export class EventServiceMock {

  public  events$ = new Observable<Event[]>();

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
