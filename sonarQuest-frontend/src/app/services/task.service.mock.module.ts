import {Injectable, NgModule} from '@angular/core';
import {TaskService} from "./task.service";
import {Quest} from "../Interfaces/Quest";
import {Task} from "../Interfaces/Task";
import {World} from "../Interfaces/World";

@Injectable()
export class TaskServiceMock {

  public getTasksForQuest(quest: Quest): Promise<Task[]> {
    return new Promise<Task[]>( () => {});
  }

  public getFreeForWorld(world: World): Promise<Task[]> {
    return new Promise<Task[]>( () => {});
  }

  addToQuest(task: any, quest: any): Promise<Task> {
    return new Promise<Task>( () => {});
  }

  deleteFromQuest(task: any): Promise<any> {
    return new Promise<any>( () => {});
  }

  solveTaskManually(task: any): Promise<any> {
    return new Promise<any>( () => {});
  }

  solveAllTasksInQuest(quest: any): Promise<any> {
    return new Promise<any>( () => {});
  }

  refreshTasks(world: World) {}

  calculateGoldAmountOfTasks(tasks: Task[]): number {
    return 23;
  }

  calculateXpAmountOfTasks(tasks: Task[]): number {
    return 42;
  }

  identifyNewAndDeselectedTasks(oldTasks: Task[], currentTasks: Task[]): Array<Task[]> {
    return new Array<Task[]>();
  }

  addParticipation(task: Task, quest: Quest): Promise<Task> {
    return new Promise<Task>(() => {});
  }

  removeParticipation(task: any): Promise<any> {
    return new Promise<any>(() => {});
  }
}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: TaskService, useClass: TaskServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class TaskServiceTestingModule {

}
