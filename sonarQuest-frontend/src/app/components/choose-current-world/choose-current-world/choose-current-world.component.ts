import {AppComponent} from './../../../app.component';
import {ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableSortingOrder, TdDataTableService} from '@covalent/core';
import {World} from './../../../Interfaces/World';
import {WorldService} from './../../../services/world.service';
import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-choose-current-world',
  templateUrl: './choose-current-world.component.html',
  styleUrls: ['./choose-current-world.component.css']
})
export class ChooseCurrentWorldComponent implements OnInit {

  worlds: World[];

  filteredData: any[];
  filteredTotal: number;
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'name';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  columns: ITdDataTableColumn[] = [
    {name: 'name', label: 'Name'},
    {name: 'project', label: 'Project'},
    {name: 'choose', label: '', width: 70, sortable: false}
  ];

  constructor(
    private dialogRef: MatDialogRef<AppComponent>,
    public worldService: WorldService,
    private _dataTableService: TdDataTableService
  ) {
  }

  ngOnInit() {
    this.worldService.getActiveWorlds().subscribe(worlds => {
      this.worlds = worlds;
      this.filter();
    });
  }

  choose(world: World) {
    this.worldService.setCurrentWorld(world).then(() => this.worldService.loadWorld());
    this.dialogRef.close();
  }

  sort(sortEvent: ITdDataTableSortChangeEvent): void {
    this.sortBy = sortEvent.name;
    this.sortOrder = sortEvent.order;
    this.filter();
  }

  filter(): void {
    let newData: any[] = this.worlds;
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
