import {MAT_DIALOG_DATA} from '@angular/material';
import {AdminDeveloperComponent} from './../../admin-developer.component';
import {MatDialogRef} from '@angular/material';
import {Component, OnInit, Inject} from '@angular/core';
import {UserService} from '../../../../../../services/user.service';
import {User} from '../../../../../../Interfaces/User';

@Component({
  selector: 'app-admin-developer-delete',
  templateUrl: './admin-developer-delete.component.html',
  styleUrls: ['./admin-developer-delete.component.css']
})
export class AdminDeveloperDeleteComponent implements OnInit {


  constructor(
    private userService: UserService,
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    @Inject(MAT_DIALOG_DATA) public user: User
  ) {
  }

  ngOnInit() {
  }

  cancel() {
    this.dialogRef.close(false)
  }

  delete() {
    this.userService.deleteUser(this.user).then(() => {
      this.dialogRef.close(true)
    })
  }

}
