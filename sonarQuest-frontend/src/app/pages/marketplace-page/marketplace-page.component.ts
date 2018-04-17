import { DeveloperService } from './../../services/developer.service';
import { Developer } from './../../Interfaces/Developer.d';
import { ITdDataTableColumn, TdDataTableSortingOrder, TdDataTableService, ITdDataTableSortChangeEvent, IPageChangeEvent } from '@covalent/core';
import { Artefact } from './../../Interfaces/Artefact';
import { ArtefactService } from './../../services/artefact.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-marketplace-page',
  templateUrl: './marketplace-page.component.html',
  styleUrls: ['./marketplace-page.component.css']
})
export class MarketplacePageComponent implements OnInit {

  artefacts: Artefact[]
  developer: Developer
  my_artefacts_id: number[] = []
  level: number;


  columns: ITdDataTableColumn[] = [
    { name: 'icon', label: '', width: {min:80}},
    { name: 'name', label: 'Name'},
    { name: 'price', label: 'Price(Gold)'},
    { name: 'quantity', label: 'Quantity'},
    { name: 'minLevel.min', label: 'min. Level'},
    { name: 'buy', label: ''}
  ]

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
    private artefactService: ArtefactService,
    private developerService: DeveloperService,
    private _dataTableService: TdDataTableService
  ) { }

  ngOnInit() {
    this.artefactService.artefactsforMarkteplace$.subscribe(artefacts => {
      this.artefacts = artefacts
      this.filter()
    })

    this.developerService.avatar$.subscribe(d => {
      this.developer = d
      d.artefacts.map(artefact => this.my_artefacts_id.push(artefact.id))
      this.level = this.developerService.getLevel(d.xp)
    })
  }

  buyArtefact(artefact: Artefact){
    if (artefact != null && this.developer != null){
      this.artefactService.buyArtefact(artefact,this.developer).then(artefact => {
        this.artefactService.getData();
        this.developerService.getMyAvatar();
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
