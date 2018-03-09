import {Component, Inject, OnInit} from '@angular/core';
import {QuestService} from "../../../../../../../../services/quest.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {GamemasterQuestCreateComponent} from "../../gamemaster-quest-create.component";
import {World} from "../../../../../../../../Interfaces/World";

@Component({
  selector: 'app-gamemaster-suggest-tasks',
  templateUrl: './gamemaster-suggest-tasks.component.html',
  styleUrls: ['./gamemaster-suggest-tasks.component.css']
})
export class GamemasterSuggestTasksComponent implements OnInit {

  condition: string;
  amount: number;

  constructor(private questService: QuestService,private dialogRef: MatDialogRef<GamemasterQuestCreateComponent>,
              @Inject(MAT_DIALOG_DATA) public data) { }

  ngOnInit() {
  }

  suggestTasks(){
    let addedTasks = this.data[1].map(task=>task.id);
    if(this.condition ==="xp"){
      this.questService.suggestTasksWithApproxXpAmountForWorld(this.data[0],this.amount).then((tasks)=>{
        this.dialogRef.close(tasks.filter(task=>{return addedTasks.indexOf(task.id)<0}));
      })
    }else{
      this.questService.suggestTasksWithApproxGoldAmountForWorld(this.data[0],this.amount).then((tasks)=>{
        this.dialogRef.close(tasks.filter(task=>{return addedTasks.indexOf(task.id)<0}));
      })
    }
  }

}
