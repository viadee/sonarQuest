import {TranslateService} from '@ngx-translate/core';
import {ParticipationService} from './../../../../services/participation.service';
import {WorldService} from './../../../../services/world.service';
import {MatDialog} from '@angular/material';
import {QuestService} from './../../../../services/quest.service';
import {
  ITdDataTableColumn,
  TdDataTableSortingOrder,
  TdDataTableService,
  ITdDataTableSortChangeEvent,
  IPageChangeEvent
} from '@covalent/core';
import {Component, OnInit} from '@angular/core';
import {Quest} from '../../../../Interfaces/Quest';
import {ViewAvailableQuestComponent} from './components/view-available-quest/view-available-quest.component';
import {World} from '../../../../Interfaces/World';

@Component({
  selector: 'app-available-quests',
  templateUrl: './available-quests.component.html',
  styleUrls: ['./available-quests.component.css']
})
export class AvailableQuestsComponent implements OnInit {

  availableQuests: Quest[];
  columns: ITdDataTableColumn[] = [
    {name: 'title', label: 'Title'},
    {name: 'gold', label: 'Gold'},
    {name: 'xp', label: 'XP'},
    {name: 'adventure.title', label: 'Adventure'},
    {name: 'status', label: 'Status'},
    {name: 'edit', label: ''}
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
    private _dataTableService: TdDataTableService,
    private participationService: ParticipationService,
    private translateService: TranslateService,
    private dialog: MatDialog) {
  }

  ngOnInit() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        {name: 'title', label: col_names.TITLE},
        {name: 'gold', label: col_names.GOLD},
        {name: 'xp', label: col_names.XP},
        {name: 'adventure.title', label: col_names.ADVENTURE},
        {name: 'status', label: col_names.STATUS},
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
      return this.questService.getAllAvailableQuestsForWorldAndUser(this.currentWorld).then(quests => {
       quests.forEach(element => {
         console.log(element);
       });
        this.availableQuests = quests;
      }).then(() => {
        this.filter()
      });
    }
  }

  viewQuest(quest: Quest) {
    this.questService.getQuest(quest.id).then(loadedQuest => {
      this.dialog.open(ViewAvailableQuestComponent, {panelClass: 'dialog-sexy', data: loadedQuest, width: '500px'})
        .afterClosed().subscribe(() => {
        this.loadQuests();
        this.participationService.announceParticipationUpdate()
      });
    });
  }

  participateInQuest(quest: Quest) {
    return this.participationService.createParticipation(quest)
      .then((msg) => {
        this.loadQuests();
        this.participationService.announceParticipationUpdate();
      })
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
    let newData: any[] = this.availableQuests;
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
