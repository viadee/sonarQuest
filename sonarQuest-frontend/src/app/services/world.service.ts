import {Observable, Subscriber, ReplaySubject} from 'rxjs';
import {Injectable} from '@angular/core';
import {World} from '../Interfaces/World';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {UserService} from './user.service';
import {User} from '../Interfaces/User';
import { Subject } from 'rxjs';

@Injectable()
export class WorldService {

  //world: World = null;
  wordChangeListener: Subscriber<boolean>[] = [];

  private worldsSubject: Subject<World[]> = new ReplaySubject(1);
  worlds$ = this.worldsSubject.asObservable();

  private allWorldsSubject: Subject<World[]> = new ReplaySubject(1);
  allWorlds$ = this.allWorldsSubject.asObservable();

  private currentWorldSubject: Subject<World> = new ReplaySubject(1);
  currentWorld$ = this.currentWorldSubject.asObservable();

  constructor(private http: HttpClient, private userService: UserService) {

    userService.user$.subscribe(user => {
      
        this.getWorlds();
        //this.world = user.currentWorld
        this.currentWorldSubject.next(user.currentWorld)
    })

    this.getAllWorlds();
  }

  public onWorldChange(): Observable<boolean> {
    return new Observable<boolean>(observer => {
      this.wordChangeListener.push(observer);
    });
  }

  public worldChanged(): void {
    this.wordChangeListener.forEach(l => l.next(true));
  }

  /**
   * Get all assigned worlds for logged in user
   */
  public getWorlds(): Observable<World[]> {
    this.http.get<World[]>(`${environment.endpoint}/world/worlds`).subscribe(
      result => this.worldsSubject.next(result),
      err => this.worldsSubject.error(err)
    );
    return this.worldsSubject;
  }

  /**
   * Get all assigned worlds for user
   * The function is needed when editing the worlds assigned to a user
   * @param user  
   */
  public ggetWorldsForUser(user: User): Promise<World[]> {
    return this.http.get<World[]>(`${environment.endpoint}/world/user/${user.id}`).toPromise();
  }

  public getAllWorlds(): Observable<World[]> {
    this.http.get<World[]>(`${environment.endpoint}/world/all`).subscribe(
      result => this.allWorldsSubject.next(result),
      err => this.allWorldsSubject.error(err)
    );
    return this.allWorldsSubject;
  }

  public loadWorld(): void {
    this.http.get<World>(`${environment.endpoint}/world/current`).subscribe(
      world => {
        this.currentWorldSubject.next(world);
        //this.world = world;
        this.worldChanged();
      });
  }

  public setCurrentWorld(world: World): Observable<World> {
    this.http.post<World>(`${environment.endpoint}/world/current`, world).subscribe(
      result => {
        this.currentWorldSubject.next(result)
        //this.world = result;
      },
      err => this.currentWorldSubject.error(err)
    );
    return this.currentWorldSubject;
  }

  updateWorld(world: World): Promise<World> {
    return this.http.post<World>(`${environment.endpoint}/world/world`, world).toPromise();
  }

  updateBackground(world: World, image: string): Promise<World> {
    return this.http.put<World>(`${environment.endpoint}/world/world/${world.id}/image`, image).toPromise();
  }

  public getActiveWorlds(): Promise<World[]> {
    return this.http.get<World[]>(`${environment.endpoint}/world/active`).toPromise();
  }

  public generateWorldsFromSonarQubeProjects(): Promise<World[]> {
    return this.http.get<World[]>(`${environment.endpoint}/world/generate`).toPromise();
  }

}
