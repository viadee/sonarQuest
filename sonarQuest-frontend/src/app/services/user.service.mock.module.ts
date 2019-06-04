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
    level: {
      id: 1,
      minXp: 5,
      maxXp: 10,
      levelNumber: 2
    },
    aboutMe: "I'm Mister Example.",
    artefacts: [
      {
        id: 1,
        name: "some Artefact",
        icon: "PATH/TO/ICON",
        price: 100,
        quantity: 2,
        description: "Artefact description",
        minLevel: null,
        skills: [{
          id: 1,
          name: "Cool skill",
          type: "magic",
          value: 123,
          avatarClasses: []
        }]
      }
    ]
  };

  user$ = of(this.user);
  avatar$ = new Observable<any>();

  public onUserChange(): Observable<boolean> {
    return new Observable<boolean>();
  }

  public loadUser(): void {
  }

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
    return new Promise(() => {
    });
  }

  public deleteUser(user: User): Promise<any> {
    return new Promise(() => {
    });
  }

}

@NgModule({
  declarations: [],
  providers: [
    {provide: UserService, useClass: UserServiceMock}
  ],
  imports: [],
  exports: []
})
export class UserServiceTestingModule {

}
