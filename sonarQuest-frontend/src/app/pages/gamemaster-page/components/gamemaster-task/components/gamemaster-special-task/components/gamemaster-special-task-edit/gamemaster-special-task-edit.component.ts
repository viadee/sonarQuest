import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {GamemasterSpecialTaskComponent} from "../../gamemaster-special-task.component";
import {SpecialTaskService} from "../../../../../../../../services/special-task.service";

@Component({
  selector: 'app-gamemaster-special-task-edit',
  templateUrl: './gamemaster-special-task-edit.component.html',
  styleUrls: ['./gamemaster-special-task-edit.component.css']
})
export class GamemasterSpecialTaskEditComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<GamemasterSpecialTaskComponent>,
              @Inject(MAT_DIALOG_DATA) public specialTask,private specialTaskService: SpecialTaskService) { }

  ngOnInit() {
  }

  updateSpecialTask(){
    if(this.specialTask.title && this.specialTask.gold && this.specialTask.xp && this.specialTask.message) {
      this.specialTaskService.updateSpecialTask(this.specialTask).then(() => this.dialogRef.close());
    }
  }

}
