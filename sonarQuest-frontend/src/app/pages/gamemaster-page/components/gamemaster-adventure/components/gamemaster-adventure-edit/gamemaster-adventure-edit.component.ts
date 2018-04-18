import { World } from './../../../../../../Interfaces/World';
import { WorldService } from './../../../../../../services/world.service';
import { AdventureService } from './../../../../../../services/adventure.service';
import { Adventure } from './../../../../../../Interfaces/Adventure';
import { GamemasterAddFreeQuestComponent } from './../gamemaster-adventure-create/components/gamemaster-add-free-quest/gamemaster-add-free-quest.component';
import { QuestService } from './../../../../../../services/quest.service';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { MatDialogRef } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';
import { GamemasterAdventureComponent } from '../../gamemaster-adventure.component';
import { Quest } from '../../../../../../Interfaces/Quest';

@Component({
  selector: 'app-gamemaster-adventure-edit',
  templateUrl: './gamemaster-adventure-edit.component.html',
  styleUrls: ['./gamemaster-adventure-edit.component.css']
})
export class GamemasterAdventureEditComponent implements OnInit {


  oldQuests: Quest[];
  currentWorld: World;

  constructor(
    private dialogRef: MatDialogRef<GamemasterAdventureComponent>,
    @Inject(MAT_DIALOG_DATA) public adventure: Adventure,
    public questService: QuestService,
    public advantureService: AdventureService,
    public worldService: WorldService,
    private dialog: MatDialog) {
    this.oldQuests = this.adventure.quests.filter(() => true)
  }

  ngOnInit() {
    this.worldService.currentWorld$.subscribe(world => this.currentWorld = world);
  }

  calculateGoldAmountOfQuests(): number {
    return this.questService.calculateGoldAmountOfQuests(this.adventure.quests)
  }

  calculateXpAmountOfQuests(): number {
    return this.questService.calculateXpAmountOfQuests(this.adventure.quests)
  }

  addFreeQuest() {
    this.dialog.open(GamemasterAddFreeQuestComponent, {panelClass: "dialog-sexy", data: [this.currentWorld, this.adventure.quests] })
      .afterClosed().subscribe(result => {
        if (result) {
          this.adventure.quests.push(result)
        }
      });

  }

  removeQuest(quest: Quest) {
    let questIndex = this.adventure.quests.indexOf(quest);
    this.adventure.quests.splice(questIndex, 1);
  }


  editAdventure() {
    if (this.adventure.title && this.adventure.gold && this.adventure.xp && this.adventure.story && (this.adventure.quests.length > 0)) {
      let newQuests = this.questService.identifyNewTasks(this.oldQuests, this.adventure.quests);
      let deselcetedQuests = this.questService.identifyDeselectedTasks(this.oldQuests, this.adventure.quests);
      this.advantureService.updateAdventure(this.adventure).then(() => {
        let promiseArray = [];
        newQuests.forEach((value, index) => {
          let addQuestToAdventure = this.questService.addToAdventure(value, this.adventure);
          promiseArray.push(addQuestToAdventure);
        });
        deselcetedQuests.forEach((value, index) => {
          let removeQuestFromAdventure = this.questService.deleteFromAdventure(value);
          promiseArray.push(removeQuestFromAdventure);
        });
        return Promise.all(promiseArray)
      }).then(() => {
        this.dialogRef.close(true);
      })
    }
  }
}
