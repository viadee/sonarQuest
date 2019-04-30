import { TranslateService } from '@ngx-translate/core';
import { WorldService } from '../../../../services/world.service';
import { World } from '../../../../Interfaces/World';
import { GamemasterQuestEditComponent } from './components/gamemaster-quest-edit/gamemaster-quest-edit.component';

import { Quest } from './../../../../Interfaces/Quest';
import { Component, OnInit, ViewChild } from '@angular/core';
import {
  IPageChangeEvent,
  ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder
} from '@covalent/core';
import { MatDialog } from '@angular/material';
import { QuestService } from '../../../../services/quest.service';
import { GamemasterQuestCreateComponent } from './components/gamemaster-quest-create/gamemaster-quest-create.component';
import { TaskService } from '../../../../services/task.service';
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';

@Component({
  selector: 'app-gamemaster-quest',
  templateUrl: './gamemaster-quest.component.html',
  styleUrls: ['./gamemaster-quest.component.css'],
})
export class GamemasterQuestComponent implements OnInit {
  @ViewChild('deleteSuccessQuestSwal') private deleteSuccessQuestSwal: SwalComponent;

  currentWorld: World;
  data: any[] = [];
  columns: ITdDataTableColumn[] = [
    { name: 'id', label: 'Id' },
    { name: 'title', label: 'Titel' },
    { name: 'visible', label: 'Sichtbar' },
    { name: 'gold', label: 'Gold' },
    { name: 'xp', label: 'XP' },
    { name: 'world.name', label: 'Welt' },
    { name: 'adventure.title', label: 'Abenteuer' },
    { name: 'status', label: 'Status' },
    { name: 'participants', label: 'Spieler' },
    { name: 'edit', label: '' }
  ];

  swalOptionsConfirmDelete: {}
  swalOptionsDeleteSuccess: {}
  // Sort / Filter / Paginate variables
  filteredData: any[] = this.data;
  filteredTotal: number = this.data.length;
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'title';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  constructor(
    private questService: QuestService,
    private taskService: TaskService,
    private worldService: WorldService,
    private _dataTableService: TdDataTableService,
    private translateService: TranslateService,
    private dialog: MatDialog) {
  }

  ngOnInit() {
    this.translateTable();
    this.init();
    this.worldService.onWorldChange().subscribe(() => this.init());

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

  private init() {
    if (this.worldService.getCurrentWorld()) {
      this.currentWorld = this.worldService.getCurrentWorld();
      this.subscribeToQuests();
    }
  }

  private translateTable() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        { name: 'image', label: '' },
        { name: 'title', label: col_names.TITLE },
        { name: 'visible', label: col_names.VISIBLE },
        { name: 'gold', label: col_names.GOLD, width: 40 },
        { name: 'xp', label: col_names.XP, width: 40 },
        { name: 'adventure.title', label: col_names.ADVENTURE },
        { name: 'status', label: col_names.STATUS },
        { name: 'participants', label: col_names.PLAYERS },
        { name: 'edit', label: '', width: 100 }
      ]
    });
  }

  private subscribeToQuests() {
    return this.questService.getQuestsForWorld(this.currentWorld).subscribe(quests => {
      this.data = quests;
      this.filter();
    });
  }

  newQuest() {
    this.dialog.open(GamemasterQuestCreateComponent, { panelClass: 'dialog-sexy', width: '500px' }).afterClosed()
      .subscribe(bool => {
        if (bool) {
          this.update();
        }
      });
  }

  editQuest(quest: Quest) {
    this.questService.getQuest(quest.id).then(loadedQuest => {
      this.dialog.open(GamemasterQuestEditComponent, {
        panelClass: 'dialog-sexy',
        data: loadedQuest,
        width: '500px'
      }).afterClosed().subscribe((bool) => {
        if (bool) {
          this.update();
        }
      });
    });
  }

  deleteQuest(quest: Quest) {

    this.questService.deleteQuest(quest).then(() => {

      this.update();
      this.deleteSuccessQuestSwal.show();
    });

  }

  update() {
    this.questService.getQuestsForWorld(this.currentWorld);
    this.taskService.refreshTasks(this.currentWorld);
  }

  sort(sortEvent: ITdDataTableSortChangeEvent): void {
    this.sortBy = sortEvent.name;
    this.sortOrder = sortEvent.order;
    this.filter();
  }

  search(searchTerm: string): void {
    this.searchTerm = searchTerm;
    this.filter();
  }

  page(pagingEvent: IPageChangeEvent): void {
    this.fromRow = pagingEvent.fromRow;
    this.currentPage = pagingEvent.page;
    this.pageSize = pagingEvent.pageSize;
    this.filter();
  }

  filter(): void {
    let newData: any[] = this.data;
    const excludedColumns: string[] = this.columns
      .filter((column: ITdDataTableColumn) => {
        return ((column.filter === undefined && column.hidden === true) ||
          (column.filter !== undefined && column.filter === false));
      }).map((column: ITdDataTableColumn) => {
        return column.name;
      });
    newData = this._dataTableService.filterData(newData, this.searchTerm, true, excludedColumns);
    this.filteredTotal = newData.length;
    newData = this._dataTableService.sortData(newData, this.sortBy, this.sortOrder);
    newData = this._dataTableService.pageData(newData, this.fromRow, this.currentPage * this.pageSize);
    this.filteredData = newData;
  }

  translate(messageString: string): string {
    let msg = '';
    this.translateService.get(messageString).subscribe(translateMsg => msg = translateMsg);
    return msg;
  }
}
