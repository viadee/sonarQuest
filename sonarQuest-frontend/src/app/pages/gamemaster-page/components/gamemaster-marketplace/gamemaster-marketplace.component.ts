import {GamemasterArtefactEditComponent} from './components/gamemaster-artefact-edit/gamemaster-artefact-edit.component';
import {MatDialog} from '@angular/material';
import {GamemasterArtefactCreateComponent} from './components/gamemaster-artefact-create/gamemaster-artefact-create.component';
import {
  ITdDataTableSortChangeEvent,
  IPageChangeEvent,
  ITdDataTableColumn,
  TdDataTableSortingOrder,
  TdDataTableService
} from '@covalent/core';
import {ArtefactService} from './../../../../services/artefact.service';
import {Component, OnInit} from '@angular/core';
import {Artefact} from '../../../../Interfaces/Artefact';
import { TranslateService } from '@ngx-translate/core';
import { updateLocale } from 'moment';

@Component({
  selector: 'app-gamemaster-marketplace',
  templateUrl: './gamemaster-marketplace.component.html',
  styleUrls: ['./gamemaster-marketplace.component.css']
})

export class GamemasterMarketplaceComponent implements OnInit {

  artefacts: Artefact[];

  columns: ITdDataTableColumn[] = [
    {name: 'icon', label: ''},
    {name: 'name', label: 'name'},
    {name: 'price', label: 'Price (in Gold)'},
    {name: 'quantity', label: 'Quantity'},
    {name: 'minLevel.level', label: 'min. Level'},
    {name: 'skills', label: 'Skills'},
    {name: 'edit', label: ''}
  ];

  // Sort / Filter / Paginate variables
  filteredData: any[];
  filteredTotal: number;
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'name';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  constructor(
    private _dataTableService: TdDataTableService,
    private artefactService: ArtefactService,
    private translateService: TranslateService,
    private dialog: MatDialog) {
  }

  ngOnInit() {
    this.translateService.get('ARTEFACT').subscribe((col_names) => {
      this.columns = [
        {name: 'icon', label: ''},
        {name: 'name', label: col_names.NAME},
        {name: 'price', label: col_names.PRICE},
        {name: 'quantity', label: col_names.QUANTITY},
        {name: 'minLevel.level', label: col_names.MIN_LEVEL},
        {name: 'skills', label: col_names.SKILLS},
        {name: 'edit', label: ''}]
    });      
    this.update();
  }

  newArtefact() {
    this.dialog.open(GamemasterArtefactCreateComponent, {panelClass: 'dialog-sexy', width: '500px'}).afterClosed()
      .subscribe(() => {
      });
  }

  editArtefact(artefact: Artefact) {
    this.dialog.open(GamemasterArtefactEditComponent, {
      panelClass: 'dialog-sexy',
      data: artefact,
      width: '500px'
    }).afterClosed().subscribe(() => {
    });
  }

  deleteArtefact(artefact: Artefact) {
    var msg = "";
    this.translateService.get('GLOBAL.CONFIRMATION_MESSAGE').subscribe(translateMsg => msg = translateMsg);
    if(confirm(msg)) {
      this.artefactService.deleteArtefact(artefact).then(() => {
        this.update();
      });
    }
  }

  update() {
    this.artefactService.artefacts$.subscribe(artefacts => {
      this.artefacts = artefacts;
      this.filter();
    });
    this.artefactService.getData();
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
