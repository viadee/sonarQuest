import {
  ITdDataTableColumn,
  TdDataTableSortingOrder,
  TdDataTableService,
  ITdDataTableSortChangeEvent,
  IPageChangeEvent
} from '@covalent/core';
import {Artefact} from './../../Interfaces/Artefact';
import {ArtefactService} from './../../services/artefact.service';
import {Component, OnInit} from '@angular/core';
import {User} from '../../Interfaces/User';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-marketplace-page',
  templateUrl: './marketplace-page.component.html',
  styleUrls: ['./marketplace-page.component.css']
})
export class MarketplacePageComponent implements OnInit {

  artefacts: Artefact[];
  my_artefacts_id: number[] = [];
  level: number;


  columns: ITdDataTableColumn[] = [
    {name: 'icon', label: '', width: {min: 80}},
    {name: 'name', label: 'Name'},
    {name: 'price', label: 'Price(Gold)'},
    {name: 'quantity', label: 'Quantity'},
    {name: 'minLevel.min', label: 'min. Level'},
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
    private _dataTableService: TdDataTableService
  ) {
  }

  ngOnInit() {
    this.artefactService.artefactsforMarkteplace$.subscribe(artefacts => {
      this.artefacts = artefacts;
      this.filter();
    });

    const user: User = this.userService.getUser();
    const userArtefacts: Artefact[] = user.artefacts;
    userArtefacts.map(artefact => this.my_artefacts_id.push(artefact.id));
    this.level = this.userService.getLevel(user.xp);
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
}
