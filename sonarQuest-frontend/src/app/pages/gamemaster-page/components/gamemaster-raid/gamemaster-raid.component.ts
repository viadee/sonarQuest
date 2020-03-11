import { QualityGateServiceService } from './../../../../services/quality-gate-service.service';
import { RaidService } from 'app/services/raid.service';
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
  data: any[] = [];

  // TODO use translateservice
  columns: ITdDataTableColumn[] = [
    { name: 'monsterImage', label: '' },
    { name: 'title', label: 'Title', width: 200 },
    { name: 'gold', label: 'Gold' },
    { name: 'xp', label: 'XP' },
    { name: 'monsterName', label: 'Monster Name' },
    { name: 'visible', label: 'Sichtbarkeit' },
    { name: 'status', label: 'Status' },
    { name: 'startdate', label: 'Startdatum' },
    { name: 'enddate', label: 'Enddatum' },
    { name: 'edit', label: '', width: 70 }
  ]

  fromRow = 1;
  currentPage = 1;
  pageSize = 1;
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;
  swalOptionsConfirmDelete: {}
  swalOptionsDeleteSuccess: {}

  constructor(private _dataTableService: TdDataTableService,
      private worldService: WorldService,
      private translateService: TranslateService,
      private dialog: MatDialog,
      private raidService: RaidService,
      private qualityGateService: QualityGateServiceService) {
  }

  ngOnInit() {
    this.worldService.currentWorld$.subscribe(world => {
      this.world = world;
      this.loadRaids();
    });
    this.raid = new RaidModel("", "", "", 0,0,null,this.world);

    this.swalOptionsConfirmDelete = {
      title: this.translate('GLOBAL.DELETE'),
      text: this.translate('GLOBAL.CONFIRMATION_MESSAGE'),
      backdrop: false,
      type: 'question',
      showCancelButton: true,
      cancelButtonText: this.translate('GLOBAL.CANCEL'),
      allowEscapeKey: true,
      allowEnterKey: true,
      confirmButtonColor: '#C62828',
      confirmButtonText: this.translate('GLOBAL.DELETE')
    }
    this.swalOptionsDeleteSuccess = {
      title: this.translate('GLOBAL.DELETE_SUCCESS'),
      toast: true,
      type: 'success',
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000
    }
  }

  newRaid() {
    this.dialog.open(GamemasterRaidCreateComponent, { panelClass: 'dialog-sexy',
          width: '500px', data: {raid: this.raid}}).afterClosed().subscribe((bool) => {
    });
  }

  loadRaids() {
    return this.raidService.findAllRaidsByWorld(this.world.id).subscribe(raids => {
      this.data = raids;
    });
  }

  translate(messageString: string): string {
    let msg = '';
    this.translateService.get(messageString).subscribe(translateMsg => msg = translateMsg);
    return msg;
  }
}
