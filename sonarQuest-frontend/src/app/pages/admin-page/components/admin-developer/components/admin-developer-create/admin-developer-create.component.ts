import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {AdminDeveloperComponent} from './../../admin-developer.component';
import {Component, OnInit, Inject} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../../../../../../Interfaces/User';
import {UserService} from '../../../../../../services/user.service';

@Component({
  selector: 'app-admin-developer-create',
  templateUrl: './admin-developer-create.component.html',
  styleUrls: ['./admin-developer-create.component.css']
})
export class AdminDeveloperCreateComponent implements OnInit {

  public username: String;
  public aboutMe: String;
  public images: any[];
  public selectedImage: string;
  public nameTaken: boolean;

  protected roles: String[] = ['GAMEMASTER', 'DEVELOPER', 'ADMIN'];

  createForm = new FormGroup({
    name: new FormControl(null, [Validators.required, this.matchNameValidator()]),
    role: new FormControl(null, [Validators.required]),
    password: new FormControl(null, [Validators.required]),
    about: new FormControl()
  });

  constructor(
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public users: User[]) {
  }

  ngOnInit() {
    this.loadImages();
    this.selectedImage = 'http://via.placeholder.com/200x200';
    this.nameTaken = false;
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
    if (!this.nameTaken) {
      const new_developer: User = {
        username: this.createForm.get('name').value,
        aboutMe: this.createForm.get('about').value,
        picture: this.selectedImage,
        role: {name: this.createForm.get('role').value},
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

  loadImages() {
    this.images = [];
    for (let i = 0; i < 15; i++) {
      this.images[i] = {};
      this.images[i].src = 'assets/images/quest/hero' + (i + 1) + '.jpg';
      this.images[i].name = 'hero' + (i + 1);
    }
  }

  getErrorMessage() {
    if (this.createForm.get('name').hasError('required')) {
      return 'You must enter a name';
    }
    if (this.nameTaken) {
      this.createForm.controls['name'].setErrors({'matchNameValidator': true});
      return 'Name already taken. Please choose a different name';
    }
  }
}



