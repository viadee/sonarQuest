import {Injectable, NgModule} from '@angular/core';
import {SkillService} from "./skill.service";
import {Observable} from "rxjs";
import {Skill} from "../Interfaces/Skill";
import {Artefact} from "../Interfaces/Artefact";

@Injectable()
export class SkillServiceMock {
  getSkills(): Observable<Skill[]> {
    return new Observable<Skill[]>();
  }

  getSkillsForArtefact(artefact: Artefact): Promise<Skill[]> {
    return new Promise<Skill[]>(() => {
      return [];
    });
  }

  deleteSkill(skill: Skill): Promise<any> {
    return new Promise<any>(() => {});
  }

  createSkill(skill: any): Promise<Skill> {
    return new Promise<Skill>(() => {});
  }
}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: SkillService, useClass: SkillServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class SkillServiceTestingModule {

}
