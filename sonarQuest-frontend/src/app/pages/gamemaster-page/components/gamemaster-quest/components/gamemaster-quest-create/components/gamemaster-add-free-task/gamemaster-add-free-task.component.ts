import { Component, Inject, OnInit } from '@angular/core';
import { Task } from 'app/Interfaces/Task';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { GamemasterQuestCreateComponent } from '../../gamemaster-quest-create.component';
import { SonarCubeService } from '../../../../../../../../services/sonar-cube.service';
import { StandardTask } from '../../../../../../../../Interfaces/StandardTask';
import { SpecialTask } from '../../../../../../../../Interfaces/SpecialTask';
import { StandardTaskService } from '../../../../../../../../services/standard-task.service';
import { SpecialTaskService } from '../../../../../../../../services/special-task.service';
import { IPageChangeEvent, ITdDataTableColumn, TdDataTableService, TdDataTableSortingOrder } from '@covalent/core';
import { TranslateService } from '@ngx-translate/core';

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
    { name: 'scoring', label: 'Scoring', width: 25 },
    { name: 'open_issue', label: '' },
    { name: 'add_task', label: '' },

  ];
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
    private sonarCubeService: SonarCubeService,
    private dialogRef: MatDialogRef<GamemasterQuestCreateComponent>,
    private standardTaskService: StandardTaskService,
    private specialTaskService: SpecialTaskService,
    private _dataTableService: TdDataTableService,
    private translateService: TranslateService,
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
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.freeStandardTasksColumns = [
        { name: 'title', label: col_names.TITLE, width: 600 },
        { name: 'severity', label: col_names.SEVERITY },
        { name: 'gold', label: col_names.GOLD },
        { name: 'xp', label: col_names.XP },
        { name: 'scoring', label: col_names.SCORING, width: 25 },
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

  protected addTask(task: Task) {
    this.dialogRef.close(task);
  }

  protected openIssue(task: Task) {
    this.sonarCubeService.getIssueLink(task.key, task.world)
      .then(link => window.open(link, '_blank'));
  }

  protected pageStandardTask(pagingEvent: IPageChangeEvent): void {
    this.fromRowStandardTasks = pagingEvent.fromRow;
    this.currentPageStandardTasks = pagingEvent.page;
    this.pageSize = pagingEvent.pageSize;
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
}
