import { ViewArtefactComponent } from './components/view-artefact/view-artefact.component';
import { ViewQuestComponent } from './components/view-quest/view-quest.component';
import { MatDialog } from '@angular/material';
import {EventDto} from '../../Interfaces/EventDto';
import {UserDto} from '../../Interfaces/UserDto';
import {Event} from '../../Interfaces/Event';
import {Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import {EventService} from '../../services/event.service';
import {WebsocketService} from 'app/services/websocket.service';
import {ReplaySubject, Subject} from 'rxjs';

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {

  private eventDtosSubject: Subject<EventDto[]> = new ReplaySubject(1); 
  public  eventDtos$ = this.eventDtosSubject.asObservable();
  private userDtosSubject:  Subject<UserDto[]>  = new ReplaySubject(1); 
  public  userDtos$  = this.userDtosSubject.asObservable(); 

  events: Event[];
  eventDtos: EventDto[];
  userDtos: UserDto[];
  previousEvent: EventDto = null;
  message = '';

  @ViewChildren('commentDiv') commentDivs: QueryList<ElementRef>;

  constructor(
    private eventService: EventService,
    private websocketService: WebsocketService,
    private dialog: MatDialog
  ) {
      this.eventService.eventDtos$.subscribe(eventDtos => { 
        this.eventDtos = eventDtos;
      });
      this.eventService.userDtos$.subscribe(userDtos => { 
        this.userDtos = userDtos;
      });
  }

  ngOnInit() {
  };

  ngAfterViewInit() {
    this.scrollToBottom();
    
    this.commentDivs.changes.subscribe(() => {
      this.scrollToBottom();
    });
  }

  scrollToBottom(){
    if (this.commentDivs && this.commentDivs.last) {
      if (this.commentDivs.last.nativeElement.children) {
        this.commentDivs.last.nativeElement.lastChild.style.cssText = ("padding-bottom: 50px");
        this.commentDivs.last.nativeElement.lastChild.focus();
        this.commentDivs.last.nativeElement.lastChild.style.cssText = ("padding-bottom: 0px")
      }
    }
  }

  checkNewDay(event: EventDto): Boolean {
    if (this.previousEvent == null) {
      this.previousEvent = event;
      return true;
    } else if (this.eventDtos[0].id === event.id) {
      // When this is the first Event in the List show Date
      this.previousEvent = event;
      return true;
    } else if ((new Date(this.previousEvent.timestamp).getDate() < new Date(event.timestamp).getDate()) || 
              (new Date(this.previousEvent.timestamp).getMonth() < new Date(event.timestamp).getMonth()) ||
              (new Date(this.previousEvent.timestamp).getFullYear() < new Date(event.timestamp).getFullYear())) {
      this.previousEvent = event;
      return true;
    } else {
      this.previousEvent = event;
      return false;
    }
  } 

  sendChat() {
    if (this.message != ""){
      this.websocketService.sendMessage(this.message)
    }
    this.message = '';
  }

  onKeyDown(event) {
    if (event.key === 'Enter') {
      this.sendChat();
    }
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
    });
    return name;
  }

  openView(e: EventDto){
    console.log(e)
    if (e.type == "QUEST"){
      this.viewQuest(e)
    } else if (e.type == "ARTEFACT"){
      this.viewArtefact(e)
    } else {
      console.log('No View for Event Type: ' + e.type)
    }
    
  }


  viewQuest(e: EventDto) {
      this.dialog.open(ViewQuestComponent, {
        panelClass: 'dialog-sexy',
        data: e,
        width: '500px'
      }).afterClosed().subscribe(() => {
      });
  }

  viewArtefact(e: EventDto){
    console.log(e)
    this.dialog.open(ViewArtefactComponent, {
      panelClass: 'dialog-sexy',
      data: e,
      width: '500px'
    }).afterClosed().subscribe(() => {
    });
  }
}
