import { Event } from './../../Interfaces/Event';
import { Component, OnInit } from '@angular/core';
import { EventService } from '../../services/event.service';
import { UserService } from '../../services/user.service';
import { ImageService } from 'app/services/image.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {

  events: Event[]
  message: string = '';

  constructor(
    private eventService: EventService,
    private userService: UserService,
    private imageService: ImageService
  ) { 
    this.eventService.events$.subscribe(events => {
      this.events = this.getImageForMessages(events)     
    });
  }

  ngOnInit() {
    
  }; 
  

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
      console.log(document.getElementsByClassName('event')[i-1].getElementsByClassName('text')[0].textContent)
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


class UserImageMap<Observable> {
  private userImages: { [key: number]: Observable };

  constructor() {
      this.userImages = {};
  }

  add(key: number, value: Observable): void {
      this.userImages[key] = value;
  }

  has(key: number): boolean {
      return key in this.userImages;
  }

  get(key: number): Observable {
      return this.userImages[key];
  }
}