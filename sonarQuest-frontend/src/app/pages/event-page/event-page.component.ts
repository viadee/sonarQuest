import { Event } from './../../Interfaces/Event';
import { Component, OnInit } from '@angular/core';
import { EventService } from '../../services/event.service';
import { UserService } from '../../services/user.service';
import { ImageService } from 'app/services/image.service';
import { WebSocketService } from 'app/services/websocket.service';

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {

  events: Event[]
  previousEvent: Event = null;
  message: string = '';

  constructor(
    private eventService: EventService,
    private userService: UserService,
    private imageService: ImageService,
    private wsSerive: WebSocketService
  ) { 
    this.eventService.events$.subscribe(events => {
      this.events = this.eventService.getImageForMessages(events)
    });
  }

  ngOnInit() {
    
  }; 
  
  checkNewDay(event: Event): Boolean{
    if (this.previousEvent==null){
      this.previousEvent = event;
      return true;
    } else if (this.events[0].id == event.id){
      this.previousEvent=event
      return true;
    } else if (new Date(this.previousEvent.timestamp).getDate() < new Date(event.timestamp).getDate()){
      this.previousEvent=event
      return true;
    } else {
      this.previousEvent=event
      return false;
    }
  }

  sendChat(){
    this.wsSerive.sendMessage(this.message)
    /*
    this.eventService.sendChat(this.message).then(event => { 
      this.getImageForMessage(event)
      this.events.push(event)
    }).then(()=>{
      //var i = document.getElementsByClassName('event').length;
      //document.getElementsByClassName('event')[i-1].scrollIntoView(false)
    })
    */
    this.message = "";

    
  }

  onKeyDown(event) {
    if (event.key === "Enter") {
      this.sendChat();
    }
  }

  click(){
    console.log('CLICK')
    this.wsSerive.initializeWebSocketConnection()
  }

  something(){
    this.eventService.something()
  }

}
