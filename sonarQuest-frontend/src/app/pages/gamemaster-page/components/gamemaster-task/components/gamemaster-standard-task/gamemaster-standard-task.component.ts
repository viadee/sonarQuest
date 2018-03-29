import { World } from './../../../../../../Interfaces/World';
import { WorldService } from './../../../../../../services/world.service';
import { Component, OnInit } from '@angular/core';
import {
  IPageChangeEvent,
  ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder
} from "@covalent/core";
import {MatDialog} from "@angular/material";
import {StandardTaskService} from "../../../../../../services/standard-task.service";
import {GamemasterStandardTaskEditComponent} from "./components/gamemaster-standard-task-edit/gamemaster-standard-task-edit.component";

@Component({
  selector: 'app-gamemaster-standard-task',
  templateUrl: './gamemaster-standard-task.component.html',
  styleUrls: ['./gamemaster-standard-task.component.css']
})
export class GamemasterStandardTaskComponent implements OnInit {

  data: any[] = [];
  columns: ITdDataTableColumn[] = [
    { name: 'id', label: 'Id',width:50},
    { name: 'title', label: 'Titel', width:400},
    { name: 'gold', label: 'Gold', width:50},
    { name: 'xp', label: 'XP', width:50},
    { name: 'type', label: 'Typ'},
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

  currentWorld: World;

  constructor(
    private standardTaskService: StandardTaskService,
    private _dataTableService: TdDataTableService,
    private worldService: WorldService,
    private dialog: MatDialog) { }

  ngOnInit() {
    this.worldService.currentWorld$.subscribe(w => {
      this.currentWorld = w
      if (w) this.loadTasks();
    })
  }

  loadTasks(){
    this.standardTaskService.getStandardTasksForWorld(this.currentWorld).subscribe(tasks => {
      this.data = tasks;
      this.filter();
    });
  }

  editStandardTask(standardTask){
    this.dialog.open(GamemasterStandardTaskEditComponent,{width:"500px", data: standardTask}).afterClosed().subscribe(()=>this.loadTasks());
  }

  refreshStandardTasks(){
    this.standardTaskService.refreshStandardTask(this.currentWorld).then(()=>{
      this.standardTaskService.getStandardTasksForWorld(this.currentWorld);
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
