import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {UsernamePassword} from './username-password';
import { UserService } from '../services/user.service';
import { AuthenticationService } from './authentication.service';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginFormGroup: FormGroup;

  constructor(private formBuilder: FormBuilder, private authService: AuthenticationService, private dialog: MatDialog) {
  }

  ngOnInit() {
    this.loginFormGroup = this.formBuilder.group(
      {
        username: '',
        password: ''
      }
    );
  }

  onSubmit() {
    console.log(this.loginFormGroup.value);
    this.authService.login(this.loginFormGroup.value.username, this.loginFormGroup.value.password);
    this.dialog.closeAll();
  }
}
