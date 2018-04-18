import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from "@angular/material";
import { GamemasterAdventureComponent } from "../../gamemaster-adventure.component";
import { AdventureService } from "../../../../../../services/adventure.service";
import { QuestService } from "../../../../../../services/quest.service";
import { GamemasterAddFreeQuestComponent } from "./components/gamemaster-add-free-quest/gamemaster-add-free-quest.component";
import { WorldService } from "../../../../../../services/world.service";
import { World } from "../../../../../../Interfaces/World";
import { Quest } from "../../../../../../Interfaces/Quest";

@Component({
  selector: 'app-gamemaster-adventure-create',
  templateUrl: './gamemaster-adventure-create.component.html',
  styleUrls: ['./gamemaster-adventure-create.component.css']
})
export class GamemasterAdventureCreateComponent implements OnInit {

  title: string;
  gold: number;
  xp: number;
  story: string;
  quests: Quest[] = [];
  currentWorld: World;
  worlds: World[];
  selectedWorld: World;

  constructor(private dialog: MatDialog,
    private questService: QuestService,
    private adventureService: AdventureService,
    private dialogRef: MatDialogRef<GamemasterAdventureComponent>,
    private worldService: WorldService) {
  }

  ngOnInit() {
    this.worldService.currentWorld$.subscribe(world => {
      this.currentWorld = world;
      this.selectWorld();
    });
    this.worldService.worlds$.subscribe(worlds => {
      this.worlds = worlds;
      this.selectWorld();
    })
  }

  selectWorld(){
    if(this.worlds && this.currentWorld){
      this.selectedWorld = this.worlds.filter(world => world.id == this.currentWorld.id)[0]
    }
  }

  addFreeQuest() {
    this.dialog.open(GamemasterAddFreeQuestComponent, {panelClass: "dialog-sexy", data: [this.selectedWorld, this.quests] })
      .afterClosed().subscribe(result => {
        if (result) {
          this.quests.push(result)
        }
      });
  }


  removeQuest(quest) {
    let questIndex = this.quests.indexOf(quest);
    this.quests.splice(questIndex, 1);
  }

  createAdventure() {
    if (this.title && this.gold && this.xp && this.story && this.selectedWorld && (this.quests.length != 0)) {
      let adventure = {
        title:  this.title,
        gold:   this.gold,
        xp:     this.xp,
        story:  this.story,
        world:  this.selectedWorld
      }
      this.adventureService.createAdventure(adventure).then((adventure) => {
        if (adventure.id) {
          let promiseArray = [];
          this.quests.forEach((value, index) => {
            let addQuestToAdventure = this.adventureService.addQuest(adventure, value);
            promiseArray.push(addQuestToAdventure);
          })
          return Promise.all(promiseArray);
        }
      }).then(() => {
        this.dialogRef.close(true);
      })

    }

  }
}
