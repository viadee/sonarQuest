import { TranslateService } from '@ngx-translate/core';
import { WorldService } from './../../../../services/world.service';
import { GamemasterAdventureEditComponent } from './components/gamemaster-adventure-edit/gamemaster-adventure-edit.component';
import { Adventure } from './../../../../Interfaces/Adventure';
import { Component, OnInit, OnChanges, Input, SimpleChanges, ViewChild } from '@angular/core';
import {
  ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder,
  IPageChangeEvent
} from '@covalent/core';
import { MatDialog } from '@angular/material';
import { AdventureService } from '../../../../services/adventure.service';
import { GamemasterAdventureCreateComponent } from './components/gamemaster-adventure-create/gamemaster-adventure-create.component';
import { QuestService } from '../../../../services/quest.service';
import { World } from '../../../../Interfaces/World';
import { TaskService } from 'app/services/task.service';
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';

@Component({
  selector: 'app-gamemaster-adventure',
  templateUrl: './gamemaster-adventure.component.html',
  styleUrls: ['./gamemaster-adventure.component.css']
})
export class GamemasterAdventureComponent implements OnInit {
  @Input() isSelected: boolean;
  @ViewChild('deleteSuccessAdventureSwal') private deleteSuccessAdventureSwal: SwalComponent;


  currentWorld: World;
  data: any[] = [];
  columns: ITdDataTableColumn[] = [
    { name: 'title', label: 'Title', width: 200 },
    { name: 'gold', label: 'Gold' },
    { name: 'xp', label: 'XP' },
    { name: 'story', label: 'Story' },
    { name: 'status', label: 'Status' },
    { name: 'edit', label: '', width: 70 }
  ]

  // Sort / Filter / Paginate variables
  filteredData: any[] = this.data
  filteredTotal: number = this.data.length
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'title';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;
  freeQuestsAvailable: boolean;

  swalOptionsConfirmDelete: {}
  swalOptionsDeleteSuccess: {}

  constructor(private _dataTableService: TdDataTableService,
    private questService: QuestService,
    private worldService: WorldService,
    private translateService: TranslateService,
    private dialog: MatDialog,
    private taskService: TaskService,
    private adventureService: AdventureService) {
  }

  ngOnInit() {
    this.translateTable();
    this.worldService.currentWorld$.subscribe(world => {
      this.currentWorld = world
      this.loadAdventures();
      this.questService.getFreeQuestsForWorld(this.currentWorld)
        .then(quests => quests.length > 0 ? this.freeQuestsAvailable = true : this.freeQuestsAvailable = false);
    })

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

  translateTable() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        { name: 'title', label: col_names.TITLE, width: 200 },
        { name: 'gold', label: col_names.GOLD, width: 40 },
        { name: 'xp', label: col_names.XP, width: 40 },
        { name: 'story', label: col_names.STORY },
        { name: 'status', label: col_names.STATUS },
        { name: 'edit', label: '', width: 100 }
      ]
    });
  }

  loadAdventures() {
    return this.adventureService.getAdventuresForWorld(this.currentWorld).subscribe(adventures => {
      this.data = adventures;
      this.filter();
    });
  }

  newAdventure() {
    this.dialog.open(GamemasterAdventureCreateComponent, { panelClass: 'dialog-sexy', width: '500px' }).afterClosed().subscribe((bool) => {
      if (bool) {
        this.loadAdventures();
        this.questService.refreshQuests(this.currentWorld);
      }
    });
  }

  editAdventure(adventure: Adventure) {
    this.dialog.open(GamemasterAdventureEditComponent, {
      panelClass: 'dialog-sexy',
      data: adventure,
      width: '500px'
    }).afterClosed().subscribe((bool) => {
      if (bool) {
        this.loadAdventures();
        this.questService.refreshQuests(this.currentWorld);
      }
    })
  }

  deleteAdventure(adventure: Adventure) {
      this.adventureService.deleteAdventure(adventure).then(() => {
        this.update();
        this.deleteSuccessAdventureSwal.show();
      });
  }

  update() {
    this.taskService.refreshTasks(this.currentWorld);
    this.adventureService.refreshAdventures(this.currentWorld);
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
