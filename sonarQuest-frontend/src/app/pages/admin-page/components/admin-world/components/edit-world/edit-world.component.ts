import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {World} from '../../../../../../Interfaces/World';
import {WorldService} from '../../../../../../services/world.service';
import {SelectBackgroundComponent} from './select-background/select-background.component';

@Component({
  selector: 'app-edit-world',
  templateUrl: './edit-world.component.html',
  styleUrls: ['./edit-world.component.css']
})
export class EditWorldComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<EditWorldComponent>,
              @Inject(MAT_DIALOG_DATA) public world: World,
              private worldService: WorldService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
  }

  updateWorld() {
    this.worldService.updateWorld(this.world).then(() => this.dialogRef.close())
  }

  selectBackground() {
    this.dialog.open(SelectBackgroundComponent,
      {data: this.world, panelClass: 'dialog-sexy', width: '500px'}).afterClosed().subscribe(
      result => {
        if (result) {
          this.world.image = result;
        }
      }
    );
  }
}
