import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {GitServerService} from '../../../../../../services/git-server.service';
import {GitServer} from '../../../../../../Interfaces/GitServer';

@Component({
  selector: 'app-edit-git',
  templateUrl: './edit-git.component.html',
  styleUrls: ['./edit-git.component.css']
})
export class EditGitComponent implements OnInit {
  constructor(private dialogRef: MatDialogRef<EditGitComponent>,
              @Inject(MAT_DIALOG_DATA) public server: GitServer,
              private gitServerService: GitServerService
  ) {
  }

  ngOnInit() {
  }

  updateServer() {
    this.gitServerService.updateGitServer(this.server).then(() => this.dialogRef.close())
  }

  deleteServer() {
    this.gitServerService.deleteGitServer(this.server).then(() => this.dialogRef.close())
  }
}
