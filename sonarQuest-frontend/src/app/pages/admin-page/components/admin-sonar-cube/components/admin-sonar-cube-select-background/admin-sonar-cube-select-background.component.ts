import { AdminSonarCubeComponent } from './../../admin-sonar-cube.component';
import { MatDialogRef } from '@angular/material';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-sonar-cube-select-background',
  templateUrl: './admin-sonar-cube-select-background.component.html',
  styleUrls: ['./admin-sonar-cube-select-background.component.css']
})
export class AdminSonarCubeSelectBackgroundComponent implements OnInit {

  imageNames: string[] = ['bg01', 'bg02', 'bg03'];
  images = [];
  selectedImage: string;

  constructor(
    private dialogRef: MatDialogRef<AdminSonarCubeComponent>
  ) { }

  ngOnInit() {
    this.loadImages()
  }


  select(image: string){
    this.dialogRef.close(image)
  }

  loadImages() {
    this.images = [];

    for (let i = 1; i <= 3; i++) {
      this.images[i-1] = {};
      this.images[i-1].src = "assets/images/background/bg" + String("00" + i).slice(-2) + ".jpg";
      this.images[i-1].name = "bg" + String("00" + i).slice(-2);
    }
  }
}
