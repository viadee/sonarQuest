import { Event } from './../../Interfaces/Event';
import { Component, OnInit } from '@angular/core';
import { EventService } from '../../services/event.service';
import { UserService } from '../../services/user.service';
import { ImageService } from 'app/services/image.service';

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {

  events: Event[]
  oldEvent: Event = null;
  message: string = '';

  constructor(
    private eventService: EventService,
    private userService: UserService,
    private imageService: ImageService
  ) { 
    this.eventService.events$.subscribe(events => {
      console.log(events)

      this.events = this.getImageForMessages(events)     
    });
  }

  ngOnInit() {
    
  }; 
  
  checkNewDay(event: Event): Boolean{
    if (this.oldEvent==null){
      this.oldEvent = event;
      return true;
    } else if (this.events[0].id == event.id){
      return true;
    } else if (new Date(this.oldEvent.timestamp).getDate() < new Date(event.timestamp).getDate()){
      return true;
    } else {
      this.oldEvent=event
      return false;
    }
  }

  getImageForMessages(events: Event[]): Event[]{
    events.forEach(event => {
        this.getImageForMessage(event)                                                        
    });
    return events;
  }

  getImageForMessage(event: Event): Event{
    if (event.type == "MESSAGE"){
      this.userService.getImageForUser(event.user).subscribe(blob => {
        this.imageService.createImageFromBlob2(blob).subscribe(image => {
          event.image = image
        });
      });
    } 
    return event;
  }

  sendChat(){
    this.eventService.sendChat(this.message).then(event => { 
      this.getImageForMessage(event)
      this.events.push(event)
    }).then(()=>{
      var i = document.getElementsByClassName('event').length;
      document.getElementsByClassName('event')[i-1].scrollIntoView(false)
    })
    this.message = "";

    
  }

  onKeyDown(event) {
    if (event.key === "Enter") {
      this.sendChat();
    }
  }

  cgetEvents(){
    //this.eventService.getEvents(); 
  }

}
