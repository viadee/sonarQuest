import {Injectable, NgModule} from '@angular/core';
import {AdventureService} from "./adventure.service";
import {World} from "../Interfaces/World";
import {Observable} from "rxjs";
import {Adventure} from "../Interfaces/Adventure";

@Injectable()
export class AdventureServiceMock {

  getAdventuresForWorld(world: World): Observable<Adventure[]> {
    return new Observable<Adventure[]>();
  }


  getFreeAdventures(world: World): Observable<Adventure[]> {
    return new Observable<Adventure[]>();
  }

  getMyAdventures(world: World): Observable<Adventure[]> {
    return new Observable<Adventure[]>();
  }

  leaveAdventure(adventure: Adventure): Promise<Adventure> {
    return new Promise<Adventure>( () => {});
  }

  joinAdventure(adventure: Adventure): Promise<Adventure> {
    return new Promise<Adventure>( () => {});
  }

  createAdventure(adventure: any): Promise<Adventure> {
    return new Promise<Adventure>( () => {});
  }

  solveAdventure(adventure: any): Promise<Adventure> {
    return new Promise<Adventure>( () => {});
  }

  updateAdventure(adventure: Adventure): Promise<any> {
    return new Promise<any>( () => {});
  }

  addQuest(adventure: any, quest: any): Promise<any> {
    return new Promise<any>( () => {});
  }

  refreshAdventures(world: World) {}

  deleteAdventure(adventure: Adventure): Promise<any> {
    return new Promise<any>( () => {});
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: AdventureService, useClass: AdventureServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class AdventureServiceTestingModule {

}
