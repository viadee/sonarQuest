import { ImageService } from '../../../../services/image.service';
import { MAT_DIALOG_DATA } from '@angular/material';
import { MatDialogRef } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Artefact } from 'app/Interfaces/Artefact';
import { Level } from 'app/Interfaces/Level';
import { Skill } from 'app/Interfaces/Skill';
import { TdDataTableSortingOrder, ITdDataTableSortChangeEvent, ITdDataTableColumn } from '@covalent/core';

@Component({
  selector: 'app-marketplace-artefact-view',
  templateUrl: './marketplace-artefact-view.component.html',
  styleUrls: ['./marketplace-artefact-view.component.css']
})
export class ArtefactViewDetailsComponent implements OnInit {

  icon: any;
  name: string;
  min: number;
  price: number;
  minLevel: Level;
  quantity: number;
  description: string;

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
    private dialogRef: MatDialogRef<ArtefactViewDetailsComponent>,
    private domSanitizer: DomSanitizer,
    private imageService: ImageService,
    @Inject(MAT_DIALOG_DATA) public artefact: Artefact) {
    this.artefact = { ...this.artefact };
    this.name = this.artefact.name;
    this.min = this.artefact.minLevel.level;
    this.price = this.artefact.price;
    this.description = this.artefact.description;
    this.quantity = this.artefact.quantity;
    this.icon = this.artefact.icon;
  }

  ngOnInit() {
  }

  cancel() {
    this.dialogRef.close(false);
  }

  protected createSkillsList(artefact: any) {
    const skillnames = artefact.skills.map(skill => skill.name);
    return skillnames.join(', ');
  }

  sort(sortEvent: ITdDataTableSortChangeEvent): void {
    this.sortBy = sortEvent.name;
    this.sortOrder = sortEvent.order;
  }

}
