import { WorldService } from './../../../../services/world.service';
import { TranslateService } from '@ngx-translate/core';
import { AdminDeveloperDeleteComponent } from './components/admin-developer-delete/admin-developer-delete.component';
import { AdminDeveloperEditComponent } from './components/admin-developer-edit/admin-developer-edit.component';
import { TdDataTableSortingOrder, ITdDataTableSortChangeEvent, ITdDataTableColumn, TdDataTableService } from '@covalent/core';
import { AdminDeveloperCreateComponent } from './components/admin-developer-create/admin-developer-create.component';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { User } from '../../../../Interfaces/User';
import { UserService } from '../../../../services/user.service';

@Component({
  selector: 'app-admin-developer',
  templateUrl: './admin-developer.component.html',
  styleUrls: ['./admin-developer.component.css']
})
export class AdminDeveloperComponent implements OnInit {

  public users: User[];

  columns: ITdDataTableColumn[] = [
    { name: 'username', label: 'Username'},
    { name: 'role.name', label: 'Role'},
    { name: 'level.level', label: 'Level'},
    { name: 'xp', label: 'XP'},
    { name: 'gold', label: 'Gold'},
    { name: 'currentWorld.name', label: 'Current World' },
    { name: 'joinedWorlds', label: 'Active Worlds' },
    { name: 'edit', label: '' }
  ];

  sortBy = 'username';
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;
  selectedRows: any[] = [];
  filteredData: any[];
  filteredTotal: number;
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 50;

  constructor(
    private userService: UserService,
    private dialog: MatDialog,
    private translateService: TranslateService,
    private _dataTableService: TdDataTableService,
    private worldService: WorldService) {
  }

  ngOnInit() {
    this.translateTable();
    this.userService.getUsers().subscribe(users => this.setUsers(users));
  }

  translateTable() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        { name: 'username', label: col_names.USERNAME },
        { name: 'role.name', label: col_names.ROLE },
        { name: 'level.level', label: col_names.LEVEL, format: this.formatNullIntoOne() },
        { name: 'xp', label: col_names.XP },
        { name: 'gold', label: col_names.GOLD },
        { name: 'currentWorld.name', label: col_names.ACTIVE_WORLD },
        { name: 'joinedWorlds', label: col_names.JOINED },
        { name: 'edit', label: '' }]
    });
  }

  private formatNullIntoOne(): (value: any) => any {
    return v => v === null ? 1 : v;
  }

  setUsers(users: User[]) {
    this.users = users;
    this.filter();
  }

  createUser() {
    this.dialog.open(AdminDeveloperCreateComponent, { data: this.users, width: '500px' }).afterClosed()
      .subscribe(() => this.ngOnInit());
  }

  editUser(user: User) {
    this.dialog.open(AdminDeveloperEditComponent, { data: user, width: '500px' }).afterClosed()
      .subscribe(() => {
        if (user.role.name == "ADMIN") this.worldService.getWorlds()
        this.ngOnInit()
      });
  }

  deleteUser(user: User) {
    this.dialog.open(AdminDeveloperDeleteComponent, { data: user, width: '500px' }).afterClosed()
      .subscribe(() => this.ngOnInit());
  }

  sort(sortEvent: ITdDataTableSortChangeEvent): void {
    this.sortBy = sortEvent.name;
    this.sortOrder = sortEvent.order;
    this.filter();
  }

  filter(): void {
    let newData: any[] = this.users;
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
