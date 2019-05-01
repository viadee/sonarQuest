import {Injectable, NgModule} from '@angular/core';
import {StandardTaskService} from "./standard-task.service";
import {World} from "../Interfaces/World";
import {Observable} from "rxjs";
import {StandardTask} from "../Interfaces/StandardTask";
import {Task} from "../Interfaces/Task";

@Injectable()
export class StandardTaskServiceMock {
  getStandardTasksForWorld(world: World): Observable<StandardTask[]> {
    return new Observable<StandardTask[]>();
  }

  updateStandardTask(task: StandardTask): Promise<StandardTask> {
    return new Promise<StandardTask>(() => {});
  }

  updateStandardTasksForWorld(world: World): Promise<Task[]> {
    return new Promise<Task[]>(() => {});
  }

  public getFreeStandardTasksForWorldExcept(world: World, excludedTasks: Task[]): Promise<StandardTask[]> {
    return new Promise<StandardTask[]>(() => {});
  }
}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: StandardTaskService, useClass: StandardTaskServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class StandardTaskServiceTestingModule {

}
