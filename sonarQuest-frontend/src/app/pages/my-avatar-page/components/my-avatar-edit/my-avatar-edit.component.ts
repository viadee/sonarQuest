import { ImageService } from './../../../../services/image.service';
import { MAT_DIALOG_DATA } from '@angular/material';
import { MatDialogRef } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';
import { DeveloperService } from "../../../../services/developer.service";
import { Developer } from "../../../../Interfaces/Developer";
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-my-avatar-edit',
  templateUrl: './my-avatar-edit.component.html',
  styleUrls: ['./my-avatar-edit.component.css']
})
export class AvatarEditComponent implements OnInit {

  images: any[];
  customAvatar: any;
  imageToShow: any;
  avatar: File;

  constructor(
    private dialogRef: MatDialogRef<AvatarEditComponent>,
    private developerService: DeveloperService,
    private domSanitizer: DomSanitizer,
    private imageService: ImageService,
    @Inject(MAT_DIALOG_DATA) public developer: Developer
  ) {
    this.developer = { ...this.developer };
  }

  ngOnInit() {      
    this.developerService.getImage(this.developer).subscribe((blob) => {
      this.imageToShow = this.imageService.createImageFromBlob(blob);
    })
  }


  editDeveloper() {
    console.log(this.developer.picture)
    this.developerService.updateDeveloper(this.developer).then(() => {
      this.dialogRef.close(this.developer);
    })
    this.developerService.postImage(this.avatar,this.developer).subscribe()
  }

  cancel() {
    this.dialogRef.close(false)
  }


  onChange(files: File[]) {
    this.avatar = files[0];
    console.log(this.avatar)
    this.developer.picture = this.avatar.name;

  }


}
