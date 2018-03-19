import { Developer } from './../../../../../../Interfaces/Developer.d';
import { MAT_DIALOG_DATA } from '@angular/material';
import { DeveloperService } from './../../../../../../services/developer.service';
import { MatDialogRef } from '@angular/material';
import { AdminDeveloperComponent } from './../../admin-developer.component';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-admin-developer-edit',
  templateUrl: './admin-developer-edit.component.html',
  styleUrls: ['./admin-developer-edit.component.css']
})
export class AdminDeveloperEditComponent implements OnInit {


  constructor(
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    private developerService: DeveloperService,
    @Inject(MAT_DIALOG_DATA) public developer: Developer
  ) { }

  ngOnInit() {
  }

  editDeveloper(){
    this.developerService.updateDeveloper(this.developer).then( () => {
      this.dialogRef.close(true);
    })
  }

  cancel(){
    this.dialogRef.close(false)
  }

}
