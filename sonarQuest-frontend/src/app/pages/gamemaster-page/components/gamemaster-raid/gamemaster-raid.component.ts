import { RaidModel } from 'app/game-model/RaidModel';
import { Raid } from 'app/Interfaces/Raid';
import { GamemasterRaidCreateComponent } from './components/gamemaster-raid-create/gamemaster-raid-create.component';
import { World } from './../../../../Interfaces/World';
import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { WorldService } from './../../../../services/world.service';
import { Adventure } from './../../../../Interfaces/Adventure';
import {
  ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder,
  IPageChangeEvent
} from '@covalent/core';
import { MatDialog } from '@angular/material';
import { AdventureService } from '../../../../services/adventure.service';
import { QuestService } from '../../../../services/quest.service';
import { TaskService } from 'app/services/task.service';
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';

@Component({
  selector: 'app-gamemaster-raid',
  templateUrl: './gamemaster-raid.component.html',
  styleUrls: ['./gamemaster-raid.component.css']
})
export class GamemasterRaidComponent implements OnInit {

  public world: World;
  public raid: Raid;

  constructor(private dialog: MatDialog,
    private worldService: WorldService)
    { }

  ngOnInit() {
    this.worldService.currentWorld$.subscribe(world => {
      this.world = world
    });
    this.raid = new RaidModel("", "", "", 0,0,null,this.world);
  }

  newRaid() {
    this.dialog.open(GamemasterRaidCreateComponent, { panelClass: 'dialog-sexy', width: '500px', data: {raid: this.raid}}).afterClosed().subscribe((bool) => {
    });
  }



}
