import { Component, Inject, OnInit } from '@angular/core';
import { QuestService } from "../../../../../../../../services/quest.service";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material";
import { GamemasterAdventureCreateComponent } from "../../gamemaster-adventure-create.component";
import { World } from "../../../../../../../../Interfaces/World";
import { Quest } from "../../../../../../../../Interfaces/Quest";

@Component({
  selector: 'app-gamemaster-add-free-quest',
  templateUrl: './gamemaster-add-free-quest.component.html',
  styleUrls: ['./gamemaster-add-free-quest.component.css']
})
export class GamemasterAddFreeQuestComponent implements OnInit {

  freeQuests: Quest[]

  constructor(
    private questService: QuestService,
    private dialogRef: MatDialogRef<GamemasterAdventureCreateComponent>,
    @Inject(MAT_DIALOG_DATA) public data
  ) { }

  ngOnInit() {
    let addedQuests = this.data[1].map(quest => quest.id);
    this.questService.getFreeQuestsForWorld(this.data[0]).then((quests) => {
      this.freeQuests = quests.filter(quest => { return addedQuests.indexOf(quest.id) < 0 });
    })
  }

  addQuest(quest) {
    this.dialogRef.close(quest);
  }

}
