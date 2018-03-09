import { ViewParticipatedQuestComponent } from './components/view-participated-quest/view-participated-quest.component';
import { ParticipationService } from './../../../../services/participation.service';
import { MatDialog } from '@angular/material';
import { DeveloperService } from './../../../../services/developer.service';
import { WorldService } from './../../../../services/world.service';
import { QuestService } from './../../../../services/quest.service';
import { ITdDataTableColumn, TdDataTableSortingOrder, TdDataTableService, ITdDataTableSortChangeEvent, IPageChangeEvent } from '@covalent/core';
import { Quest } from './../../../../Interfaces/Quest';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-participated-quests',
  templateUrl: './participated-quests.component.html',
  styleUrls: ['./participated-quests.component.css']
})
export class ParticipatedQuestsComponent implements OnInit {

  participatedQuests: Quest[];
  columns: ITdDataTableColumn[] = [
    { name: 'title', label: 'Titel', width: {min:80}},
    { name: 'gold', label: 'Gold', width: {min:40}},
    { name: 'xp', label: 'XP', width: {min:40}},
    { name: 'story', label: 'Erzählung', width: {min:200}},
    { name: 'adventure.title', label: 'Abenteuer', width: {min:80}},
    { name: 'status', label: 'Status', width: {min:60}},
    { name: 'edit', label: '', width: {min:60}}
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

  constructor(
    private questService: QuestService,
    private worldService: WorldService,
    private developerService: DeveloperService,
    private participationService: ParticipationService,
    private _dataTableService: TdDataTableService,
    private dialog: MatDialog) { 
      this.participationService.participationUpdated$.subscribe(()=>{
        this.ngOnInit()
      })
    }



  ngOnInit() {
    this.developerService.getMyAvatar().then(developer=>{
      return this.loadQuests(this.worldService.getCurrentWorld(),developer)
    }).then(()=>{
      this.filter()
    })
    ;
  }

  loadQuests(world,developer){
    return this.questService.getAllParticipatedQuestsForWorldAndDeveloper(world,developer).then(quests => {
     this.participatedQuests = quests;
    });
  }

  viewQuest(quest: Quest){
    this.dialog.open(ViewParticipatedQuestComponent,{data: quest,width:"500px"}).afterClosed().subscribe(()=>{
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
    let newData: any[] = this.participatedQuests;
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
