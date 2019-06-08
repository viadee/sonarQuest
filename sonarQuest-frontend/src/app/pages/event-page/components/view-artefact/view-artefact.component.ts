import { UserService } from './../../../../services/user.service';
import { ArtefactService } from './../../../../services/artefact.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject, OnInit} from '@angular/core';
import {Artefact} from 'app/Interfaces/Artefact';
import {Level} from 'app/Interfaces/Level';
import {Skill} from 'app/Interfaces/Skill';
import {ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableSortingOrder} from '@covalent/core';
import { EventDto } from 'app/Interfaces/EventDto';
import { User } from 'app/Interfaces/User';

@Component({
  selector: 'app-view-artefact',
  templateUrl: './view-artefact.component.html',
  styleUrls: ['./view-artefact.component.css']
})
export class ViewArtefactComponent implements OnInit {

  icon: any;
  name: string;
  min: number;
  price: number;
  minLevel: Level;
  quantity: number;
  description: string;
  artefact: Artefact;
  user: User;
  inMyInventory: Boolean = true;

  columns: ITdDataTableColumn[] = [
    {name: 'name', label: 'Name', width: {min: 80}},
    {name: 'type', label: 'Type', width: {min: 40}},
    {name: 'value', label: 'Value', width: {min: 40}}
  ];

  // Sort / Filter / Paginate variables
  filteredSkills: Skill[];
  filteredTotal: number;
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 5;
  sortBy = 'name';
  selectedRows: any[] = [];
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;

  constructor(
    private dialogRef: MatDialogRef<ViewArtefactComponent>,
    private artefactService: ArtefactService,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public event: EventDto) {

    this.artefactService.getArtefact(event.typeId).then(artefact => {
      this.artefact = artefact ;

      this.userService.user$.subscribe(user => {
        this.user = user
        
        this.inMyInventory = user.artefacts.includes(artefact);
      })
      this.name = this.artefact.name;
      this.min = this.artefact.minLevel.levelNumber;
      this.price = this.artefact.price;
      this.description = this.artefact.description;
      this.quantity = this.artefact.quantity;
      this.icon = this.artefact.icon;
    })
    
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

  buy() {
    if (this.artefact != null && this.user != null) {
      this.artefactService.buyArtefact(this.artefact).then(() => {
        this.artefactService.getData();
      })
    }
  }

  close(){
    this.dialogRef.close()
  }

}
