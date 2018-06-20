import {Component, Inject, OnInit} from '@angular/core';
import {Task} from 'app/Interfaces/Task';
import {TaskService} from '../../../../../../../../services/task.service';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {GamemasterQuestCreateComponent} from '../../gamemaster-quest-create.component';
import {SonarCubeService} from '../../../../../../../../services/sonar-cube.service';
import {World} from '../../../../../../../../Interfaces/World';
import {WorldService} from '../../../../../../../../services/world.service';


@Component({
  selector: 'app-gamemaster-add-free-task',
  templateUrl: './gamemaster-add-free-task.component.html',
  styleUrls: ['./gamemaster-add-free-task.component.css']
})
export class GamemasterAddFreeTaskComponent implements OnInit {

  currentWorld: World;
  freeTasks: Task[];

  constructor(
    private sonarCubeService: SonarCubeService,
    private worldService: WorldService,
    private taskService: TaskService,
    private dialogRef: MatDialogRef<GamemasterQuestCreateComponent>,
    @Inject(MAT_DIALOG_DATA) public data
  ) {
  }

  ngOnInit() {
    this.currentWorld = this.data[0];
    this.taskService.getFreeTasksForWorld(this.currentWorld).then(freetasks => {
      const addedTasks = this.data[1].map(task => task.id);
      this.freeTasks = freetasks.filter(task => {
        return addedTasks.indexOf(task.id) < 0
      });
    });
  }

  addTask(task: Task) {
    this.dialogRef.close(task);
  }

  openIssue(task: Task) {
    this.sonarCubeService.getIssueLink(task.key, this.currentWorld)
      .then(link => window.open(link, '_blank'));
  }

}
