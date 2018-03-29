import { DeveloperService } from './../../../../../../services/developer.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { AdminDeveloperComponent } from './../../admin-developer.component';
import { Developer } from './../../../../../../Interfaces/Developer.d';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-admin-developer-create',
  templateUrl: './admin-developer-create.component.html',
  styleUrls: ['./admin-developer-create.component.css']
})
export class AdminDeveloperCreateComponent implements OnInit {

  public username: String;
  public aboutMe:  String;

  constructor(
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    private developerService: DeveloperService,
    @Inject(MAT_DIALOG_DATA) public developers: Developer[]
  ) { }

  ngOnInit() {
    
  }

  createDeveloper(){
    if(this.username && this.username != ""){ 
      let new_developer = {
        username: this.username,
        aboutMe:  this.aboutMe
      }

      this.developerService.createDeveloper(new_developer)
      .then( developer => {
        this.dialogRef.close(developer);
      })
    }
  }

  cancel(){
    this.dialogRef.close();
  }

}
