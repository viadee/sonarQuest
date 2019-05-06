import {Injectable, NgModule} from '@angular/core';
import {AvatarClass} from "../Interfaces/AvatarClass";
import {Role} from "../Interfaces/Role";
import {RoleService} from "./role.service";

@Injectable()
export class RoleServiceMock {

  public getRoles(): Promise<Role[]> {
    return new Promise<AvatarClass[]>( () => {});
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: RoleService, useClass: RoleServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class RoleServiceTestingModule {

}
