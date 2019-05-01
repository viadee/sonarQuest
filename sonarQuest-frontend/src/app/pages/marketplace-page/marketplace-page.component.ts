import {
  ITdDataTableColumn,
  TdDataTableSortingOrder,
  TdDataTableService,
  ITdDataTableSortChangeEvent,
  IPageChangeEvent
} from '@covalent/core';
import {Artefact} from '../../Interfaces/Artefact';
import {ArtefactService} from '../../services/artefact.service';
import {Component, OnInit} from '@angular/core';
import {User} from '../../Interfaces/User';
import {UserService} from '../../services/user.service';
import { MatDialog } from '@angular/material';
import { ArtefactViewDetailsComponent } from './components/artefact-view-details/artefact-view-details.component';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-marketplace-page',
  templateUrl: './marketplace-page.component.html',
  styleUrls: ['./marketplace-page.component.css']
})
export class MarketplacePageComponent implements OnInit {

  artefacts: Artefact[];
  my_artefacts_id: number[] = [];
  public level: number;  
  public gold: number;

  columns: ITdDataTableColumn[] = [
    {name: 'icon', label: '', width: {min: 80}},
    {name: 'name', label: 'Name'},
    {name: 'price', label: 'Price(Gold)'},
    {name: 'quantity', label: 'Quantity'},
    {name: 'minLevel.levelNumber', label: 'min. Level'},
    {name: 'buy', label: ''}
  ];
 
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
    private artefactService: ArtefactService,
    private userService: UserService,
    private dialog: MatDialog,
    private translateService: TranslateService,
    private _dataTableService: TdDataTableService
  ) {
  }

  ngOnInit() {
    this.translateService.get('ARTEFACT').subscribe((col_names) => {
      this.columns = [
        {name: 'icon', label: ''},
        {name: 'name', label: col_names.NAME},
        {name: 'price', label: col_names.PRICE},
        {name: 'quantity', label: col_names.QUANTITY},
        {name: 'minLevel.levelNumber', label: col_names.MIN_LEVEL},
        {name: 'buy', label: ''}]
    });    
    this.artefactService.artefactsforMarkteplace$.subscribe(artefacts => {
      this.artefacts = artefacts;
      this.filter();
    });
    this.artefactService.getData();

    const user: User = this.userService.getUser();
    const userArtefacts: Artefact[] = user.artefacts;
    userArtefacts.map(artefact => this.my_artefacts_id.push(artefact.id));
    if (user.level) {
      this.level = user.level.levelNumber;
    } else {
      this.level = 1;
    }
    if (user.gold) {
      this.gold = user.gold;
    } else {
      this.gold = 0;
    }
  }

  buyArtefact(artefact: Artefact) {
    if (artefact != null && this.userService.getUser() != null) {
      this.artefactService.buyArtefact(artefact).then(() => {
        this.artefactService.getData();
        this.userService.getUser();
      })
    }
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

  viewArtefact(artefact: Artefact) {
    this.dialog.open(ArtefactViewDetailsComponent, {
      panelClass: 'dialog-sexy',
      data: artefact,
      width: '500px'
    }).afterClosed().subscribe(() => {
    });
  }
}
