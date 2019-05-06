import {WorldService} from '../../../../../../../services/world.service';
import {AdminSonarQubeComponent} from '../../../../admin-sonar-qube/admin-sonar-qube.component';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject, OnInit} from '@angular/core';
import {World} from '../../../../../../../Interfaces/World';
import {User} from '../../../../../../../Interfaces/User';

@Component({
  selector: 'app-admin-sonar-qube-select-background',
  templateUrl: './select-background.component.html',
  styleUrls: ['./select-background.component.css']
})
export class SelectBackgroundComponent implements OnInit {

  images = [];
  user: User;

  constructor(
    private dialogRef: MatDialogRef<AdminSonarQubeComponent>,
    private worldService: WorldService,
    @Inject(MAT_DIALOG_DATA) public world: World) {
  }

  ngOnInit() {
    this.loadImages();
  }

  select(image: string) {
    this.worldService.updateBackground(this.world, image).then(() => {
      this.worldService.loadWorld();
      this.dialogRef.close(image)
    })
  }

  loadImages() {
    this.images = [];

    for (let i = 1; i <= 22; i++) {
      this.images[i - 1] = {};
      this.images[i - 1].src = 'assets/images/background/bg' + String(i) + '.jpg';
      this.images[i - 1].name = 'bg' + String(i);
    }
  }
}
