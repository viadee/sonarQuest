import { World } from './../Interfaces/World';
import { WorldService } from './world.service';
import { EventService } from 'app/services/event.service';
import { UserService } from 'app/services/user.service';
import { Message } from './../Interfaces/Message';
import { Event } from './../Interfaces/Event';
import * as SockJS from 'sockjs-client';
import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import { User } from 'app/Interfaces/User';


@Injectable()
export class WebSocketService {

  private serverUrl = 'http://localhost:8080/socket'
  private stompClient;
  private user: User;
  private currentWorld: World;
  private chatStomp = null;

  constructor
  ( public userService:  UserService,
    public eventService: EventService,
    public worldService: WorldService
  ) {
      userService.user$.subscribe(user => { this.user = user })
      worldService.currentWorld$.subscribe(currentWorld => { this.currentWorld = currentWorld })
      this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    this.closeWebSocket();

    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    this.stompClient.connect({}, function(frame) {
      this.chatStomp = that.stompClient.subscribe('/chat', message => {
        const event: Event = JSON.parse(message.body);
        that.eventService.addEvent(event);
      });
    });
  }

  sendMessage(message) {
    const messageDto: Message = {
      message: message,
      userId: this.user.id
    };

    this.stompClient.send('/app/send/message' , {}, JSON.stringify(messageDto));
  }

  closeWebSocket() {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  }
}




