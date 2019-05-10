import {Component, Inject, OnInit} from '@angular/core';
import {Task} from 'app/Interfaces/Task';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {GamemasterQuestCreateComponent} from '../../gamemaster-quest-create.component';
import {SonarQubeService} from '../../../../../../../../services/sonar-qube.service';
import {StandardTask} from '../../../../../../../../Interfaces/StandardTask';
import {SpecialTask} from '../../../../../../../../Interfaces/SpecialTask';
import {StandardTaskService} from '../../../../../../../../services/standard-task.service';
import {SpecialTaskService} from '../../../../../../../../services/special-task.service';
import { IPageChangeEvent, ITdDataTableColumn, TdDataTableService, TdDataTableSortingOrder, ITdDataTableSortChangeEvent } from '@covalent/core';
import { TranslateService } from '@ngx-translate/core';
import { UserSkillService } from 'app/services/user-skill.service';

@Component({
  selector: 'app-gamemaster-add-free-task',
  templateUrl: './gamemaster-add-free-task.component.html',
  styleUrls: ['./gamemaster-add-free-task.component.css']
})
export class GamemasterAddFreeTaskComponent implements OnInit {



  freeStandardTasksColumns: ITdDataTableColumn[] = [
    { name: 'title', label: 'Title', width: 600 },
    { name: 'severity', label: 'Severity' },
    { name: 'gold', label: 'Gold' },
    { name: 'xp', label: 'XP' },
    { name: 'userSkillScoring', label: 'Scoring', width: 25, tooltip: '' },
    { name: 'open_issue', label: '' },
    { name: 'add_task', label: '' },

  ];
  filteredData: any[];
  filteredTotal: number;
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  sortBy = 'title';
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;
  selectedRows: any[] = [];
  fromRowStandardTasks = 1;
  currentPageStandardTasks = 1;
  filteredStandardTasks: StandardTask[];

  freeSpecialTasksColumns: ITdDataTableColumn[] = [
    { name: 'title', label: 'Title' },
    { name: 'gold', label: 'Gold' },
    { name: 'xp', label: 'XP' },
    { name: 'message', label: 'Message', width: 500 },
    { name: 'add_task', label: '' }
  ];
  fromRowSpecialTasks = 1;
  currentPageSpecialTasks = 1;
  filteredSpecialTasks: StandardTask[];

  pageSize = 8;

  public freeStandardTasks: StandardTask[];
  public freeSpecialTasks: SpecialTask[];

  constructor(
    private sonarQubeService: SonarQubeService,
    private dialogRef: MatDialogRef<GamemasterQuestCreateComponent>,
    private standardTaskService: StandardTaskService,
    private specialTaskService: SpecialTaskService,
    private _dataTableService: TdDataTableService,
    private translateService: TranslateService,
    private userSkillService: UserSkillService,
    @Inject(MAT_DIALOG_DATA) public data
  ) {
  }

  ngOnInit() {
    this.translateTable();
    this.specialTaskService.getFreeSpecialTasksForWorldExcept(this.data[0], this.data[1]).then(freeTasks => {
      this.freeSpecialTasks = freeTasks;
      this.pageSpecialTaskData();
    });
    this.standardTaskService.getFreeStandardTasksForWorldExcept(this.data[0], this.data[1]).then(freeTasks => {
      this.freeStandardTasks = freeTasks;
      this.pageStandardTaskData();
    });
  }

  private translateTable() {
    this.translateService.get('TABLE').subscribe((table) => {
      this.freeStandardTasksColumns = [
        { name: 'title', label: table.COLUMNS.TITLE, width: 570 },
        { name: 'severity', label: table.COLUMNS.SEVERITY },
        { name: 'gold', label: table.COLUMNS.GOLD },
        { name: 'xp', label: table.COLUMNS.XP },
        { name: 'userSkillScoring', label: table.COLUMNS.SCORING, width: 100, tooltip: table.TOOLTIP.TEAM_SCORING },
        { name: 'open_issue', label: '' },
        { name: 'add_task', label: '' },

      ]
    });
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.freeSpecialTasksColumns = [
        { name: 'title', label: col_names.TITLE },
        { name: 'gold', label: col_names.GOLD },
        { name: 'xp', label: col_names.XP },
        { name: 'message', label: col_names.MISSION, width: 500 },
        { name: 'add_task', label: '' }
      ]
    });
  }
  sort(sortEvent: ITdDataTableSortChangeEvent): void {
    this.sortBy = sortEvent.name;
    this.sortOrder = sortEvent.order;
    this.filterStandardTask();
  }

  filterStandardTask(): void {
    let newData: StandardTask[] = this.freeStandardTasks;
    const excludedColumns: string[] = this.freeStandardTasksColumns
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
    this.filteredStandardTasks = newData;
  }
  protected addTask(task: Task) {
    this.dialogRef.close(task);
  }

  protected openIssue(task: Task) {
    this.sonarQubeService.getIssueLink(task.key, task.world)
      .then(link => window.open(link, '_blank'));
  }

  protected pageStandardTask(pagingEvent: IPageChangeEvent): void {
    const newData: any[] = this.freeStandardTasks;
    this.fromRowStandardTasks = pagingEvent.fromRow;
    this.currentPageStandardTasks = pagingEvent.page;
    this.pageSize = pagingEvent.pageSize;
    this.freeStandardTasks = this._dataTableService.sortData(newData, this.sortBy, this.sortOrder);
    this.pageStandardTaskData();
  }

  private pageStandardTaskData(): void {
    let newData: any[] = this.freeStandardTasks;
    newData = this._dataTableService.pageData(newData, this.fromRowStandardTasks, this.currentPageStandardTasks * this.pageSize);
    this.filteredStandardTasks = newData;
  }

  protected pageSpecialTask(pagingEvent: IPageChangeEvent): void {
    this.fromRowSpecialTasks = pagingEvent.fromRow;
    this.currentPageSpecialTasks = pagingEvent.page;
    this.pageSize = pagingEvent.pageSize;
    this.pageSpecialTaskData();
  }

  private pageSpecialTaskData(): void {
    let newData: any[] = this.freeSpecialTasks;
    newData = this._dataTableService.pageData(newData, this.fromRowSpecialTasks, this.currentPageSpecialTasks * this.pageSize);
    this.filteredSpecialTasks = newData;
  }

  createRange(value: number): number[] {
  return this.userSkillService.getNumberOfIcons(value);
  }
}
