import { GamemasterQuestEditComponent } from './components/gamemaster-quest-edit/gamemaster-quest-edit.component';

import { Quest } from './../../../../Interfaces/Quest';
import { Component, OnInit } from '@angular/core';
import {
  IPageChangeEvent,
  ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder
} from "@covalent/core";
import {MatDialog} from "@angular/material";
import {QuestService} from "../../../../services/quest.service";
import {GamemasterQuestCreateComponent} from "./components/gamemaster-quest-create/gamemaster-quest-create.component";
import {StandardTaskService} from "../../../../services/standard-task.service";
import {SpecialTaskService} from "../../../../services/special-task.service";
import {TaskService} from "../../../../services/task.service";

@Component({
  selector: 'app-gamemaster-quest',
  templateUrl: './gamemaster-quest.component.html',
  styleUrls: ['./gamemaster-quest.component.css']
})
export class GamemasterQuestComponent implements OnInit {

  data: any[] = [];
  columns: ITdDataTableColumn[] = [
    { name: 'id', label: 'Id'},
    { name: 'title', label: 'Titel' },
    { name: 'gold', label: 'Gold'},
    { name: 'xp', label: 'XP'},
    { name: 'story', label: 'Erzählung'},
    { name: 'world.name', label: 'Welt'},
    { name: 'adventure.title', label: 'Abenteuer'},
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
    private questService: QuestService,
    private taskService: TaskService,
    private _dataTableService: TdDataTableService,
    private dialog: MatDialog) { }

  ngOnInit() {
    this.subscribeToQuests();
  }

  private subscribeToQuests(){
    return this.questService.getQuests().subscribe(quests => {
     this.data = quests;
     this.filter();
    });
  }

  newQuest(){
    this.dialog.open(GamemasterQuestCreateComponent,{width:"500px"}).afterClosed().subscribe(()=>{
      this.questService.getQuests()
      this.taskService.refreshTasks();
    })
  }

  editQuest(quest: Quest){
    this.dialog.open(GamemasterQuestEditComponent,{data: quest,width:"500px"}).afterClosed().subscribe(()=>{
      this.questService.getQuests()
      this.taskService.refreshTasks();
    })
  }

  deleteQuest(quest: Quest){
    this.questService.deleteQuest(quest).then(()=>{
      this.questService.getQuests()
      this.taskService.refreshTasks();
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
