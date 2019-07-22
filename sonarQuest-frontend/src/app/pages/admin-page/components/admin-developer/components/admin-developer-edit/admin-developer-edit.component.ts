import { TranslateService } from '@ngx-translate/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { MatDialogRef } from '@angular/material';
import { AdminDeveloperComponent } from './../../admin-developer.component';
import { Component, OnInit, Inject } from '@angular/core';
import { UserService } from '../../../../../../services/user.service';
import { User } from '../../../../../../Interfaces/User';
import { ImageService } from '../../../../../../services/image.service';
import { UserToWorld } from '../../../../../../Interfaces/UserToWorld';
import { ITdDataTableColumn } from '@covalent/core';
import { UserToWorldService } from '../../../../../../services/user-to-world.service';
import { Role } from 'app/Interfaces/Role';
import { RoleService } from 'app/services/role.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { WorldService } from 'app/services/world.service';

@Component({
  selector: 'app-admin-developer-edit',
  templateUrl: './admin-developer-edit.component.html',
  styleUrls: ['./admin-developer-edit.component.css']
})
export class AdminDeveloperEditComponent implements OnInit {

  imageToShow: any;
  userToWorlds: UserToWorld[];
  roles: Role[];
  nameTaken: boolean;
  mailTaken: boolean;
  user: User;
  mail: string = null;

  columns: ITdDataTableColumn[] = [
    { name: 'userId', label: 'UserId', hidden: true },
    { name: 'worldId', label: 'WorldId', hidden: true },
    { name: 'worldName', label: 'World' },
    { name: 'editJoined', label: 'Joined' },
  ];
  editForm = new FormGroup({
    name: new FormControl(null, [Validators.required, this.matchNameValidator()]),
    mail: new FormControl(null, [this.matchMailValidator()]),
    role: new FormControl(null, [Validators.required]),
    about: new FormControl(),
    picture: new FormControl()
  });
  constructor(
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    private userService: UserService,
    private translateService: TranslateService,
    @Inject(MAT_DIALOG_DATA) public data: { user: User, users: User[] },
    private imageService: ImageService,
    private roleService: RoleService,
    private userToWorldService: UserToWorldService,
    private worldService: WorldService
  ) {
  }

  ngOnInit() {
    this.translateTable();
    this.loadImages();
    this.userService.user$.subscribe(user => this.user = user)
    this.userToWorldService.getUserToWorlds(this.data.user).then(userToWorlds => {
      this.userToWorlds = userToWorlds
    });
    if (this.data.user.mail !== null && !this.data.user.mail.includes('.local')) {
      this.mail = this.data.user.mail;
    }
    this.roleService.getRoles().then(roles => this.roles = roles);
    this.nameTaken = false;
    this.mailTaken = false;
  }

  translateTable() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        { name: 'worldName', label: col_names.WORLD },
        { name: 'editJoined', label: col_names.JOINED }]
    });
  }

  editDeveloper() {
    if (this.mail != null) {
      this.data.user.mail = this.mail;
    }
    this.userService.updateUser(this.data.user).then(() => {
      this.userToWorldService.updateUserToWorld(this.userToWorlds);
      this.dialogRef.close(true);
      if(this.data.user.id === this.user.id){
        this.worldService.getWorlds()
      }
    })
  }

  cancel() {
    this.dialogRef.close(false);
  }

  loadImages() {
    this.userService.getImageForUser(this.data.user).subscribe((blob) => {
      this.imageService.createImageFromBlob(blob).subscribe(image => this.imageToShow = image);
    });
  }

  matchNameValidator() {
    return (control: FormControl) => {
      const nameVal = control.value;
      if (this.data.users.filter(user => (user.username === nameVal && user.username !== this.data.user.username)).length !== 0) {
        this.nameTaken = true;
      } else {
        this.nameTaken = false;
      }
      return this.nameTaken ? { 'currentName': { nameVal } } : null;
    }
  }
  matchMailValidator() {
    return (control: FormControl) => {
      const mailVal = control.value;
      if (this.data.users.filter(user => (user.mail === mailVal && user.mail !== this.data.user.mail)).length !== 0) {
        this.mailTaken = true;
      } else {
        this.mailTaken = false;
      }
      return this.mailTaken ? { 'currentMail': { mailVal: mailVal } } : null;
    }
  }

  getErrorMessage(control: string) {
    if (this.editForm.get(control).hasError('required')) {
      return 'This value is mandatory';
    }
    if (this.nameTaken) {
      this.editForm.controls['name'].setErrors({ 'matchNameValidator': true });
      return 'Name already taken. Please choose a different name';
    }
    if (this.mailTaken && this.editForm.get('mail').value != null) {
      this.editForm.controls['mail'].setErrors({ 'matchMailValidator': true });
      return 'E-Mail already taken. Please choose a different E-Mail';
    }
  }
}
