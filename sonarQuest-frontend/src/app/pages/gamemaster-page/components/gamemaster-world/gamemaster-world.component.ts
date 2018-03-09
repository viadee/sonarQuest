import { Component, OnInit } from '@angular/core';
import {WorldService} from '../../../../services/world.service';
import {World} from '../../../../Interfaces/World';
import {
  IPageChangeEvent, ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder
} from '@covalent/core';
import {MatDialog} from '@angular/material';
import {EditWorldComponent} from './components/edit-world/edit-world.component';
import {TaskService} from "../../../../services/task.service";
import {QuestService} from "../../../../services/quest.service";
import {AdventureService} from "../../../../services/adventure.service";

@Component({
  selector: 'app-gamemaster-world',
  templateUrl: './gamemaster-world.component.html',
  styleUrls: ['./gamemaster-world.component.css']
})
export class GamemasterWorldComponent implements OnInit {

  data: any[] = [];
  columns: ITdDataTableColumn[] = [
    { name: 'id', label: 'Id'},
    { name: 'name', label: 'Name' },
    { name: 'project', label: 'Projekt'},
    { name: 'active', label: 'Aktiv'},
    { name: 'edit', label: ''},
    { name: 'update', label: 'Update Aufgaben'}
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

  constructor(private worldService: WorldService,
              private questService: QuestService,
              private adventureService: AdventureService,
              private taskService: TaskService,
              private _dataTableService: TdDataTableService,
              private dialog: MatDialog) { }

  ngOnInit() {
      this.loadWorlds();
  }

  loadWorlds(): Promise<void>{
    return this.worldService.getWorlds().then(worlds => {
      this.data = worlds;
      this.filter();
    });
  }

  editWorld(world: World){
    this.dialog.open(EditWorldComponent, {data: world}).afterClosed().subscribe(() => this.loadWorlds())
  }

  updateStandardTasksForWorld(world: World){
    this.taskService.updateStandardTasksForWorld(world).then(()=>{
      this.taskService.refreshTasks();
      this.questService.refreshQuests();
      this.adventureService.refreshAdventures();
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
