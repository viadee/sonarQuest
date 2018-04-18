import { TranslateService } from '@ngx-translate/core';
import { World } from './../../../../../../Interfaces/World';
import { WorldService } from './../../../../../../services/world.service';
import { Component, OnInit } from '@angular/core';
import {
  IPageChangeEvent,
  ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder
} from "@covalent/core";
import {TaskService} from "../../../../../../services/task.service";
import {MatDialog} from "@angular/material";
import {SpecialTaskService} from "../../../../../../services/special-task.service";
import {GamemasterSpecialTaskCreateComponent} from "./components/gamemaster-special-task-create/gamemaster-special-task-create.component";
import {GamemasterSpecialTaskEditComponent} from "./components/gamemaster-special-task-edit/gamemaster-special-task-edit.component";
import {QuestService} from "../../../../../../services/quest.service";
import {AdventureService} from "../../../../../../services/adventure.service";

@Component({
  selector: 'app-gamemaster-special-task',
  templateUrl: './gamemaster-special-task.component.html',
  styleUrls: ['./gamemaster-special-task.component.css']
})
export class GamemasterSpecialTaskComponent implements OnInit {

  currentWorld: World;
  data: any[] = [];
  columns: ITdDataTableColumn[] = [
    { name: 'id', label: 'Id'},
    { name: 'title', label: 'Title',width: 200 },
    { name: 'gold', label: 'Gold'},
    { name: 'xp', label: 'XP'},
    { name: 'message', label: 'Mission'},
    { name: 'quest.title', label: 'Quest'},
    { name: 'status', label: 'Status'},
    { name: 'edit', label: ''}
  ]

  // Sort / Filter / Paginate variables
  filteredData: any[] = this.data
  filteredTotal: number = this.data.length
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'id';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  constructor(
    private specialTaskService: SpecialTaskService,
    private questService: QuestService,
    private adventureService: AdventureService,
    private _dataTableService: TdDataTableService,
    private worldService: WorldService,
    private translateService: TranslateService,
    private dialog: MatDialog) { }

  ngOnInit() {
    this.translateTable();
    this.worldService.currentWorld$.subscribe(w => {
      this.currentWorld = w
      if (w) this.loadTasks();
    })
  }
    
  private translateTable(){
      this.translateService.get("TABLE.COLUMNS").subscribe((col_names) => {
      this.columns=[
            { name: 'id', label: col_names.ID},
            { name: 'title', label: col_names.TITLE, width: 200 },
            { name: 'gold', label: col_names.GOLD},
            { name: 'xp', label: col_names.XP},
            { name: 'message', label: col_names.MISSION},
            { name: 'quest.title', label: col_names.QUEST},
            { name: 'status', label: col_names.STATUS},
            { name: 'edit', label: '', width: 70}
        ]
    });
  }    

  loadTasks(){
    return this.specialTaskService.getSpecialTasksForWorld(this.currentWorld).subscribe(tasks => {
      this.data = tasks;
      this.filter();
    });
  }

  newSpecialTask(){
    this.dialog.open(GamemasterSpecialTaskCreateComponent,{panelClass: 'dialog-sexy', data: this.currentWorld, width:"500px"}).afterClosed().subscribe((bool)=> {
      if (bool) this.loadTasks()
    });
  }

  editSpecialTask(specialTask){
    this.dialog.open(GamemasterSpecialTaskEditComponent,{width:"500px",data:specialTask}).afterClosed().subscribe(()=>this.loadTasks());
  }

  deleteSpecialTask(specialTask){
    this.specialTaskService.deleteSpecialTask(specialTask).then(()=>this.loadTasks())
  }

  solveSpecialTask(specialTask){
    this.specialTaskService.solveSpecialTask(specialTask).then(()=>{
      this.loadTasks();
      this.questService.refreshQuests(this.currentWorld);
      this.adventureService.refreshAdventures(this.currentWorld);
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
