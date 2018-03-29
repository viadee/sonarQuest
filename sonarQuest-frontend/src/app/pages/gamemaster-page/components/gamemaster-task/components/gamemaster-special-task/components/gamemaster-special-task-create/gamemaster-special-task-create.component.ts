import { Task } from 'app/Interfaces/Task';
import { World } from './../../../../../../../../Interfaces/World';
import { Component, OnInit, Inject } from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from "@angular/material";
import {GamemasterSpecialTaskComponent} from "../../gamemaster-special-task.component";
import {SpecialTaskService} from "../../../../../../../../services/special-task.service";

@Component({
  selector: 'app-gamemaster-special-task-create',
  templateUrl: './gamemaster-special-task-create.component.html',
  styleUrls: ['./gamemaster-special-task-create.component.css']
})
export class GamemasterSpecialTaskCreateComponent implements OnInit {

  title: string;
  gold: number;
  xp: number;
  message: string;

  task: Task

  constructor(
    private dialogRef: MatDialogRef<GamemasterSpecialTaskComponent>, 
    private specialTaskService: SpecialTaskService,
    @Inject(MAT_DIALOG_DATA) public currentWorld: World,
  ) { }

  ngOnInit() {
  }

  createSpecialTask(){
    if(this.title && this.gold && this.xp && this.message && this.currentWorld){
      let specialTask= {
        title: this.title,
        gold: this.gold,
        xp: this.xp,
        message: this.message,
        world: this.currentWorld
      }
      this.specialTaskService.createSpecialTask(specialTask).then(()=>this.dialogRef.close())
    }

  }

}
