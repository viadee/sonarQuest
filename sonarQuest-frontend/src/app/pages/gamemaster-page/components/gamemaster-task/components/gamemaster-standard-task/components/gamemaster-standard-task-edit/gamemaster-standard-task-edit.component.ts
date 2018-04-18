import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {GamemasterStandardTaskComponent} from "../../gamemaster-standard-task.component";
import {StandardTaskService} from "../../../../../../../../services/standard-task.service";

@Component({
  selector: 'app-gamemaster-standard-task-edit',
  templateUrl: './gamemaster-standard-task-edit.component.html',
  styleUrls: ['./gamemaster-standard-task-edit.component.css']
})
export class GamemasterStandardTaskEditComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<GamemasterStandardTaskComponent>,
              @Inject(MAT_DIALOG_DATA) public standardTask, public standardTaskService: StandardTaskService) { }

  ngOnInit() {

  }

  updateStandardTask(){
    if(this.standardTask.xp && this.standardTask.title && this.standardTask.gold){
      this.standardTaskService.updateStandardTask(this.standardTask).then(()=> this.dialogRef.close(true));
    }
  }
}
