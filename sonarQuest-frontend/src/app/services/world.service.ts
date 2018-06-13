import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {World} from '../Interfaces/World';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Subscriber} from 'rxjs/Subscriber';
import {UserService} from './user.service';

@Injectable()
export class WorldService {

  world: World = null;
  wordChangeListener: Subscriber<boolean>[] = [];

  constructor(private http: HttpClient, private userService: UserService) {
    userService.onUserChange().subscribe(() => {
      if (userService.getUser()) {
        this.loadWorld();
      }
    });
  }

  public onWorldChange(): Observable<boolean> {
    return new Observable<boolean>(observer => {
      this.wordChangeListener.push(observer);
    });
  }

  private worldChanged(): void {
    this.wordChangeListener.forEach(l => l.next(true));
  }

  public getWorlds(): Observable<World[]> {
    return this.http.get<World[]>(`${environment.endpoint}/world/worlds`);
  }

  public loadWorld(): void {
    this.http.get<World>(`${environment.endpoint}/world/current`)
      .subscribe(world => {
        this.world = world;
        this.worldChanged();
      });
  }

  public getCurrentWorld(): World {
    return this.world;
  }

  public setCurrentWorld(world: World): Promise<World> {
    return this.http.post<World>(`${environment.endpoint}/world/current`, world).toPromise();
  }

  updateWorld(world: World): Promise<World> {
    return this.http.post<World>(`${environment.endpoint}/world/world`, world).toPromise();
  }

  updateBackground(world: World, image: string): Promise<World> {
    return this.http.put<World>(`${environment.endpoint}/world/world/${world.id}/image`, image).toPromise();
  }

}
