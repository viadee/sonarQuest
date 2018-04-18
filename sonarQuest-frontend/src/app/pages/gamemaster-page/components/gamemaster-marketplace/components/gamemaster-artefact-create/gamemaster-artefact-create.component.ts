import { GamemasterIconSelectComponent } from './components/gamemaster-icon-select/gamemaster-icon-select.component';
import { ITdDataTableColumn, TdDataTableService, TdDataTableSortingOrder, ITdDataTableSortChangeEvent, IPageChangeEvent } from '@covalent/core';
import { Skill } from './../../../../../../Interfaces/Skill';
import { SkillService } from './../../../../../../services/skill.service';
import { GamemasterSkillCreateComponent } from './components/gamemaster-skill-create/gamemaster-skill-create.component';
import { ArtefactService } from './../../../../../../services/artefact.service';
import { Artefact } from './../../../../../../Interfaces/Developer.d';
import { GamemasterMarketplaceComponent } from './../../gamemaster-marketplace.component';
import { MatDialogRef, MatDialog } from '@angular/material';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-gamemaster-artefact-create',
  templateUrl: './gamemaster-artefact-create.component.html',
  styleUrls: ['./gamemaster-artefact-create.component.css']
})
export class GamemasterArtefactCreateComponent implements OnInit {

  name: string;
  min: number;
  price: number;
  quantity: number;
  skills: Skill[] = [];
  description: string;
  icon: string = '';


  columns: ITdDataTableColumn[] = [
    { name: 'name', label: 'Name', width: { min: 80 } },
    { name: 'type', label: 'Type', width: { min: 40 } },
    { name: 'value', label: 'Value', width: { min: 40 } },
    { name: 'action', label: '', }
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
    private artefactService: ArtefactService,
    private dialog: MatDialog,
    private skillServive: SkillService,
    private _dataTableService: TdDataTableService
  ) { }

  ngOnInit() {
    this.filter()
  }

  removeSkill(skill: Skill) {
    this.skillServive.deleteSkill(skill).then()
    this.skills.splice(this.skills.indexOf(skill))
    this.filter()
  }

  createSkill() {
    this.dialog.open(GamemasterSkillCreateComponent, { panelClass: 'dialog-sexy', width: "500px" }).afterClosed().subscribe(skill => {
      if (skill != undefined) {
        this.skills.push(skill)
        this.filter()
      }
    });
  }

  selectIcon() {
    this.dialog.open(GamemasterIconSelectComponent, { data: this.icon, panelClass: 'dialog-sexy', width: "800px" }).afterClosed().subscribe(icon => {
      if (icon != undefined) {
        this.icon = icon
      }
    });
  }

  createArtefact() {

    if (this.min == 0 || this.min == null) this.min = 1
    if (this.quantity == 0 || this.quantity == null) this.quantity = 1

    if (this.name && this.min && this.price && this.quantity && this.skills.length > 0) {

      let artefact = {
        name: this.name,
        price: this.price,
        quantity: this.quantity,
        description: this.description,
        skills: this.skills,
        icon: this.icon,
        minLevel: {
          min: this.min
        }
      }

      this.artefactService.createArtefact(artefact).then(() => {
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
