import { GamemasterArtefactEditComponent } from './components/gamemaster-artefact-edit/gamemaster-artefact-edit.component';
import { MatDialog } from '@angular/material';
import { GamemasterArtefactCreateComponent } from './components/gamemaster-artefact-create/gamemaster-artefact-create.component';
import {
  ITdDataTableSortChangeEvent,
  IPageChangeEvent,
  ITdDataTableColumn,
  TdDataTableSortingOrder,
  TdDataTableService
} from '@covalent/core';
import { ArtefactService } from './../../../../services/artefact.service';
import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Artefact } from '../../../../Interfaces/Artefact';
import { TranslateService } from '@ngx-translate/core';
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';

@Component({
  selector: 'app-gamemaster-marketplace',
  templateUrl: './gamemaster-marketplace.component.html',
  styleUrls: ['./gamemaster-marketplace.component.css']
})

export class GamemasterMarketplaceComponent implements OnInit {

  @ViewChild('cannotDeleteArtefactSwal') private cannotDeleteArtefactSwal: SwalComponent;
  @ViewChild('deleteSuccessArtefactSwal') private deleteSuccessArtefactSwal: SwalComponent;

  artefacts: Artefact[];

  columns: ITdDataTableColumn[] = [
    { name: 'icon', label: '' },
    { name: 'name', label: 'name' },
    { name: 'price', label: 'Price (in Gold)' },
    { name: 'quantity', label: 'Quantity' },
    { name: 'minLevel.levelNumber', label: 'min. Level' },
    { name: 'skills', label: 'Skills' },
    { name: 'onMarketplace', label: 'Visible on the Marketplace' },
    { name: 'edit', label: '' }
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

  swalOptionsConfirmDelete: {}
  swalOptionsCannotDelete: {}
  swalOptionsDeleteSuccess: {}

  constructor(
    private _dataTableService: TdDataTableService,
    private artefactService: ArtefactService,
    private translateService: TranslateService,
    private dialog: MatDialog) {
    this.initSweetAlert();
  }

  ngOnInit() {
    this.translateService.get('ARTEFACT').subscribe((col_names) => {
      this.columns = [
        { name: 'icon', label: '' },
        { name: 'name', label: col_names.NAME },
        { name: 'price', label: col_names.PRICE },
        { name: 'quantity', label: col_names.QUANTITY },
        { name: 'minLevel.levelNumber', label: col_names.MIN_LEVEL },
        { name: 'skills', label: col_names.SKILLS },
        { name: 'onMarketplace', label: col_names.ON_MARKETPLACE },
        { name: 'edit', label: '' }]
    });
    this.update();
    this.initSweetAlert();
  }
  initSweetAlert(): void {
    this.swalOptionsConfirmDelete = {
      title: this.translate('GLOBAL.DELETE'),
      text: this.translate('GLOBAL.CONFIRMATION_MESSAGE'),
      backdrop: false,
      type: 'question',
      showCancelButton: true,
      cancelButtonText: this.translate('GLOBAL.CANCEL'),
      allowEscapeKey: true,
      allowEnterKey: true,
      confirmButtonColor: '#C62828',
      confirmButtonText: this.translate('GLOBAL.DELETE')
    }

    this.swalOptionsCannotDelete = {
      title: this.translate('GLOBAL.CANNOT_DELETE'),
      text: this.translate('ARTEFACT.CANNOT_DELETE_ARTEFACT'),
      backdrop: false,
      type: 'error',
      confirmButtonColor: '#C62828',
      showCancelButton: true,
      cancelButtonColor: '#C62828',
      allowEscapeKey: true,
      cancelButtonText: this.translate('ARTEFACT.PAYOUT'),
      confirmButtonText: this.translate('ARTEFACT.REMOVE_FROM_MARKETPLACE')
    }
    this.swalOptionsDeleteSuccess = {
      title: this.translate('GLOBAL.DELETE_SUCCESS'),
      toast: true,
      type: 'success',
      position: 'top-end',
      showConfirmButton: false,
      timer: 5000
    }
  }
  newArtefact() {
    this.dialog.open(GamemasterArtefactCreateComponent, { panelClass: 'dialog-sexy', width: '500px' }).afterClosed()
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
    this.artefactService.deleteArtefact(artefact).then(response => {
      if (!response) {
        this.cannotDeleteArtefactSwal.show();
      } else {
        this.deleteSuccessArtefactSwal.show();
        this.update();
       
      }
    });

  }
  
  removeArtefactFromMarketplace(artefact: Artefact) {
    this.artefactService.removeArtefactFromMarketplace(artefact).then(() => {
      this.deleteSuccessArtefactSwal.show();
      this.update();
    }
    )
  }

  payoutArtefact(artefact: Artefact) {
    this.artefactService.payoutArtefact(artefact).then(() => {
      this.deleteSuccessArtefactSwal.show();
      this.update();
    })
  }

  translate(messageString: string): string {
    let msg = '';
    this.translateService.get(messageString).subscribe(translateMsg => msg = translateMsg);
    return msg;
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
      .filter((column: ITdDataTableColumn) => ((column.filter === undefined && column.hidden === true) ||
        (column.filter !== undefined && column.filter === false))).map((column: ITdDataTableColumn) => column.name);
    newData = this._dataTableService.filterData(newData, this.searchTerm, true, excludedColumns);
    this.filteredTotal = newData.length;
    newData = this._dataTableService.sortData(newData, this.sortBy, this.sortOrder);
    newData = this._dataTableService.pageData(newData, this.fromRow, this.currentPage * this.pageSize);
    this.filteredData = newData;
  }

  toggleActive(artefact: Artefact){
    artefact.onMarketplace = !artefact.onMarketplace
    this.artefactService.updateArtefact(artefact)
  }
}
