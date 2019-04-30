import {Injectable, NgModule} from '@angular/core';
import {Observable, of} from 'rxjs';
import {UserService} from "./user.service";
import {User} from "../Interfaces/User";

@Injectable()
export class UserServiceMock {

  private user: User = {
    username: "Exmaple",
    mail: "mister.example@provider.com",
    role: {
      name: "someRole"
    },
    aboutMe: "I'm Mister Example.",
    artefacts: []
  };

  user$ = of(this.user);

  public onUserChange(): Observable<boolean> {
    return new Observable<boolean>();
  }

  public loadUser(): void {}

  public getUser(): User {
    return this.user;
  }

  public getUsers(): Observable<User[]> {
    const users = [this.user];
    return of(users);
  }

  public getImage(): Observable<Blob> {
    return new Observable<Blob>();
  }

  public getImageForUser(user: User): Observable<Blob> {
    return new Observable<Blob>();
  }

  public updateUser(user: User): Promise<User> {
    return new Promise(() => {});
  }

  public deleteUser(user: User): Promise<any> {
    return new Promise(() => {});
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: UserService, useClass: UserServiceMock }
  ],
  imports: [

  ],
  exports: [

  ]
})
export class UserServiceTestingModule {

}
