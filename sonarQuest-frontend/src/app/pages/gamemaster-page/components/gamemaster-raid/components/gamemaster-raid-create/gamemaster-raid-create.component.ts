import { Component, OnInit, Input, Optional, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Raid } from 'app/Interfaces/Raid';
import {
  GamemasterAddFreeQuestComponent
} from '../../../gamemaster-adventure/components/gamemaster-adventure-create/components/gamemaster-add-free-quest/gamemaster-add-free-quest.component';

// Can be expanded
export const enum DialogResultType {
  SAVE,
  SOLVE
}
export interface RaidDialogResult {
  raid: Raid;
  dialogResult: DialogResultType;
}
@Component({
  selector: 'app-gamemaster-raid-create',
  templateUrl: './gamemaster-raid-create.component.html',
  styleUrls: ['./gamemaster-raid-create.component.css']
})
export class GamemasterRaidCreateComponent implements OnInit {

  @Input()
  raid: Raid;

  isSolved: boolean;
  images: any[];

  constructor(private dialog: MatDialog,
    public dialogRef: MatDialogRef<GamemasterRaidCreateComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any) {
    this.raid = data.raid;
    this.isSolved = this.raid.status === 'SOLVED';
  }

  ngOnInit() {
    this.loadMonsterImages();
  }

  addFreeQuest() {
    this.dialog.open(GamemasterAddFreeQuestComponent, { panelClass: 'dialog-sexy', data: [this.raid.world, this.raid.quests] })
      .afterClosed().subscribe(result => {
        if (result) {
          this.raid.quests.push(result)
        }
      });
  }

  removeQuest(quest) {
    const questIndex = this.raid.quests.indexOf(quest);
    this.raid.quests.splice(questIndex, 1);
  }

  solveRaid() {
    const raidDialogResult: RaidDialogResult = {
      raid: this.raid,
      dialogResult: DialogResultType.SOLVE
    };
    this.dialogRef.close(raidDialogResult);
  }

  saveRaid() {
    const raidDialogResult: RaidDialogResult = {
      raid: this.raid,
      dialogResult: DialogResultType.SAVE
    };
    this.dialogRef.close(raidDialogResult);
  }

  // TODO move in service
  loadMonsterImages() {
    this.images = [];
    for (let i = 0; i < 3; i++) {
      this.images[i] = {};
      this.images[i].src = 'assets/images/monster/monster' + (i + 1) + '.png';
      this.images[i].name = 'monster' + (i + 1);
    }
  }
}
