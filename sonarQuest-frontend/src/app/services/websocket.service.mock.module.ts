import {Injectable, NgModule} from '@angular/core';
import {WebsocketService} from "./websocket.service";

@Injectable()
export class WebsocketServiceMock {

  initializeWebSocketConnection() {}

  sendMessage(message) {}

  closeWebSocket() {}

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: WebsocketService, useClass: WebsocketServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class WebsocketServiceTestingModule {

}
