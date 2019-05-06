import {Injectable, NgModule} from '@angular/core';
import {AvatarClass} from "../Interfaces/AvatarClass";
import {AvatarRaceService} from "./avatar-race.service";

@Injectable()
export class AvatarRaceServiceMock {

  public getRaces(): Promise<AvatarClass[]> {
    return new Promise<AvatarClass[]>( () => {});
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: AvatarRaceService, useClass: AvatarRaceServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class AvatarRaceServiceTestingModule {

}
