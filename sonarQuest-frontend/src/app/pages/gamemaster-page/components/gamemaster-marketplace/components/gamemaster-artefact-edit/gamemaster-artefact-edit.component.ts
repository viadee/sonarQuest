import { MatDialog } from '@angular/material';
import { GamemasterIconSelectComponent } from './../gamemaster-artefact-create/components/gamemaster-icon-select/gamemaster-icon-select.component';
import { SkillService } from './../../../../../../services/skill.service';
import { ITdDataTableColumn, TdDataTableSortingOrder, IPageChangeEvent, ITdDataTableSortChangeEvent, TdDataTableService } from '@covalent/core';
import { Artefact, Skill, Level } from './../../../../../../Interfaces/Artefact';
import { ArtefactService } from './../../../../../../services/artefact.service';
import { GamemasterMarketplaceComponent } from './../../gamemaster-marketplace.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-gamemaster-artefact-edit',
  templateUrl: './gamemaster-artefact-edit.component.html',
  styleUrls: ['./gamemaster-artefact-edit.component.css']
})
export class GamemasterArtefactEditComponent implements OnInit {

  name:         string;
  min:          number;
  price:        number;
  skills:       Skill[];
  minLevel:     Level;
  quantity:     number;
  description:  string;
  icon:         string  = '';

  columns: ITdDataTableColumn[] = [
    { name: 'name',  label: 'Name',  width: {min: 80}},
    { name: 'type',  label: 'Type',  width: {min: 40}},
    { name: 'value', label: 'Value', width: {min: 40}},
    { name: 'action', label: '',  }
  ]

  // Sort / Filter / Paginate variables
  filteredSkills: Skill[];
  filteredTotal: number
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'name';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  constructor(
    private dialogRef: MatDialogRef<GamemasterMarketplaceComponent>,
    private dialog: MatDialog,
    private artefactService: ArtefactService,
    private skillService: SkillService,
    @Inject(MAT_DIALOG_DATA) public artefact: Artefact,
    private _dataTableService: TdDataTableService
  ) { }

  ngOnInit() {
    this.skillService.getSkillsForArtefact(this.artefact).then(skills => {
      this.skills = skills
      this.filter();
    })
    this.name        = this.artefact.name
    this.min         = this.artefact.minLevel.min
    this.price       = this.artefact.price
    this.description = this.artefact.description
    this.quantity    = this.artefact.quantity
    this.icon        = this.artefact.icon || ''
  }


  selectIcon(){
    this.dialog.open(GamemasterIconSelectComponent,{data: this.icon, panelClass: 'dialog-sexy', width:"800px"}).afterClosed().subscribe(icon=>{
      if (icon != undefined) this.icon = icon
    });
  }

  updateArtefact() {
    if (this.name && this.min && this.price) {
      this.artefact.name        = this.name
      this.artefact.price       = this.price
      this.artefact.quantity    = this.quantity
      this.artefact.description = this.description
      this.artefact.icon        = this.icon
      this.artefactService.setMinLevel(this.artefact, this.min)
      
      this.artefactService.updateArtefact(this.artefact).then(() => {
        this.artefactService.getData();
        this.dialogRef.close();
      });
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
    let newData: any[] = this.skills;
    const excludedColumns: string[] = this.columns
      .filter((column: ITdDataTableColumn) => {
        return ((column.filter === undefined && column.hidden === true) ||
          (column.filter !== undefined && column.filter === false));
      }).map((column: ITdDataTableColumn) => {
        return column.name;
      });
    newData = this._dataTableService.filterData(newData, this.searchTerm, true, excludedColumns);
    this.filteredTotal = newData.length || 0;
    newData = this._dataTableService.sortData(newData, this.sortBy, this.sortOrder);
    newData = this._dataTableService.pageData(newData, this.fromRow, this.currentPage * this.pageSize);
    this.filteredSkills = newData;
  }

}
 
