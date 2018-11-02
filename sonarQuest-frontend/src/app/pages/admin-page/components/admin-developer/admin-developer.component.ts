import {TranslateService} from '@ngx-translate/core';
import {AdminDeveloperDeleteComponent} from './components/admin-developer-delete/admin-developer-delete.component';
import {AdminDeveloperEditComponent} from './components/admin-developer-edit/admin-developer-edit.component';
import {TdDataTableSortingOrder, ITdDataTableSortChangeEvent, ITdDataTableColumn, TdDataTableService} from '@covalent/core';
import {AdminDeveloperCreateComponent} from './components/admin-developer-create/admin-developer-create.component';
import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {User} from '../../../../Interfaces/User';
import {UserService} from '../../../../services/user.service';
import { ImageService } from 'app/services/image.service';

@Component({
  selector: 'app-admin-developer',
  templateUrl: './admin-developer.component.html',
  styleUrls: ['./admin-developer.component.css']
})
export class AdminDeveloperComponent implements OnInit {

  public users: User[];

  columns: ITdDataTableColumn[] = [
    {name: 'avatar', label: 'Avatar', width: {min: 20}},
    {name: 'username', label: 'Username', width: {min: 100}},
    {name: 'xp', label: 'XP', width: 50},
    {name: 'gold', label: 'Gold', width: 50},
    {name: 'aboutMe', label: 'About Me', width: {min: 100}},
    {name: 'edit', label: '', width: 120}
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
    private imageService: ImageService,
    private dialog: MatDialog,
    private translateService: TranslateService,
    private _dataTableService: TdDataTableService) {
  }

  ngOnInit() {
    this.translateTable();
    this.userService.getUsers().subscribe(users => this.setUsers(users));
  }

  translateTable() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        {name: 'avatar', label: col_names.AVATAR},
        {name: 'username', label: col_names.USERNAME},
        {name: 'xp', label: col_names.XP},
        {name: 'gold', label: col_names.GOLD},
        {name: 'aboutMe', label: col_names.ABOUT_ME},
        {name: 'edit', label: ''}]
    });
  }

  setUsers(users: User[]) {
    this.users = users;
    this.filter();
  }

  createUser() {
    this.dialog.open(AdminDeveloperCreateComponent, {data: this.users, width: '500px'}).afterClosed()
      .subscribe(() => this.ngOnInit());
  }

  editUser(user: User) {
    this.dialog.open(AdminDeveloperEditComponent, {data: user, width: '500px'}).afterClosed()
      .subscribe(() => this.ngOnInit());
  }

  deleteUser(user: User) {
    this.dialog.open(AdminDeveloperDeleteComponent, {data: user, width: '500px'}).afterClosed()
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
    this.mapPicture(newData);
    this.filteredTotal = newData.length;
    newData = this._dataTableService.sortData(newData, this.sortBy, this.sortOrder);
    newData = this._dataTableService.pageData(newData, this.fromRow, this.currentPage * this.pageSize);
    this.filteredData = newData;
  }

  mapPicture(rows: any[]): void {
    rows.forEach(row => {
      // REST ressource for user avatar
      row.avatar = this.imageService.createAvatarImageUrl(row.id);
    });
  }
}
