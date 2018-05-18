import { Task } from 'app/Interfaces/Task';
import { World } from './../../../../../../../../Interfaces/World';
import { Component, OnInit, Inject } from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from "@angular/material";
import {GamemasterStandardTaskComponent} from "../../gamemaster-standard-task.component";
import {StandardTaskService} from "../../../../../../../../services/standard-task.service";

@Component({
  selector: 'app-gamemaster-standard-task-create',
  templateUrl: './gamemaster-standard-task-create.component.html',
  styleUrls: ['./gamemaster-standard-task-create.component.css']
})
export class GamemasterStandardTaskCreateComponent implements OnInit {

  title: string;
  gold: number;
  xp: number;
  message: string;

  task: Task

  constructor(
    private dialogRef: MatDialogRef<GamemasterStandardTaskComponent>, 
    private standardTaskService: StandardTaskService,
    @Inject(MAT_DIALOG_DATA) public currentWorld: World,
  ) { }

  ngOnInit() {
  }

  createStandardTask(){
    if(this.title && this.gold && this.xp && this.message && this.currentWorld){
      let standardTask= {
        title: this.title,
        gold: this.gold,
        xp: this.xp,
        message: this.message,
        world: this.currentWorld
      }
      this.standardTaskService.createStandardTask(standardTask).then(()=>this.dialogRef.close())
    }

  }

}
