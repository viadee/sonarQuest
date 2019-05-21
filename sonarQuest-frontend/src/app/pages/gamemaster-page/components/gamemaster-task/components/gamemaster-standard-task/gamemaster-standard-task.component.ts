import {TranslateService} from '@ngx-translate/core';
import {World} from './../../../../../../Interfaces/World';
import {WorldService} from './../../../../../../services/world.service';
import {Component, OnInit} from '@angular/core';
import {
  IPageChangeEvent,
  ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder
} from '@covalent/core';
import {MatDialog} from '@angular/material';
import {StandardTaskService} from '../../../../../../services/standard-task.service';
import {GamemasterStandardTaskEditComponent} from './components/gamemaster-standard-task-edit/gamemaster-standard-task-edit.component';
import {StandardTask} from '../../../../../../Interfaces/StandardTask';
import {TaskService} from '../../../../../../services/task.service';
import {QuestService} from '../../../../../../services/quest.service';
import {AdventureService} from '../../../../../../services/adventure.service';
import {LoadingService} from '../../../../../../services/loading.service';

@Component({
  selector: 'app-gamemaster-standard-task',
  templateUrl: './gamemaster-standard-task.component.html',
  styleUrls: ['./gamemaster-standard-task.component.css']
})
export class GamemasterStandardTaskComponent implements OnInit {

  data: any[] = [];
  columns: ITdDataTableColumn[] = [
    {name: 'id', label: 'Id', width: 50},
    {name: 'title', label: 'Title', width: 400},
    {name: 'gold', label: 'Gold', width: 50},
    {name: 'xp', label: 'XP', width: 50},
    {name: 'type', label: 'Type'},
    {name: 'severity', label: 'Severity'},
    {name: 'quest.title', label: 'Quest'},
    {name: 'status', label: 'Status'},
    {name: 'edit', label: ''}
  ];

  // Sort / Filter / Paginate variables
  filteredData: any[] = this.data;
  filteredTotal: number = this.data.length;
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'id';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  currentWorld: World;

  constructor(
    private standardTaskService: StandardTaskService,
    private taskService: TaskService,
    private questService: QuestService,
    private adventureService: AdventureService,
    private _dataTableService: TdDataTableService,
    private worldService: WorldService,
    private translateService: TranslateService,
    private dialog: MatDialog,
    private loadingService: LoadingService) {
  }

  ngOnInit() {
    this.translateTable();
    this.worldService.currentWorld$.subscribe(world => {
      this.currentWorld = world
      this.loadTasks();
    })
  }

  private translateTable() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        {name: 'id', label: col_names.ID, width: 50},
        {name: 'title', label: col_names.TITLE, width: 400},
        {name: 'gold', label: col_names.GOLD, width: 50},
        {name: 'xp', label: col_names.XP, width: 50},
        {name: 'type', label: col_names.TYPE},
        {name: 'severity', label: col_names.SEVERITY},
        {name: 'quest.title', label: col_names.QUEST},
        {name: 'status', label: col_names.STATUS},
        {name: 'edit', label: '', width: 70}
      ]
    });
  }

  loadTasks() {
    this.standardTaskService.getStandardTasksForWorld(this.currentWorld).subscribe(tasks => {
      this.data = tasks;
      this.filter();
    });
  }

  editStandardTask(standardTask: StandardTask) {
    this.dialog.open(GamemasterStandardTaskEditComponent, {
      panelClass: 'dialog-sexy',
      width: '500px',
      data: standardTask
    }).afterClosed().subscribe((bool) => {
      if (bool) {
        this.loadTasks();
      }
    });
  }

  updateStandardTasksStatus(){
     const loading = this.loadingService.getLoadingSpinner();
      this.standardTaskService.updateStandardTasksForWorld(this.currentWorld).then(() => {
        this.taskService.refreshTasks(this.currentWorld);
        this.questService.refreshQuests(this.currentWorld);
        this.adventureService.refreshAdventures(this.currentWorld);
        loading.close();
      }).catch(() => loading.close())
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


}
