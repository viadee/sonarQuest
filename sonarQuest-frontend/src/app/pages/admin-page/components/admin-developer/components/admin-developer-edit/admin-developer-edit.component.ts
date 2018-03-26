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

  images: any[];

  constructor(
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    private developerService: DeveloperService,
    @Inject(MAT_DIALOG_DATA) public developer: Developer
  ) { }

  ngOnInit() {
    this.loadImages();
  }

  editDeveloper(){
    this.developerService.updateDeveloper(this.developer).then( () => {
      this.dialogRef.close(true);
    })
  }

  cancel(){
    this.dialogRef.close(false)
  }

  loadImages() {
    this.images = [];

    for (let i = 0; i < 15; i++) {
      this.images[i] = {};
      this.images[i].src = "assets/images/quest/hero" + (i + 1) + ".jpg";
      this.images[i].name = "hero" + (i + 1);
    }
  }
}
