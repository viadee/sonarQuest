import {Injectable, NgModule} from '@angular/core';
import {Observable} from 'rxjs';
import {AuthenticationService} from "./authentication.service";

@Injectable()
export class AuthenticationServiceMock {

  public login(username, password): void {}

  public logout(): void {}

  public isLoggedIn(): boolean {
    return true;
  }

  public onLoginLogout(): Observable<boolean> {
    return new Observable<boolean>();
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: AuthenticationService, useClass: AuthenticationServiceMock }
  ],
  imports: [

  ],
  exports: [

  ]
})
export class AuthenticationTestingModule {

}
