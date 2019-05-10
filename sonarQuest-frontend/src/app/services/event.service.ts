import { UserDto } from './../Interfaces/UserDto';
import { EventDto } from './../Interfaces/EventDto';
import { EventUserDto } from './../Interfaces/EventUserDto';
import {ImageService} from 'app/services/image.service';
import {UserService} from './user.service';
import {User} from '../Interfaces/User';
import {WorldService} from './world.service';
import {World} from '../Interfaces/World';
import {Observable} from 'rxjs/Observable';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Subject} from 'rxjs/Subject';
import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {environment} from "../../environments/environment";
import {Event} from '../Interfaces/Event';
import {HttpClient} from '@angular/common/http';


@Injectable()
export class EventService {

  private eventUserDtoSubject: Subject<EventUserDto> = new ReplaySubject(1); 
  public  eventUserDto$ = this.eventUserDtoSubject.asObservable();
  
  private eventDtosSubject: Subject<EventDto[]> = new ReplaySubject(1); 
  public  eventDtos$ = this.eventDtosSubject.asObservable();
  private userDtosSubject:  Subject<UserDto[]>  = new ReplaySubject(1); 
  public  userDtos$  = this.userDtosSubject.asObservable(); 

  
  eventDtos: EventDto[] = [];
  userDtos:  UserDto[]  = [];


  currentWorld: World;
  user: User;
  messages: Subject<any>;
  events: Event[];
  loadPicturesforEvents: Event[] = [];


  constructor(
    public http: HttpClient,
    public worldService: WorldService,
    public userService: UserService,
    public imageService: ImageService
    ) {
      worldService.currentWorld$.subscribe(world=> {  
        this.currentWorld = world;
        this.getEventsForCurrentWorldEfficient();
      });

      this.subscribeEventUserDto()

      userService.user$.subscribe(user =>{ this.user = user });

  }

  subscribeEventUserDto(){
    this.eventUserDto$.subscribe((eventUserDto: EventUserDto) => {
      this.eventUserDtoToData(eventUserDto)
    })
  }

  eventUserDtoToData(eventUserDto: EventUserDto){

      var localEventDtos: EventDto[] = eventUserDto.eventDtos;
      var localUserDtos: UserDto[]   = eventUserDto.userDtos;
            localUserDtos.forEach((userDto: UserDto) => {
                
              this.userService.getImageForUserId(userDto.id).subscribe((blob) => {
                this.imageService.createImageFromBlob2(blob).subscribe(image => {
                  userDto.picture = image
                  
                  localEventDtos.forEach((eventDto: EventDto) => {
                    if (eventDto.userId == userDto.id && eventDto.type == 'MESSAGE'){
                      eventDto.image = userDto.picture
                    }
                  });
                });
              });  
            });

            localEventDtos.forEach(eDto => this.eventDtos.push(eDto))
            localUserDtos.forEach(uDto => this.userDtos.push(uDto))

      this.eventDtosSubject.next(this.eventDtos)
      this.userDtosSubject.next(this.userDtos)

    
  }

  /*
  getEventsOfCurrentWorld(): Observable<Event[]>{
    this.http.get<Event[]>(`${environment.endpoint}/event/currentWorld`).subscribe(
        result => this.eventsSubject.next(result),
        err    => this.eventsSubject.error(err)
      );
    return this.eventsSubject;
  }
  */

  getEventsForCurrentWorldEfficient(): Observable<EventUserDto>{
    this.http.get<EventUserDto>(`${environment.endpoint}/event/getEventsForCurrentWorldEfficient`).subscribe(
        result => {
          this.eventUserDtoSubject.next(result)
          console.log(result)
        },
        err    => this.eventUserDtoSubject.error(err)
      );
    return this.eventUserDtoSubject;
  }

  public addEvent(eventUserDto: EventUserDto){
    this.eventUserDtoToData(eventUserDto);
  }


  

  sendChat(message: string): Promise<Event> {
    
    return this.http.post<Event>(`${environment.endpoint}/event/sendChat`, message)
      .toPromise()
      .catch(this.handleError);
  }

  something() {
    var message = "Sender";
    this.http.post<string>(`${environment.endpoint}/event/something`, message).subscribe(s => {
      console.log(s)
    })
  }

  /*
  getImageForMessages(events: Event[]): Event[]{
    events.forEach((event: Event) => {
      if(!this.loadPicturesforEvents.includes(event)){
        this.loadPicturesforEvents.push(event)
      }                                                    
    });

    this.loadPicturesforEvents.forEach(event => {
        event.image = this.getImageForMessage(event)                                                        
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
  */

  
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
