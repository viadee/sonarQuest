import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from "@angular/material";
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

  constructor(private dialogRef: MatDialogRef<GamemasterSpecialTaskComponent>, private specialTaskService: SpecialTaskService) { }

  ngOnInit() {
  }

  createSpecialTask(){
    if(this.title && this.gold && this.xp && this.message){
      let specialTask= {
        title: this.title,
        gold: this.gold,
        xp: this.xp,
        message: this.message
      }
      this.specialTaskService.createSpecialTask(specialTask).then(()=>this.dialogRef.close())
    }

  }

}
