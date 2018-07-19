import {TranslateService} from '@ngx-translate/core';
import {World} from '../../../../../../Interfaces/World';
import {WorldService} from '../../../../../../services/world.service';
import {Component, OnInit} from '@angular/core';
import {
  IPageChangeEvent,
  ITdDataTableColumn,
  ITdDataTableSortChangeEvent,
  TdDataTableService,
  TdDataTableSortingOrder
} from '@covalent/core';
import {MatDialog, MatSnackBar} from '@angular/material';
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
    private snackBar: MatSnackBar,
    private loadingService: LoadingService) {
  }

  ngOnInit() {
    this.translateTable();
    this.init();
    this.worldService.onWorldChange().subscribe(() => this.init());
  }

  private init() {
    if (this.worldService.getCurrentWorld()) {
      this.currentWorld = this.worldService.getCurrentWorld();
      this.loadTasks();
    }
  }

  private translateTable() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        {name: 'id', label: col_names.ID, width: 50},
        {name: 'title', label: col_names.TITLE, width: 400},
        {name: 'gold', label: col_names.GOLD, width: 50},
        {name: 'xp', label: col_names.XP, width: 50},
        {name: 'type', label: col_names.TYPE},
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

  updateStandardTasksStatus() {
    const loading = this.loadingService.getLoadingSpinner();
    this.standardTaskService.updateStandardTasksForWorld(this.worldService.getCurrentWorld()).then(() => {
      this.taskService.refreshTasks(this.worldService.getCurrentWorld());
      this.questService.refreshQuests(this.worldService.getCurrentWorld());
      this.adventureService.refreshAdventures(this.worldService.getCurrentWorld());
      loading.close();
    }).catch(() => loading.close())
  }

  updateStandardTasksScores(worldName: String) {
    this.snackBar.open(this.translateService.instant('TASK.UPDATE_SCORES_STARTED'), null, {duration: 10000});
    this.standardTaskService.updateStandardTasksScoresForWorld(this.worldService.getCurrentWorld()).then((updated) => {
      if (updated) {
        this.taskService.refreshTasks(this.worldService.getCurrentWorld());
        this.questService.refreshQuests(this.worldService.getCurrentWorld());
        this.adventureService.refreshAdventures(this.worldService.getCurrentWorld());
        this.snackBar.open(this.translateService.instant('TASK.UPDATE_SCORES_FINISHED', {world: worldName}), null, {duration: 10000});
      } else {
        this.snackBar.open(this.translateService.instant('TASK.UPDATE_SCORES_ALREADY_RUNNING'), null, {duration: 10000});
      }
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
