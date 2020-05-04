import { RaidService } from 'app/services/raid.service';
import { RaidModel } from 'app/game-model/RaidModel';
import { Raid } from 'app/Interfaces/Raid';
import {
  GamemasterRaidCreateComponent,
  RaidDialogResult
} from './components/gamemaster-raid-create/gamemaster-raid-create.component';
import { World } from './../../../../Interfaces/World';
import { Component, OnInit, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { WorldService } from './../../../../services/world.service';
import {
  ITdDataTableColumn,
  TdDataTableSortingOrder,
} from '@covalent/core';
import { MatDialog } from '@angular/material';
import { QuestService } from 'app/services/quest.service';
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';

@Component({
  selector: 'app-gamemaster-raid',
  templateUrl: './gamemaster-raid.component.html',
  styleUrls: ['./gamemaster-raid.component.css']
})
export class GamemasterRaidComponent implements OnInit {

  @ViewChild('cannotDeleteRaidSwal', { static: false }) private cannotDeleteRaidSwal: SwalComponent;
  @ViewChild('deleteSuccessRaidSwal', { static: false }) private deleteSuccessRaidSwal: SwalComponent;

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
    { name: 'edit', label: ''}
  ]

  fromRow = 1;
  currentPage = 1;
  pageSize = 1;
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;
  swalOptionsConfirmDelete: {}
  swalOptionsCannotDelete: {}
  swalOptionsDeleteSuccess: {}

  constructor(
    public questService: QuestService,
    private worldService: WorldService,
    private translateService: TranslateService,
    private dialog: MatDialog,
    private raidService: RaidService) {
      this.initSweetAlert();
  }

  ngOnInit() {
    this.worldService.currentWorld$.subscribe(world => {
      this.world = world;
      this.loadRaids();
    });
    // Default RaidModel
    this.raid = new RaidModel('', '', '', 0, 0, null, this.world);
    this.initSweetAlert();
  }

  initSweetAlert(): void {
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
      timer: 5000
    }
  }

  newRaid() {
    this.dialog.open(GamemasterRaidCreateComponent, {
      panelClass: 'dialog-sexy',
      width: '500px',
      data: { raid: this.raid }
    }).afterClosed().subscribe((raidDialogResult: RaidDialogResult) => {
      if (raidDialogResult) {
        this.raidService.createRaid(raidDialogResult.raid).then(() => {
          this.loadRaids();
        });
      }
    });
  }

  editRaid(raid: Raid) {
    const oldQuests = raid.quests.filter(() => true);
    this.dialog.open(GamemasterRaidCreateComponent, {
      panelClass: 'dialog-sexy',
      width: '500px',
      data: { raid: raid }
    }).afterClosed().subscribe((raidDialogResult: RaidDialogResult) => {
      raid = raidDialogResult.raid;

      if (raidDialogResult) {
        // Adding or delete quests from raid (raid: Raid)
        const newQuests = this.questService.identifyNewTasks(oldQuests, raid.quests);
        const deselectedQuests = this.questService.identifyDeselectedTasks(oldQuests, raid.quests);
        newQuests.forEach((value, index) => {
          this.questService.addToRaid(value, raid);
        });
        deselectedQuests.forEach((value, index) => {
          this.questService.deleteFromRaid(value);
        });
        // Finally update raid and refresh site
        this.raidService.updateRaid(raid).then(() => {
          this.loadRaids();
        });
      }
    });
  }

  deleteRaid(raid: Raid) {
    this.raidService.deleteRaid(raid).then(() => {
      this.loadRaids();
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
