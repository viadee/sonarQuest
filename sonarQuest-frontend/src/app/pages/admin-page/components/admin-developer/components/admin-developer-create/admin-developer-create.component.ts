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
  public images: any[];
  public selectedImage: string;

  constructor(
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    private developerService: DeveloperService,
    @Inject(MAT_DIALOG_DATA) public developers: Developer[]
  ) { }

  ngOnInit() {
    this.loadImages();
    this.selectedImage = "http://via.placeholder.com/200x200";
  }

  createDeveloper(){
    if(this.username && this.username != "" && this.selectedImage){
      let new_developer = {
        username: this.username,
        aboutMe:  this.aboutMe,
        picture: this.selectedImage
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

  loadImages() {
    this.images = [];

    for (let i = 0; i < 15; i++) {
      this.images[i] = {};
      this.images[i].src = "assets/images/quest/hero" + (i + 1) + ".jpg";
      this.images[i].name = "hero" + (i + 1);
    }
  }
}
