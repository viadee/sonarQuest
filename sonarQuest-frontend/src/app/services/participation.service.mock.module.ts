import {Injectable, NgModule} from '@angular/core';
import {ParticipationService} from "./participation.service";
import {Quest} from "../Interfaces/Quest";
import {Participation} from "../Interfaces/Participation";
import {Observable} from "rxjs";

@Injectable()
export class ParticipationServiceMock {

  participationUpdated$ = new Observable();

  createParticipation(quest: Quest): Promise<Participation> {
    return new Promise<Participation>(() => {});
  }

  public getParticipations(quest: Quest): Promise<Participation[]> {
    return new Promise<Participation[]>(() => {});
  }

  public getAllMyParticipations(): Promise<Participation[]> {
    return new Promise<Participation[]>(() => {});
  }

  announceParticipationUpdate() {}

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: ParticipationService, useClass: ParticipationServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class ParticipationServiceTestingModule {

}
