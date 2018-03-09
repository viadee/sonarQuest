import {Component, Inject, OnInit} from '@angular/core';
import {Task} from "app/Interfaces/Task";
import {TaskService} from "../../../../../../../../services/task.service";
import {MatDialogRef, MAT_DIALOG_DATA} from "@angular/material";
import {GamemasterQuestCreateComponent} from "../../gamemaster-quest-create.component";


@Component({
  selector: 'app-gamemaster-add-free-task',
  templateUrl: './gamemaster-add-free-task.component.html',
  styleUrls: ['./gamemaster-add-free-task.component.css']
})
export class GamemasterAddFreeTaskComponent implements OnInit {

  freeTasks: Task[];

  constructor(private taskService: TaskService,private dialogRef: MatDialogRef<GamemasterQuestCreateComponent>,
              @Inject(MAT_DIALOG_DATA) public data) { }

  ngOnInit() {
    this.taskService.getFreeTasksForWorld(this.data[0]).then(freetasks=>{
      let addedTasks = this.data[1].map(task=>task.id);
      this.freeTasks=freetasks.filter(task=>{return addedTasks.indexOf(task.id)<0});
    });

  }

  addTask(task: Task){
    this.dialogRef.close(task);
  }


}
