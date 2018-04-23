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

  constructor(
    private dialogRef: MatDialogRef<AvatarEditComponent>,
    private developerService: DeveloperService,
    private domSanitizer: DomSanitizer,
    @Inject(MAT_DIALOG_DATA) public developer: Developer
  ) {
    this.developer = { ...this.developer };
  }

  ngOnInit() {
    this.loadImages();
      
    this.developerService.getImage(this.developer).subscribe((blob) => {
      

      // Obtain a blob: URL for the image data.

    /*   var arrayBufferView = new Uint8Array(arrayBuffer);

      var blob = new Blob([arrayBufferView], { type: "image/png" });
 */
     /*  var urlCreator = window.URL;

      var imageUrl = urlCreator.createObjectURL(blob);

      console.log(imageUrl);

      this.customAvatar = imageUrl;


 */
      
      this.createImageFromBlob(blob);
    })
  }


  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = this.domSanitizer.bypassSecurityTrustUrl(reader.result);
      console.log(this.imageToShow)
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }




  editDeveloper() {
    this.developerService.updateDeveloper(this.developer).then(() => {
      this.dialogRef.close(this.developer);
    })
  }

  cancel() {
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
