import {Injectable, NgModule} from '@angular/core';
import {PermissionService} from "./permission.service";

@Injectable()
export class PermissionServiceMock {

  public loadPermissions(): Promise<any> {
    return new Promise<any>( () => {});
  }

  public isUrlPermitted(url: string): boolean | Promise<boolean> {
    return true;
  }

  public isUrlVisible(url: string): boolean {
    return true;
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: PermissionService, useClass: PermissionServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class PermissionServiceTestingModule {

}
