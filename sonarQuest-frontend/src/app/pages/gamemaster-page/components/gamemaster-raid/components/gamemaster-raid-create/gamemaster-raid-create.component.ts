import { GamemasterRaidComponent } from './../../gamemaster-raid.component';
import { Component, OnInit, Input, Optional, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { RaidService } from 'app/services/raid.service';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { GamemasterAddFreeQuestComponent } from '../../../gamemaster-adventure/components/gamemaster-adventure-create/components/gamemaster-add-free-quest/gamemaster-add-free-quest.component';
import { Raid } from 'app/Interfaces/Raid';

@Component({
  selector: 'app-gamemaster-raid-create',
  templateUrl: './gamemaster-raid-create.component.html',
  styleUrls: ['./gamemaster-raid-create.component.css']
})
export class GamemasterRaidCreateComponent implements OnInit {

  @Input()
  raid: Raid;

  images: any[];

  constructor(private dialog: MatDialog, public dialogRef: MatDialogRef<GamemasterRaidCreateComponent>,  private raidService: RaidService,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any){
    this.raid = data.raid;
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

  saveRaid() {
    this.raidService.createRaid(this.raid).then((newRaid) => {
      console.log(newRaid);
      // TODO show list or table
    });
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
