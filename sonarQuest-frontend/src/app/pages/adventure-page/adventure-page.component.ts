import {isUndefined} from 'util';
import {TranslateService} from '@ngx-translate/core';
import {Component, OnInit} from '@angular/core';
import {
  ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder,
  IPageChangeEvent
} from '@covalent/core';
import {AdventureService} from '../../services/adventure.service';
import {WorldService} from '../../services/world.service';
import {Adventure} from '../../Interfaces/Adventure';
import {World} from '../../Interfaces/World';
import {User} from '../../Interfaces/User';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-adventure-page',
  templateUrl: './adventure-page.component.html',
  styleUrls: ['./adventure-page.component.css']
})

export class AdventurePageComponent implements OnInit {

  public user: User;
  public currentWorld: World;

  columns: ITdDataTableColumn[] = [
    {name: 'title', label: 'Title', width: 200},
    {name: 'gold', label: 'Gold', width: 10},
    {name: 'xp', label: 'XP', width: 10},
    {name: 'story', label: 'Story', width: 200},
    {name: 'status', label: 'Status', width: 200},
    {name: 'edit', label: '', width: 70}
  ]

  sortBy = 'title';
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;
  selectedRows: any[] = [];
  filteredTotal: number;
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 50;

  public myAdventures: Adventure[];
  public availableAdventures: Adventure[];

  public filteredMyAdventures: Adventure[];
  public filteredAvailableAdventures: Adventure[];

  constructor(
    public adventureService: AdventureService,
    public worldService: WorldService,
    public userService: UserService,
    public translateService: TranslateService,
    private _dataTableService: TdDataTableService) {
  }


  ngOnInit() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        {name: 'title', label: col_names.TITLE, width: 130},
        {name: 'gold', label: col_names.GOLD, width: 30},
        {name: 'xp', label: col_names.XP, width: 30},
        {name: 'story', label: col_names.STORY, width: 500},
        {name: 'status', label: col_names.STATUS, width: 50},
        {name: 'edit', label: ''}]
    });

    this.userService.avatar$.subscribe({
      next: user => {
        this.user = user;
        this.loadAdventures();
      }
    })

    this.worldService.currentWorld$.subscribe({
      next: world => {
        this.currentWorld = world;
        this.loadAdventures();
      }
    })

  }

  loadAdventures() {
    if (this.currentWorld && !isUndefined(this.currentWorld.id) && this.user) {
      this.adventureService.getFreeAdventures(this.currentWorld).subscribe(availableAdventures => {
        this.availableAdventures = availableAdventures
        this.filteredAvailableAdventures = this.filterAdventures(this.availableAdventures);
      })
      this.adventureService.getMyAdventures(this.currentWorld).subscribe(myAdventures => {
        this.myAdventures = myAdventures
        this.filteredMyAdventures = this.filterAdventures(this.myAdventures);
      })
    }
  }

  leaveAdventure(row) {
    this.adventureService.leaveAdventure(row).then(() => this.loadAdventures());
  }

  joinAdventure(row) {
    this.adventureService.joinAdventure(row).then(() => this.loadAdventures());
  }


  sort(sortEvent: ITdDataTableSortChangeEvent): void {
    this.sortBy = sortEvent.name;
    this.sortOrder = sortEvent.order;
    this.filter()
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

  filter() {
    this.filteredAvailableAdventures = this.filterAdventures(this.availableAdventures);
    this.filteredMyAdventures = this.filterAdventures(this.myAdventures);
  }

  filterAdventures(adventures: Adventure[]): Adventure[] {
    let newData: Adventure[] = adventures;
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
    return newData;
  }

}
