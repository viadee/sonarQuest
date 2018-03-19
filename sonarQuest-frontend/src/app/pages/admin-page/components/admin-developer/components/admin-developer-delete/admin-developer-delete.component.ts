import { Developer } from './../../../../../../Interfaces/Developer.d';
import { MAT_DIALOG_DATA } from '@angular/material';
import { AdminDeveloperComponent } from './../../admin-developer.component';
import { MatDialogRef } from '@angular/material';
import { DeveloperService } from './../../../../../../services/developer.service';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-admin-developer-delete',
  templateUrl: './admin-developer-delete.component.html',
  styleUrls: ['./admin-developer-delete.component.css']
})
export class AdminDeveloperDeleteComponent implements OnInit {


  constructor(
    private developerService: DeveloperService,
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    @Inject(MAT_DIALOG_DATA) public developer: Developer
  ) { }

  ngOnInit() {
  }

  cancel(){
    this.dialogRef.close(false)
  }

  delete(){
    this.developerService.deleteDeveloper(this.developer).then(()=>{
      this.dialogRef.close(true)
    })
  }

}
