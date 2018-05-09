import { DeveloperService } from './../../../../../../services/developer.service';
import { Developer } from './../../../../../../Interfaces/Developer.d';
import { WorldService } from './../../../../../../services/world.service';
import { AdminSonarCubeComponent } from './../../admin-sonar-cube.component';
import { MatDialogRef } from '@angular/material';
import { Component, OnInit } from '@angular/core';
import { World } from '../../../../../../Interfaces/World';

@Component({
  selector: 'app-admin-sonar-cube-select-background',
  templateUrl: './admin-sonar-cube-select-background.component.html',
  styleUrls: ['./admin-sonar-cube-select-background.component.css']
})
export class AdminSonarCubeSelectBackgroundComponent implements OnInit {

  imageNames: string[] = ['bg01', 'bg02', 'bg03'];
  images = [];
  selectedImage: string;
  currentWorld: World;
  developer: Developer;

  constructor(
    private dialogRef: MatDialogRef<AdminSonarCubeComponent>,
    private worldService: WorldService,
    private developerService: DeveloperService
  ) {
  }

  ngOnInit() {
    this.loadImages()

    this.worldService.currentWorld$.subscribe(world => {
      this.currentWorld = world;
    })

    this.developerService.avatar$.subscribe(developer => {
      this.developer = developer;
    })
  }


  select(image: string) {
    this.worldService.updateBackground(this.currentWorld, image).then(() => {
      this.worldService.getCurrentWorld(this.developer)
      this.dialogRef.close(image)
    })
  }

  loadImages() {
    this.images = [];

    for (let i = 1; i <= 3; i++) {
      this.images[i - 1] = {};
      this.images[i - 1].src = "assets/images/background/bg" + String("00" + i).slice(-2) + ".jpg";
      this.images[i - 1].name = "bg" + String("00" + i).slice(-2);
    }
  }
}
