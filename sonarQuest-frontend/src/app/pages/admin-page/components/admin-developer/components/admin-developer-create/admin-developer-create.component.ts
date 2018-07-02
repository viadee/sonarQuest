import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {AdminDeveloperComponent} from './../../admin-developer.component';
import {Component, OnInit, Inject} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../../../../../../Interfaces/User';
import {UserService} from '../../../../../../services/user.service';
import {AvatarClass} from '../../../../../../Interfaces/AvatarClass';
import {AvatarRace} from '../../../../../../Interfaces/AvatarRace';
import {AvatarClassService} from '../../../../../../services/avatar-class.service';
import {AvatarRaceService} from '../../../../../../services/avatar-race.service';
import {Role} from '../../../../../../Interfaces/Role';
import {RoleService} from '../../../../../../services/role.service';

@Component({
  selector: 'app-admin-developer-create',
  templateUrl: './admin-developer-create.component.html',
  styleUrls: ['./admin-developer-create.component.css']
})
export class AdminDeveloperCreateComponent implements OnInit {

  public username: String;
  public aboutMe: String;
  public nameTaken: boolean;

  protected roles: Role[];
  protected classes: AvatarClass[];
  protected races: AvatarRace[];

  createForm = new FormGroup({
    name: new FormControl(null, [Validators.required, this.matchNameValidator()]),
    role: new FormControl(null, [Validators.required]),
    password: new FormControl(null, [Validators.required]),
    about: new FormControl(),
    picture: new FormControl(),
    race: new FormControl(null, [Validators.required]),
    class: new FormControl(null, [Validators.required])
  });

  constructor(
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    private userService: UserService,
    private avatarClassServoce: AvatarClassService,
    private avatarRaceServoce: AvatarRaceService,
    private roleService: RoleService,
    @Inject(MAT_DIALOG_DATA) public users: User[]) {
  }

  ngOnInit() {
    this.nameTaken = false;
    this.avatarClassServoce.getClasses().then(classes => this.classes = classes);
    this.avatarRaceServoce.getRaces().then(races => this.races = races);
    this.roleService.getRoles().then(roles => this.roles = roles);
  }

  matchNameValidator() {
    return (control: FormControl) => {
      const nameVal = control.value;
      if (this.users.filter(user => (user.username === nameVal)).length !== 0) {
        this.nameTaken = true;
      } else {
        this.nameTaken = false;
      }
      console.log(this.nameTaken);
      return this.nameTaken ? {'currentName': {nameVal}} : null;
    }
  }

  createDeveloper() {
    if (!this.nameTaken && this.createForm.valid) {
      const new_developer: User = {
        username: this.createForm.get('name').value,
        aboutMe: this.createForm.get('about').value,
        picture: this.createForm.get('picture').value,
        role: this.createForm.get('role').value,
        avatarClass:  this.createForm.get('class').value,
        avatarRace: this.createForm.get('race').value,
        password: this.createForm.get('password').value
      };
      this.userService.updateUser(new_developer)
        .then(developer => {
          this.dialogRef.close(developer);
        })
    }
  }

  cancel() {
    this.dialogRef.close();
  }

  getErrorMessage() {
    if (this.createForm.get('name').hasError('required')) {
      return 'This value is mandatory';
    }
    if (this.nameTaken) {
      this.createForm.controls['name'].setErrors({'matchNameValidator': true});
      return 'Name already taken. Please choose a different name';
    }
  }
}



