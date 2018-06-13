import {WorldService} from './../../../../../../services/world.service';
import {AdminSonarCubeComponent} from './../../admin-sonar-cube.component';
import {MatDialogRef} from '@angular/material';
import {Component, OnInit} from '@angular/core';
import {World} from '../../../../../../Interfaces/World';
import {User} from '../../../../../../Interfaces/User';
import {UserService} from '../../../../../../services/user.service';

@Component({
  selector: 'app-admin-sonar-cube-select-background',
  templateUrl: './admin-sonar-cube-select-background.component.html',
  styleUrls: ['./admin-sonar-cube-select-background.component.css']
})
export class AdminSonarCubeSelectBackgroundComponent implements OnInit {

  images = [];
  currentWorld: World;
  user: User;

  constructor(
    private dialogRef: MatDialogRef<AdminSonarCubeComponent>,
    private worldService: WorldService) {
  }

  ngOnInit() {
    this.loadImages();
    this.currentWorld = this.worldService.getCurrentWorld();
  }

  select(image: string) {
    this.worldService.updateBackground(this.currentWorld, image).then(() => {
      this.worldService.loadWorld();
      this.dialogRef.close(image)
    })
  }

  loadImages() {
    this.images = [];

    for (let i = 1; i <= 3; i++) {
      this.images[i - 1] = {};
      this.images[i - 1].src = 'assets/images/background/bg' + String('00' + i).slice(-2) + '.jpg';
      this.images[i - 1].name = 'bg' + String('00' + i).slice(-2);
    }
  }
}
