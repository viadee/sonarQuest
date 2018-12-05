import {TranslateService} from '@ngx-translate/core';
import {World} from './../../../../Interfaces/World';
import {ViewParticipatedQuestComponent} from './components/view-participated-quest/view-participated-quest.component';
import {ParticipationService} from './../../../../services/participation.service';
import {MatDialog} from '@angular/material';
import {WorldService} from './../../../../services/world.service';
import {QuestService} from './../../../../services/quest.service';
import {
  ITdDataTableColumn,
  TdDataTableSortingOrder,
  TdDataTableService,
  ITdDataTableSortChangeEvent,
  IPageChangeEvent
} from '@covalent/core';
import {Quest} from './../../../../Interfaces/Quest';
import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-participated-quests',
  templateUrl: './participated-quests.component.html',
  styleUrls: ['./participated-quests.component.css']
})
export class ParticipatedQuestsComponent implements OnInit {

  participatedQuests: Quest[];
  columns: ITdDataTableColumn[] = [
    {name: 'title', label: 'Title'},
    {name: 'gold', label: 'Gold'},
    {name: 'xp', label: 'XP'},
    {name: 'adventure.title', label: 'Adventure'},
    {name: 'status', label: 'Status'},
    {name: 'edit', label: '', width: {min: 45}}
  ];

  // Sort / Filter / Paginate variables
  filteredData: any[];
  filteredTotal: number;
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'title';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  currentWorld: World;

  constructor(
    private questService: QuestService,
    private worldService: WorldService,
    private participationService: ParticipationService,
    private _dataTableService: TdDataTableService,
    private translateService: TranslateService,
    private dialog: MatDialog) {
    this.participationService.participationUpdated$.subscribe(() => this.ngOnInit());
  }

  ngOnInit() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        {name: 'title', label: col_names.TITLE},
        {name: 'gold', label: col_names.GOLD},
        {name: 'xp', label: col_names.XP},
        {name: 'adventure.title', label: col_names.ADVENTURE},
        {name: 'status', label: col_names.STATUS, width: {min: 45}},
        {name: 'edit', label: ''}]
    });
    if (this.worldService.getCurrentWorld()) {
      this.init();
    }
    this.worldService.onWorldChange().subscribe(() => this.init());
  }

  private init() {
    this.currentWorld = this.worldService.getCurrentWorld();
    this.loadQuests();
  }

  loadQuests() {
    if (this.currentWorld) {
      return this.questService.getAllParticipatedQuestsForWorldAndUser(this.currentWorld).then(quests => {
        this.participatedQuests = quests;
      }).then(() => {
        this.filter()
      });
    }
  }

  viewQuest(quest: Quest) {
    this.questService.getQuest(quest.id).then(loadedQuest => {
      this.dialog.open(ViewParticipatedQuestComponent, {
        panelClass: 'dialog-sexy',
        data: loadedQuest,
        width: '500px'
      }).afterClosed().subscribe(() => {
      });
    });
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
    let newData: any[] = this.participatedQuests;
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
}
