import { Event } from '../../Interfaces/Event';
import { ViewChildren, QueryList, ElementRef, Component, OnInit } from '@angular/core';
import { EventService } from '../../services/event.service';
import { WebsocketService } from 'app/services/websocket.service';
import * as $ from "jquery";

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {

  events: Event[];
  previousEvent: Event = null;
  message = '';

  @ViewChildren('commentDiv') commentDivs: QueryList<ElementRef>;

  constructor(
    private eventService: EventService,
    private websocketService: WebsocketService
  ) {
    this.eventService.events$.subscribe(events => {
      this.events = this.eventService.getImageForMessages(events)
    });
  }

  ngOnInit() {
  };

  ngAfterViewInit() {
    this.commentDivs.changes.subscribe(() => {
          $(".event").last().css("padding-bottom", "50px")
          $(".event").last().focus()
          $(".event").last().css("padding-bottom", "0")

    });
  }

  checkNewDay(event: Event): Boolean {
    if (this.previousEvent == null) {
      this.previousEvent = event;
      return true;
    } else if (this.events[0].id === event.id) {
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
    console.log('CLICK');
    this.websocketService.initializeWebSocketConnection();
  }

  something() {
    this.eventService.something();
  }

}
