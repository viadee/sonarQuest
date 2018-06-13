import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {World} from '../Interfaces/World';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Subscriber} from 'rxjs/Subscriber';
import {UserService} from './user.service';

@Injectable()
export class WorldService {

  world: World;
  worlds: World[];

  wordChangeListener: Subscriber<boolean>[] = [];
  wordsChangeListener: Subscriber<boolean>[] = [];

  constructor(private http: HttpClient, private userService: UserService) {
    userService.onUserChange().subscribe(() => {
      if (userService.getUser()) {
        this.loadWorlds();
        this.loadWorld();
      }
    });
  }

  public onWorldChange(): Observable<boolean> {
    return new Observable<boolean>(observer => {
      this.wordChangeListener.push(observer);
    });
  }

  public onWorldsChanged(): Observable<boolean> {
    return new Observable<boolean>(observer => {
      this.wordsChangeListener.push(observer);
    });
  }

  private worldChanged(): void {
    this.wordChangeListener.forEach(l => l.next(true));
  }

  private worldsChanged(): void {
    this.wordsChangeListener.forEach(l => l.next(true));
  }

  public loadWorlds(): void {
    this.http.get<World[]>(`${environment.endpoint}/world/worlds`)
      .subscribe(worlds => {
        this.worlds = worlds;
        this.worldsChanged();
      });
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

  public getWorlds(): World[] {
    return this.worlds;
  }

  updateWorld(world: World): Promise<World> {
    return this.http.post<World>(`${environment.endpoint}/world/world`, world).toPromise();
  }

  updateBackground(world: World, image: string): Promise<World> {
    return this.http.put<World>(`${environment.endpoint}/world/world/${world.id}/image`, image).toPromise();
  }

}
