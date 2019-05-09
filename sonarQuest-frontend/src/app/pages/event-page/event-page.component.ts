import { UserService } from 'app/services/user.service';
import { ImageService } from 'app/services/image.service';
import { EventDto } from './../../Interfaces/EventDto';
import { UserDto } from './../../Interfaces/UserDto';
import { EventUserDto } from './../../Interfaces/EventUserDto';
import { Event } from '../../Interfaces/Event';
import { ViewChildren, QueryList, ElementRef, Component, OnInit } from '@angular/core';
import { EventService } from '../../services/event.service';
import { WebsocketService } from 'app/services/websocket.service';
import { Subject, ReplaySubject, Subscription } from 'rxjs';
import { domRendererFactory3 } from '@angular/core/src/render3/interfaces/renderer';

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {
  private subscribtion: Subscription = new Subscription();

  private eventDtosSubject: Subject<EventDto[]> = new ReplaySubject(1); 
  public  eventDtos$ = this.eventDtosSubject.asObservable();
  private userDtosSubject:  Subject<UserDto[]>  = new ReplaySubject(1); 
  public  userDtos$  = this.userDtosSubject.asObservable(); 

  events: Event[];
  eventDtos: EventDto[];
  userDtos: UserDto[];
  previousEvent: Event = null;
  message = '';

  @ViewChildren('commentDiv') commentDivs: QueryList<ElementRef>;
  @ViewChildren('test') testDivs: QueryList<ElementRef>;

  constructor(

    private eventService: EventService,
    private websocketService: WebsocketService,
    private imageService: ImageService,
    private userService: UserService

  ) {
      this.eventService.eventDtos$.subscribe(eventDtos => { 
        this.eventDtos = eventDtos
      })
      this.eventService.userDtos$.subscribe(userDtos => { 
        this.userDtos = userDtos
      })
  }

  ngOnInit() {

  };

  ngAfterViewInit() {
    
    this.commentDivs.changes.subscribe(() => {
      if (this.commentDivs && this.commentDivs.last) {
        //this.commentDivs.last.nativeElement.focus();
        if (this.commentDivs.last.nativeElement.children) {
          console.log(this.commentDivs.last.nativeElement);
          console.log(this.commentDivs.last.nativeElement.lastChild);
          this.commentDivs.last.nativeElement.lastChild.focus();
        }
      }
    })
  }


  checkNewDay(event: Event): Boolean {
    if (this.previousEvent == null) {
      this.previousEvent = event;
      return true;
    } else if (this.eventDtos[0].id === event.id) {
      // When this is the first Event in the List show Date
      this.previousEvent = event
      return true;
    } else if ((new Date(this.previousEvent.timestamp).getDate() < new Date(event.timestamp).getDate()) || 
              (new Date(this.previousEvent.timestamp).getMonth() < new Date(event.timestamp).getMonth()) ||
              (new Date(this.previousEvent.timestamp).getFullYear() < new Date(event.timestamp).getFullYear())) {
      this.previousEvent = event
      return true;
    } else {
      this.previousEvent = event;
      return false;
    }
  }

  sendChat() {
    if (this.message != ""){
      this.websocketService.sendMessage(this.message)
      /*
      this.eventService.sendChat(this.message).then(event => {
        this.getImageForMessage(event)
        this.events.push(event)
      }).then(()=>{
        //var i = document.getElementsByClassName('event').length;
        //document.getElementsByClassName('event')[i-1].scrollIntoView(false)
      })
      */
    }
    this.message = '';

  }

  onKeyDown(event) {
    if (event.key === 'Enter') {
      this.sendChat();
    }
  }

  click() {
    this.websocketService.initializeWebSocketConnection();
  }

  something() {
    this.eventService.something();
  }

  getUserName(e: EventDto): String{
    var name: String = "null";

    this.userDtos.forEach((userDto: UserDto) => {
      if ( e.userId == userDto.id) {
        name = userDto.username;
      }
    })
    return name
  }

}
