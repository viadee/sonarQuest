import {Injectable, NgModule} from '@angular/core';
import {WorldService} from "./world.service";
import {Observable} from "rxjs";
import {World} from "../Interfaces/World";
import {User} from "../Interfaces/User";

@Injectable()
export class WorldServiceMock {

  worlds$ = new Observable<any>();

  currentWorld$ = new Observable();

  public onWorldChange(): Observable<boolean> {
    return new Observable<boolean>();
  }

  public worldChanged(): void {}

  public getWorlds(): Observable<World[]> {
    return new Observable<World[]>();
  }

  public getWorldsForUser(user: User): Promise<World[]> {
    return new Promise<World[]>( () => {});
  }


  public getAllWorlds(): Observable<World[]> {
    return new Observable<World[]>();
  }

  public loadWorld(): void {}

  public getCurrentWorld(): World {
    return null;
  }

  public setCurrentWorld(world: World): Observable<World> {
    return new Observable<World>();
  }

  updateWorld(world: World): Promise<World> {
    return new Promise<World>( () => {});
  }

  updateBackground(world: World, image: string): Promise<World> {
    return new Promise<World>( () => {});
  }

  public getActiveWorlds(): Promise<World[]> {
    return new Promise<World[]>( () => {});
  }

  public generateWorldsFromSonarQubeProjects(): Promise<World[]> {
    return new Promise<World[]>( () => {});
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: WorldService, useClass: WorldServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class WorldServiceTestingModule {

}
