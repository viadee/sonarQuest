import {Injectable, NgModule} from '@angular/core';
import {QuestService} from "./quest.service";
import {World} from "../Interfaces/World";
import {Observable} from "rxjs";
import {Quest} from "../Interfaces/Quest";
import {Task} from "../Interfaces/Task";
import {Adventure} from "../Interfaces/Adventure";

@Injectable()
export class QuestServiceMock {

  getQuestsForWorld(world: World): Observable<Quest[]> {
    return new Observable<Quest[]>();
  }

  public getQuest(id: number): Promise<Quest> {
    return new Promise<Quest>(()=>{});
  }

  solveQuestDummy(quest: Quest): Promise<Quest> {
    return new Promise<Quest>(()=>{});
  }

  solveQuestDummyy(quest: Quest): Observable<Quest[]> {
    return new Observable<Quest[]>();
  }


  createQuest(quest: any): Promise<Quest> {
    return new Promise<Quest>(()=>{});
  }

  updateQuest(quest: Quest): Promise<any> {
    return new Promise<any>(()=>{});
  }

  deleteQuest(quest: Quest): Promise<any> {
    return new Promise<any>(()=>{});
  }

  addToWorld(quest: any, world: any): Promise<any> {
    return new Promise<any>(()=>{});
  }

  suggestTasksWithApproxXpAmountForWorld(world: World, xp: number): Promise<Task[]> {
    return new Promise<Task[]>(()=>{});
  }

  suggestTasksWithApproxGoldAmountForWorld(world: World, gold: number): Promise<Task[]> {
    return new Promise<Task[]>(()=>{});
  }

  getFreeQuestsForWorld(world: World): Promise<any> {
    return new Promise<any>(()=>{});
  }

  getAllParticipatedQuestsForWorldAndUser(world: World) {
    return {};
  }

  getAllAvailableQuestsForWorldAndUser(world: World) {
    return {};
  }

  refreshQuests(world: World) {}


  identifyNewTasks(oldQuests: Quest[], currentQuests: Quest[]): Quest[] {
    return [];
  }

  identifyDeselectedTasks(oldQuests: Quest[], currentQuests: Quest[]): Quest[] {
    return [];
  }

  calculateGoldAmountOfQuests(quests: Quest[]): number {
    return 42;
  }

  calculateXpAmountOfQuests(quests: Quest[]): number {
    return 23;
  }

  addToAdventure(quest: Quest, adventure: Adventure): Promise<any> {
    return new Promise<any>(()=>{});
  }

  deleteFromAdventure(quest: any): Promise<any> {
    return new Promise<any>(()=>{});
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: QuestService, useClass: QuestServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class QuestServiceTestingModule {

}
