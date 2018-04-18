import { TranslateService } from '@ngx-translate/core';
import { WorldService } from './../../../../services/world.service';
import { GamemasterAdventureEditComponent } from './components/gamemaster-adventure-edit/gamemaster-adventure-edit.component';
import { Adventure } from './../../../../Interfaces/Adventure';
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
import { World } from '../../../../Interfaces/World';

@Component({
  selector: 'app-gamemaster-adventure',
  templateUrl: './gamemaster-adventure.component.html',
  styleUrls: ['./gamemaster-adventure.component.css']
})
export class GamemasterAdventureComponent implements OnInit {

  currentWorld: World;
  data: any[] = [];
  columns: ITdDataTableColumn[] = [
    { name: 'title', label: 'Title', width:200 },
    { name: 'gold', label: 'Gold'},
    { name: 'xp', label: 'XP'},
    { name: 'story', label: 'Story'},
    { name: 'status', label: 'Status'},
    { name: 'edit', label: '', width: 70}
  ]

  // Sort / Filter / Paginate variables
  filteredData: any[] = this.data
  filteredTotal: number = this.data.length
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'title';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  constructor(private _dataTableService: TdDataTableService,
  	private questService: QuestService,
    private worldService: WorldService,
    private translateService: TranslateService,
    private dialog: MatDialog, 
    private adventureService: AdventureService) { }

  ngOnInit() {
  	this.translateTable();
    this.worldService.currentWorld$.subscribe(w => {
      this.currentWorld = w
      if (w) this.loadAdventures();
    })
  }
  
  translateTable(){
  	  this.translateService.get("TABLE.COLUMNS").subscribe((col_names) => {
      this.columns=[
		    { name: 'title', label: col_names.TITLE, width:200 },
		    { name: 'gold', label: col_names.GOLD, width: 40},
		    { name: 'xp', label: col_names.XP, width: 40},
		    { name: 'story', label: col_names.STORY},
		    { name: 'status', label: col_names.STATUS},
		    { name: 'edit', label: '', width: 100}
    	]
    });
  }  

  loadAdventures(){
    return this.adventureService.getAdventuresForWorld(this.currentWorld).subscribe(adventures => {
      this.data = adventures;
      this.filter();
    });
  }

  newAdventure(){
    this.dialog.open(GamemasterAdventureCreateComponent,{panelClass:"dialog-sexy", width:"500px"}).afterClosed().subscribe((bool)=>{
      if (bool){
        this.loadAdventures();
        this.questService.refreshQuests(this.currentWorld);
      }
    });
  }

  editAdventure(adventure: Adventure){
    this.dialog.open(GamemasterAdventureEditComponent,{panelClass:"dialog-sexy", data: adventure, width: "500px"}).afterClosed().subscribe((bool)=>{
      if (bool){
        this.loadAdventures();
        this.questService.refreshQuests(this.currentWorld);
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
