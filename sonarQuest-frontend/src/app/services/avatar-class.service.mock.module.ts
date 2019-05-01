import {Injectable, NgModule} from '@angular/core';
import {AvatarClassService} from "./avatar-class.service";
import {AvatarClass} from "../Interfaces/AvatarClass";

@Injectable()
export class AvatarClassServiceMock {

  public getClasses(): Promise<AvatarClass[]> {
    return new Promise<AvatarClass[]>( () => {});
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: AvatarClassService, useClass: AvatarClassServiceMock }
  ],
  imports: [

  ],
  exports: [

  ]
})
export class AvatarClassServiceTestingModule {

}
