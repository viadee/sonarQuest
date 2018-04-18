import { GamemasterArtefactEditComponent } from './components/gamemaster-artefact-edit/gamemaster-artefact-edit.component';
import { MatDialog } from '@angular/material';
import { GamemasterArtefactCreateComponent } from './components/gamemaster-artefact-create/gamemaster-artefact-create.component';
import { ITdDataTableSortChangeEvent, IPageChangeEvent, ITdDataTableColumn, TdDataTableSortingOrder, TdDataTableService } from '@covalent/core';
import { ArtefactService } from './../../../../services/artefact.service';
import { Artefact } from './../../../../Interfaces/Developer.d';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-gamemaster-marketplace',
  templateUrl: './gamemaster-marketplace.component.html',
  styleUrls: ['./gamemaster-marketplace.component.css']
})

export class GamemasterMarketplaceComponent implements OnInit {

  artefacts: Artefact[];


  columns: ITdDataTableColumn[] = [
    { name: 'icon',         label: ''},
    { name: 'name',         label: 'name'},
    { name: 'price',        label: 'Price (in Gold)'},
    { name: 'quantity',     label: 'Quantity'},
    { name: 'minLevel.min', label: 'min. Level'},
    { name: 'skills',       label: 'Skills'},
    { name: 'edit',         label: ''}
  ]


  // Sort / Filter / Paginate variables
  filteredData: any[]
  filteredTotal: number
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'name';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  constructor(
    private _dataTableService:  TdDataTableService,
    private artefactService:    ArtefactService,
    private dialog:             MatDialog 
  ) { }

  ngOnInit() {
    this.artefactService.artefacts$.subscribe(artefacts => {
      this.artefacts = artefacts
      this.filter()
    })
  }


  newArtefact(){
    this.dialog.open(GamemasterArtefactCreateComponent,{panelClass: 'dialog-sexy', width:"500px"}).afterClosed().subscribe(()=>{});
  }

  editArtefact(artefact: Artefact){
    this.dialog.open(GamemasterArtefactEditComponent,{panelClass: 'dialog-sexy', data:artefact, width:"500px"}).afterClosed().subscribe(()=>{});
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
    let newData: any[] = this.artefacts;
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
