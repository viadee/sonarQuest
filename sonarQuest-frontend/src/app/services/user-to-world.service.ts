import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../Interfaces/User';
import { World } from '../Interfaces/World';
import { environment } from '../../environments/environment';
import { UserToWorld } from '../Interfaces/UserToWorld';
import { WorldService } from './world.service';

@Injectable()
export class UserToWorldService {

  constructor(
    private http: HttpClient,
    private worldService: WorldService) {
  }

  public updateUserToWorld(userToWorlds: UserToWorld[]): Promise<Boolean> {
    return this.http.put<Boolean>(`${environment.endpoint}/user_to_world/update`, userToWorlds).toPromise();
  }

  public getUserToWorlds(user: User): Promise<UserToWorld[]> {

    return this.worldService.getWorldsForUser(user.id).then(worlds => {
      let activeWorlds: World[];
      let userWorlds: World[] = worlds;
      let userWorldIds: number[];

      return this.worldService.getActiveWorlds().then(worlds => {
        activeWorlds = worlds;
        userWorldIds = userWorlds.map(userWorld => userWorld.id);

        return activeWorlds.map(world => <UserToWorld>{
          userId: user.id,
          worldId: world.id,
          joined: userWorldIds.includes(world.id),
          worldName: world.name
        });
      });

    })

  }
}
