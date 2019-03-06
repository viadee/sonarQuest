import { Event } from './../../Interfaces/Event';
import { Component, OnInit } from '@angular/core';
import { EventService } from '../../services/event.service';
import { UserService } from '../../services/user.service';
import { ImageService } from 'app/services/image.service';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {

  events: Event[]
  oldEvent: Event = null;
  message: string = '';
  
  private serverUrl = 'http://localhost:8080/socket'
  private stompClient;
  //private stompClient: Stomp.Client;

  constructor(
    private eventService: EventService,
    private userService: UserService,
    private imageService: ImageService
  ) { 
    this.eventService.events$.subscribe(events => {
      this.events = this.getImageForMessages(events)     
    });


    this.initializeWebSocketConnection();
  }

  ngOnInit() {
    
  }; 
  
  checkNewDay(event: Event): Boolean{
    if (this.oldEvent==null){
      this.oldEvent = event;
      return true;
    } else if (this.events[0].id == event.id){
      this.oldEvent=event
      return true;
    } else if (new Date(this.oldEvent.timestamp).getDate() < new Date(event.timestamp).getDate()){
      this.oldEvent=event
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
    this.sendMessage(this.message)
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
    console.log(event )
    if (event.key === "Enter") {
      this.sendChat();
    }
  }

  initializeWebSocketConnection(){
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe("/chat", (message) => {
        alert(message.body)
        /*
        if(message.body) {
          $(".chat").append("<div class='message'>"+message.body+"</div>")
          console.log(message.body);
        }
        */
      });
    });
  }

  sendMessage(message){
    this.stompClient.send("/app/send/message" , {}, message);
    /*$('#input').val('');*/
  }
}
