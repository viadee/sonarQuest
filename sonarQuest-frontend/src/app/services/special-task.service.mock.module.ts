import {Injectable, NgModule} from '@angular/core';
import {SpecialTaskService} from "./special-task.service";
import {World} from "../Interfaces/World";
import {Observable} from "rxjs";
import {SpecialTask} from "../Interfaces/SpecialTask";
import {Task} from "../Interfaces/Task";

@Injectable()
export class SpecialTaskServiceMock {
  getSpecialTasksForWorld(world: World): Observable<SpecialTask[]> {
    return new Observable<SpecialTask[]>();
  }

  createSpecialTask(specialTask: any): Promise<SpecialTask> {
    return new Promise<SpecialTask>(() => {});
  }

  updateSpecialTask(specialTask: any): Promise<SpecialTask> {
    return new Promise<SpecialTask>(() => {});
  }

  deleteSpecialTask(specialTask: any): Promise<any> {
    return new Promise<any>(() => {});
  }

  solveSpecialTask(specialTask: any): Promise<SpecialTask> {
    return new Promise<SpecialTask>(() => {});
  }

  public getFreeSpecialTasksForWorldExcept(world: World, excludetTasks: Task[]): Promise<SpecialTask[]> {
    return new Promise<SpecialTask[]>(() => {});
  }
}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: SpecialTaskService, useClass: SpecialTaskServiceMock }
  ],
  imports: [

  ],
  exports: [

  ]
})
export class SpecialTaskServiceTestingModule {

}
