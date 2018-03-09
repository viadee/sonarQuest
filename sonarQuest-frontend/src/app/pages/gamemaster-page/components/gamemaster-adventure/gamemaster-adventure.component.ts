import { Component, OnInit } from '@angular/core';
import {
  ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder,
  IPageChangeEvent
} from "@covalent/core";
import {MatDialog} from "@angular/material";
import {AdventureService} from "../../../../services/adventure.service";
import {GamemasterAdventureCreateComponent} from "./components/gamemaster-adventure-create/gamemaster-adventure-create.component";
import {QuestService} from "../../../../services/quest.service";

@Component({
  selector: 'app-gamemaster-adventure',
  templateUrl: './gamemaster-adventure.component.html',
  styleUrls: ['./gamemaster-adventure.component.css']
})
export class GamemasterAdventureComponent implements OnInit {

  data: any[] = [];
  columns: ITdDataTableColumn[] = [
    { name: 'id', label: 'Id'},
    { name: 'title', label: 'Titel', width:200 },
    { name: 'gold', label: 'Gold'},
    { name: 'xp', label: 'XP'},
    { name: 'story', label: 'Erzählung'},
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

  constructor(private _dataTableService: TdDataTableService,
              private questService: QuestService,
              private dialog: MatDialog, private adventureService: AdventureService) { }

  ngOnInit() {
    this.loadAdventures();
  }

  loadAdventures(){
    return this.adventureService.getAdventures().subscribe(adventures => {
      this.data = adventures;
      this.filter();
    });
  }

  newAdventure(){
    this.dialog.open(GamemasterAdventureCreateComponent,{width:"500px"}).afterClosed().subscribe(()=>{
      this.loadAdventures();
      this.questService.refreshQuests();
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
