import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../Interfaces/User';
import {World} from '../Interfaces/World';
import {environment} from '../../environments/environment';
import {UserToWorld} from '../Interfaces/UserToWorld';
import {WorldService} from './world.service';

@Injectable()
export class UserToWorldService {
  
  userWorlds: World[];

  constructor(
    private http: HttpClient,
    private worldService: WorldService) 
  {
    this.worldService.worlds$.subscribe(worlds => {
      this.userWorlds = worlds;
    })
  }

  public updateUserToWorld(userToWorlds: UserToWorld[]): Promise<Boolean> {
    return this.http.put<Boolean>(`${environment.endpoint}/user_to_world/update`, userToWorlds).toPromise();
  }

  public getUserToWorlds(user: User): Promise<UserToWorld[]> {

    let activeWorlds: World[];
    let userWorlds:   World[] = this.userWorlds;
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

    
  }
}
