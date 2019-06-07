import {Injectable, NgModule} from '@angular/core';
import {ArtefactService} from "./artefact.service";
import {Observable} from "rxjs";
import {Artefact} from "../Interfaces/Artefact";

@Injectable()
export class ArtefactServiceMock {

  artefactsforMarkteplace$ = new Observable<Artefact[]>();

  artefacts$ = new Observable<Artefact>();

  getData(): void {}

  getArtefact(): Promise<Artefact>{
    return new Promise<Artefact>(() => {});
  }

  getArtefacts(): Observable<Artefact[]> {
    return new Observable<Artefact[]>();
  }

  getArtefactsforMarkteplace(): Observable<Artefact[]> {
    return new Observable<Artefact[]>();
  }

  createArtefact(artefact: any): Promise<Artefact> {
    return new Promise<Artefact>(() => {});
  }

  updateArtefact(artefact: any): Promise<Artefact> {
    return new Promise<Artefact>(() => {});
  }

  buyArtefact(artefact: Artefact): Promise<boolean> {
    return new Promise<boolean>(() => {});
  }

  deleteArtefact(artefact: Artefact): Promise<any> {
    return new Promise<any>(() => {});
  }
}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: ArtefactService, useClass: ArtefactServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class ArtefactServiceTestingModule {

}
