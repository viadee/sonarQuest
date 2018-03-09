import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {World} from "../../../../../../Interfaces/World";
import {WorldService} from "../../../../../../services/world.service";

@Component({
  selector: 'app-edit-world',
  templateUrl: './edit-world.component.html',
  styleUrls: ['./edit-world.component.css']
})
export class EditWorldComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<EditWorldComponent>,
              @Inject(MAT_DIALOG_DATA) public world: World,
              private worldService: WorldService) { }

  ngOnInit() {
  }

  updateWorld(){
    this.worldService.updateWorld(this.world).then(()=>this.dialogRef.close())
  }

}
