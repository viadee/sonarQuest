import { Component, OnInit } from '@angular/core';
import { EventService } from '../../services/event.service';
import { Event } from '../../Interfaces/Event';

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {

  events: Event[]

  message: string = '';

  constructor(
    private eventService: EventService
  ) { 
    this.eventService.events$.subscribe(events => {
      this.events = events
    });    
  }

  ngOnInit() {
  }


  sendChat(){
    this.eventService.sendChat(this.message).then(event => { this.events.push(event) })
    this.message = "";
  }

  onKeyDown(event) {
    if (event.key === "Enter") {
      this.sendChat();
    }
  }

  getEvents(){
    this.eventService.getEvents(); 
  }

}
