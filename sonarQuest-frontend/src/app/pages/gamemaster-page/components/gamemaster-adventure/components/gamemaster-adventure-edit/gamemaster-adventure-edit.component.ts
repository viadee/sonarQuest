import {World} from '../../../../../../Interfaces/World';
import {WorldService} from '../../../../../../services/world.service';
import {AdventureService} from '../../../../../../services/adventure.service';
import {Adventure} from '../../../../../../Interfaces/Adventure';
import {GamemasterAddFreeQuestComponent} from '../gamemaster-adventure-create/components/gamemaster-add-free-quest/gamemaster-add-free-quest.component';
import {QuestService} from '../../../../../../services/quest.service';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material';
import {MatDialogRef} from '@angular/material';
import {Component, OnInit, Inject} from '@angular/core';
import {GamemasterAdventureComponent} from '../../gamemaster-adventure.component';
import {Quest} from '../../../../../../Interfaces/Quest';
import { AdventureState } from 'app/Interfaces/AdventureState';

@Component({
  selector: 'app-gamemaster-adventure-edit',
  templateUrl: './gamemaster-adventure-edit.component.html',
  styleUrls: ['./gamemaster-adventure-edit.component.css']
})
export class GamemasterAdventureEditComponent implements OnInit {

  isSolved: boolean;
  oldQuests: Quest[];
  currentWorld: World;

  constructor(
    private dialogRef: MatDialogRef<GamemasterAdventureComponent>,
    @Inject(MAT_DIALOG_DATA) public adventure: Adventure,
    public questService: QuestService,
    public adventureService: AdventureService,
    public worldService: WorldService,
    private dialog: MatDialog) {
    this.oldQuests = this.adventure.quests.filter(() => true)
  }

  ngOnInit() {
    // TODO: MAT_DIALOG_DATA makes it hard to see where the data is coming from. Use Events/Services instead?
    this.isSolved = this.adventure.status === AdventureState.SOLVED;
    this.currentWorld = this.worldService.getCurrentWorld();
    this.calculateGoldAmountOfQuests();
    this.calculateXpAmountOfQuests();
  }

  calculateGoldAmountOfQuests(): number {
    return this.questService.calculateGoldAmountOfQuests(this.adventure.quests)
  }

  calculateXpAmountOfQuests(): number {
    return this.questService.calculateXpAmountOfQuests(this.adventure.quests)
  }

  addFreeQuest() {
    this.dialog.open(GamemasterAddFreeQuestComponent, {panelClass: 'dialog-sexy', data: [this.currentWorld, this.adventure.quests]})
      .afterClosed().subscribe(result => {
      if (result) {
        this.adventure.quests.push(result)
      }
    });

  }

  removeQuest(quest: Quest) {
    const questIndex = this.adventure.quests.indexOf(quest);
    this.adventure.quests.splice(questIndex, 1);
  }

  solveAdventure(){    
    this.adventureService.solveAdventure(this.adventure).then(() => {
      this.dialogRef.close(true);
    })
  }


  editAdventure() {
    if (this.adventure.title && this.adventure.gold && this.adventure.xp && this.adventure.story && (this.adventure.quests.length > 0)) {
      const newQuests = this.questService.identifyNewTasks(this.oldQuests, this.adventure.quests);
      const deselectedQuests = this.questService.identifyDeselectedTasks(this.oldQuests, this.adventure.quests);
      this.adventureService.updateAdventure(this.adventure).then(() => {
        const promiseArray = [];
        newQuests.forEach((value, index) => {
          const addQuestToAdventure = this.questService.addToAdventure(value, this.adventure);
          promiseArray.push(addQuestToAdventure);
        });
        deselectedQuests.forEach((value, index) => {
          const removeQuestFromAdventure = this.questService.deleteFromAdventure(value);
          promiseArray.push(removeQuestFromAdventure);
        });
        return Promise.all(promiseArray)
      }).then(() => {
        this.dialogRef.close(true);
      })
    }
  }
}
