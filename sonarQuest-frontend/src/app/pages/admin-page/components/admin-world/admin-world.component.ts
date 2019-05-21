import {TranslateService} from '@ngx-translate/core';
import {Component, OnInit} from '@angular/core';
import {WorldService} from '../../../../services/world.service';
import {World} from '../../../../Interfaces/World';
import {
  IPageChangeEvent,
  ITdDataTableColumn,
  ITdDataTableSortChangeEvent,
  TdDataTableService,
  TdDataTableSortingOrder
} from '@covalent/core';
import {MatDialog} from '@angular/material';
import {EditWorldComponent} from './components/edit-world/edit-world.component';
import {LoadingService} from '../../../../services/loading.service';
import {UserService} from 'app/services/user.service';
import { User } from 'app/Interfaces/User';

@Component({
  selector: 'sq-admin-world',
  templateUrl: './admin-world.component.html',
  styleUrls: ['./admin-world.component.css']
})
export class AdminWorldComponent implements OnInit {

  currentWorld: World;
  worlds: World[];
  user: User

  columns: ITdDataTableColumn[] = [
    { name: 'id', label: 'Id' },
    { name: 'name', label: 'Name' },
    { name: 'project', label: 'Project', width: { min: 300 } },
    { name: 'active', label: 'Active' },
    { name: 'usequestcards', label: 'Questcards' },
    { name: 'edit', label: '' }
  ];

  // Sort / Filter / Paginate variables
  filteredData: any[];
  filteredTotal: number;
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'id';
  selectedRows: any[];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  constructor(private worldService: WorldService,
    private _dataTableService: TdDataTableService,
    private translateService: TranslateService,
    private dialog: MatDialog,
    private loadingService: LoadingService,
    private userService: UserService) {
  }

  ngOnInit() {
    this.translateTable();
    this.init();
    this.worldService.onWorldChange().subscribe(() => this.init());
    this.userService.user$.subscribe(user => this.user = user)
  }

  private init() {
    if (this.worldService.getCurrentWorld()) {
      this.currentWorld = this.worldService.getCurrentWorld();
    }
    if (this.user.role.name.toLocaleUpperCase() === 'ADMIN') {
      this.loadWorlds();
    }

  }

  translateTable() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        { name: 'id', label: col_names.ID, width: 35 },
        { name: 'name', label: col_names.NAME },
        { name: 'project', label: col_names.PROJECT, width: { min: 300 } },
        { name: 'active', label: col_names.ACTIVE },
        { name: 'usequestcards', label: col_names.USE_QUEST_CARDS },
        { name: 'edit', label: '' }]
    });
  }

  loadWorlds() {
    this.worldService.getAllWorlds().subscribe(worlds => {
      this.worlds = worlds;
      this.filter();
    });
  }

  protected editWorld(world: World) {
    this.dialog.open(EditWorldComponent, { data: world }).afterClosed().subscribe(() => {
      this.loadWorlds();
      this.worldService.loadWorld();
    })
  }

  updateWorlds() {
    const loading = this.loadingService.getLoadingSpinner();
    this.worldService.generateWorldsFromSonarQubeProjects().then(() => {
      this.worldService.worldChanged();
      loading.close();
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
    let newData: World[] = this.worlds;
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


  toggleUsequestcards(world: World){
    world.usequestcards = !world.usequestcards
    this.worldService.updateWorld(world)
  }
  toggleActive(world: World){
    world.active = !world.active
    this.worldService.updateWorld(world)
  }
}
