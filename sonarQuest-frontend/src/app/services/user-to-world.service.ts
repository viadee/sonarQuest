import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../Interfaces/User';
import {World} from '../Interfaces/World';
import {environment} from '../../environments/environment';
import {UserToWorld} from '../Interfaces/UserToWorld';
import {WorldService} from './world.service';

@Injectable()
export class UserToWorldService {
  constructor(private http: HttpClient,
              private worldService: WorldService) {
  }

  private addUserToWorld(userId: number, worldId: number): Promise<User> {
    return this.http.post<User>(`${environment.endpoint}/user_to_world/${userId}/${worldId}`, null).toPromise();
  }

  private removeUserToWorld(userId: number, worldId: number): Promise<User> {
    return this.http.delete<User>(`${environment.endpoint}/user_to_world/${userId}/${worldId}`).toPromise();
  }

  /*public saveUserToWorlds(userToWorlds: UserToWorld[]) {
    this.updateUserToWorld(userToWorlds)
  }*/

  public updateUserToWorld(userToWorlds: UserToWorld[]): Promise<Boolean> {
    return this.http.put<Boolean>(`${environment.endpoint}/user_to_world/update`, userToWorlds).toPromise();
  }

  public getUserToWorlds(user: User): Promise<UserToWorld[]> {
    let activeWorlds: World[];
    let userWorlds: World[];

    return this.worldService.getActiveWorlds().then(worlds => {
      activeWorlds = worlds;
      this.worldService.worlds$.subscribe(worlds => userWorlds = worlds)
      return userWorlds;
    }).then(userWorlds => {
      const userWorldIds: number[] = userWorlds.map(userWorld => userWorld.id);
      return activeWorlds.map(world => <UserToWorld>{
        userId: user.id,
        worldId: world.id,
        joined: userWorldIds.includes(world.id),
        worldName: world.name
      });
    });
  }
}
