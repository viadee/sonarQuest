import { Token } from './../login/Token';
import { LocalStorageService } from './../login/local-storage.service';
import { AuthenticationService } from './../login/authentication.service';
import { environment } from './../../environments/environment';
import * as SockJS from 'sockjs-client';
import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import {HttpClient} from '@angular/common/http';


@Injectable()
export class WebSocketService {

  private serverUrl = 'http://localhost:8080/socket'
  private stompClient;

  constructor( private httpClient: HttpClient
    ) {
      this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection(){
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    that.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe("/chat", (message) => {
        if(message.body) {
          //$(".chat").append("<div class='message'>"+message.body+"</div>")
          console.log(message.body);
        }
      });
    });
  }

  

  
  sendMessage(message){
    this.a(message);
    this.b(message)
  }
  
  a(message){
    console.log("a")
    this.stompClient.send("/app/send/message" , {}, message);
  }

  public b(message) {
    console.log("b")
    const url = `${environment.endpoint}/app/send/message`;
    this.httpClient.post<any>(url, message);
  }
}




