import {Injectable, NgModule} from '@angular/core';
import {UserToWorldService} from "./user-to-world.service";
import {UserToWorld} from "../Interfaces/UserToWorld";
import {User} from "../Interfaces/User";

@Injectable()
export class UserToWorldServiceMock {

  public updateUserToWorld(userToWorlds: UserToWorld[]): Promise<Boolean> {
    return new Promise<Boolean>(() => {});
  }

  public getUserToWorlds(user: User): Promise<UserToWorld[]> {
    return new Promise<UserToWorld[]>(() => {});
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: UserToWorldService, useClass: UserToWorldServiceMock }
  ],
  imports: [

  ],
  exports: [

  ]
})
export class UserToWorldServiceTestingModule {

}
