import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../Interfaces/User';
import {World} from '../Interfaces/World';
import {environment} from '../../environments/environment';

@Injectable()
export class UserToWorldService {
  constructor(public http: HttpClient) {
  }

  public addUserToWorld(user: User, world: World): Promise<User> {
    return this.http.post<User>(`${environment.endpoint}/user_to_world/${user.id}/${world.id}`, null).toPromise();
  }

  public removeUserToWorld(user: User, world: World): Promise<User> {
    return this.http.delete<User>(`${environment.endpoint}/user_to_world/${user.id}/${world.id}`).toPromise();
  }
}
