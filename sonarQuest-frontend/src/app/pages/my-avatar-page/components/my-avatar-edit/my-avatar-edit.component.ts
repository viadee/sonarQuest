import { MAT_DIALOG_DATA } from '@angular/material';
import { MatDialogRef } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';
import {DeveloperService} from "../../../../services/developer.service";
import {Developer} from "../../../../Interfaces/Developer";

@Component({
  selector: 'app-my-avatar-edit',
  templateUrl: './my-avatar-edit.component.html',
  styleUrls: ['./my-avatar-edit.component.css']
})
export class AvatarEditComponent implements OnInit {

  images: any[];

  constructor(
    private dialogRef: MatDialogRef<AvatarEditComponent>,
    private developerService: DeveloperService,
    @Inject(MAT_DIALOG_DATA) public developer: Developer
  ) {
    this.developer = { ...this.developer };
  }

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
