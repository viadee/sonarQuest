import { ParticipationService } from './../../../../services/participation.service';
import { Developer } from './../../../../Interfaces/Developer.d';
import { DeveloperService } from './../../../../services/developer.service';
import { WorldService } from './../../../../services/world.service';
import { MatDialog } from '@angular/material';
import { QuestService } from './../../../../services/quest.service';
import { ITdDataTableColumn, TdDataTableSortingOrder, TdDataTableService, ITdDataTableSortChangeEvent, IPageChangeEvent } from '@covalent/core';
import { Component, OnInit, Input } from '@angular/core';
import { Quest } from '../../../../Interfaces/Quest';
import { ViewAvailableQuestComponent } from './components/view-available-quest/view-available-quest.component';
import { World } from '../../../../Interfaces/World';

@Component({
  selector: 'app-available-quests',
  templateUrl: './available-quests.component.html',
  styleUrls: ['./available-quests.component.css']
})
export class AvailableQuestsComponent implements OnInit {

  availableQuests: Quest[];
  columns: ITdDataTableColumn[] = [
    { name: 'title', label: 'Titel', width: {min:80}},
    { name: 'gold', label: 'Gold', width: 40},
    { name: 'xp', label: 'XP', width: 40},
    { name: 'story', label: 'Erzählung', width: {min:400}},
    { name: 'adventure.title', label: 'Abenteuer', width: {min:80}},
    { name: 'status', label: 'Status', width:60},
    { name: 'edit', label: '', width: 70}
  ]

  // Sort / Filter / Paginate variables
  filteredData: any[]
  filteredTotal: number
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'title';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  developer: Developer;
  world: World;

  constructor(
    private questService: QuestService,
    private worldService: WorldService,
    private developerService: DeveloperService,
    private _dataTableService: TdDataTableService,
    private participationService: ParticipationService,
    private dialog: MatDialog) { }



  ngOnInit() {
    this.developerService.getMyAvatar().subscribe(dev=>{
      this.developer= dev;
      this.world = this.worldService.getCurrentWorld();
      return this.loadQuests(this.world,this.developer)
    })
    ;
  }

  loadQuests(world,developer){
    return this.questService.getAllAvailableQuestsForWorldAndDeveloper(world,developer).then(quests => {
     this.availableQuests = quests;
    }).then(()=>{
      this.filter()
    });
  }




  viewQuest(quest: Quest){
    this.dialog.open(ViewAvailableQuestComponent,{data: quest,width:"500px"}).afterClosed().subscribe(()=>{
      this.loadQuests(this.world,this.developer);
      this.participationService.announceParticipationUpdate()
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
    let newData: any[] = this.availableQuests;
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
