import { UserDto } from './../Interfaces/UserDto';
import { EventDto } from './../Interfaces/EventDto';
import { EventUserDto } from './../Interfaces/EventUserDto';
import { ImageService } from 'app/services/image.service';
import { UserService } from './user.service';
import { User } from '../Interfaces/User';
import { WorldService } from './world.service';
import { World } from '../Interfaces/World';
import { Observable } from 'rxjs/Observable';
import { ReplaySubject } from 'rxjs/ReplaySubject';
import { Subject } from 'rxjs/Subject';
import { Injectable, OnChanges, SimpleChanges } from '@angular/core';
import { Response } from '@angular/http';
import { environment } from "../../environments/environment";
import { Event } from '../Interfaces/Event';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { RoutingUrls } from 'app/app-routing/routing-urls';


@Injectable()
export class EventService implements OnChanges {


  private eventUserDtoSubject: Subject<EventUserDto> = new ReplaySubject(1);
  public eventUserDto$ = this.eventUserDtoSubject.asObservable();

  private eventDtosSubject: Subject<EventDto[]> = new ReplaySubject(1);
  public eventDtos$ = this.eventDtosSubject.asObservable();
  private userDtosSubject: Subject<UserDto[]> = new ReplaySubject(1);
  public userDtos$ = this.userDtosSubject.asObservable();
  private unseenEventsSubject: Subject<boolean> = new ReplaySubject(1);
  public unseenEvents$ = this.unseenEventsSubject.asObservable();
  eventDtos: EventDto[] = [];
  userDtos: UserDto[] = [];


  currentWorld: World;
  user: User;
  messages: Subject<any>;
  events: Event[];
  loadPicturesforEvents: Event[] = [];


  constructor(
    public http: HttpClient,
    public worldService: WorldService,
    public userService: UserService,
    public imageService: ImageService,
    public router: Router
  ) {
    worldService.currentWorld$.subscribe(world => {
      this.currentWorld = world;
      this.getEventsForCurrentWorldEfficient();
    });

    this.subscribeEventUserDto()

    userService.user$.subscribe(user => { this.user = user });

  }
  ngOnChanges(changes: SimpleChanges): void {
    console.log('Change');
    console.log(changes);
  }
  subscribeEventUserDto() {
    this.eventUserDto$.subscribe((eventUserDto: EventUserDto) => {
      this.eventUserDtosToData(eventUserDto)
    })
  }

  eventUserDtosToData(eventUserDto: EventUserDto) {

    var localEventDtos: EventDto[] = eventUserDto.eventDtos;
    var localUserDtos: UserDto[] = eventUserDto.userDtos;
    localUserDtos.forEach((userDto: UserDto) => {

      this.userService.getImageForUserId(userDto.id).subscribe((blob) => {
        this.imageService.createImageFromBlob2(blob).subscribe(image => {
          userDto.picture = image

          localEventDtos.forEach((eventDto: EventDto) => {
            if (eventDto.userId === userDto.id) {
              if (eventDto.type === 'MESSAGE' || eventDto.type === 'USER_SKILL') {

              }
              eventDto.image = userDto.picture
            }
          });
        });
      });
    });

    this.eventDtos = [];
    this.userDtos = [];

    localEventDtos.forEach(eDto => this.eventDtos.push(eDto))
    localUserDtos.forEach(uDto => this.userDtos.push(uDto))

    this.eventDtosSubject.next(this.eventDtos)
    this.userDtosSubject.next(this.userDtos)
  }

  addEventUserDtoToData(eventUserDto: EventUserDto) {

    var localEventDtos: EventDto[] = eventUserDto.eventDtos;
    var localUserDtos: UserDto[] = eventUserDto.userDtos;

    var eventDto: EventDto = this.eventDtos.filter(eDto => ((eDto.type == localEventDtos[0].type) &&
      ((eDto.type != "MESSAGE" && eDto.title == localEventDtos[0].title) || (eDto.type == "MESSAGE" && eDto.userId == localEventDtos[0].userId))))[0]

    if (eventDto != null) {
      console.log(eventUserDto.eventDtos[0])
      console.log(eventDto)
      eventUserDto.eventDtos[0].image = eventDto.image
      console.log(localEventDtos[0].type)
    } else if (localEventDtos[0].type === "MESSAGE" || localEventDtos[0].type === "USER_SKILL") {
      localUserDtos.forEach((userDto: UserDto) => {

        this.userService.getImageForUserId(userDto.id).subscribe((blob) => {
          this.imageService.createImageFromBlob2(blob).subscribe(image => {
            userDto.picture = image

            localEventDtos.forEach((eventDto: EventDto) => {
              if (eventDto.userId === userDto.id) {
                if (eventDto.type === 'USER_SKILL' || eventDto.type === 'MESSAGE') {
                  eventDto.image = userDto.picture
                }
              }
            });
          });
        });
      });
    }

    localEventDtos.forEach(eDto => this.eventDtos.push(eDto))
    localUserDtos.forEach(uDto => this.userDtos.push(uDto))

    this.eventDtosSubject.next(this.eventDtos)
    this.userDtosSubject.next(this.userDtos)
    this.checkForUnseenEvents();
  }

  getEventsForCurrentWorldEfficient(): Observable<EventUserDto> {
    this.http.get<EventUserDto>(`${environment.endpoint}/event/getEventsForCurrentWorldEfficient`).subscribe(
      result => {
        this.eventUserDtoSubject.next(result)
        console.log(result)
      },
      err => this.eventUserDtoSubject.error(err)
    );
    return this.eventUserDtoSubject;
  }

  public addEvent(eventUserDto: EventUserDto) {
    this.addEventUserDtoToData(eventUserDto);
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

  public checkForUnseenEvents(): Observable<boolean> {
    const currentUrl = this.router.url.substring(1);
    if (currentUrl !== RoutingUrls.events) {
      this.http.get<boolean>(`${environment.endpoint}/event/checkForUnseenEvents`).subscribe(result => this.unseenEventsSubject.next(result));
      return this.unseenEventsSubject.asObservable();
    }
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
