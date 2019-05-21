import {ImageService} from './../../../../services/image.service';
import {MAT_DIALOG_DATA} from '@angular/material';
import {MatDialogRef} from '@angular/material';
import {Component, OnInit, Inject} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';
import {UserService} from '../../../../services/user.service';
import {User} from '../../../../Interfaces/User';

@Component({
  selector: 'app-my-avatar-edit',
  templateUrl: './my-avatar-edit.component.html',
  styleUrls: ['./my-avatar-edit.component.css']
})
export class AvatarEditComponent implements OnInit {

  imageToShow: any;
  public mail: string = null;

  constructor(
    private dialogRef: MatDialogRef<AvatarEditComponent>,
    private userService: UserService,
    private domSanitizer: DomSanitizer,
    private imageService: ImageService,
    @Inject(MAT_DIALOG_DATA) public user: User) {
    this.user = {...this.user};
  }

  ngOnInit() {
    this.userService.getImage().subscribe((blob) => {
      this.imageToShow = this.imageService.createImageFromBlob(blob);
    })
    if (this.user.mail !== null && !this.user.mail.includes('.local')) {
      this.mail = this.user.mail;
    }
  }

  editDeveloper() {
    if(this.mail !== null){
      this.user.mail = this.mail;
    }
    this.userService.updateUser(this.user).then(() => {
      this.userService.loadUser();
      this.dialogRef.close(this.user);
    })
  }

  cancel() {
    this.dialogRef.close(false);
  }
}
