import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = '';
  password = '';

  constructor(private authService: AuthenticationService, private dialog: MatDialog) {
  }

  ngOnInit() {
  }

  onSubmit() {
    this.authService.login(this.username, this.password);
    this.dialog.closeAll();
  }

  onKeyDown(event) {
    if (event.key === "Enter" && this.username != '' && this.password != '') {
      this.onSubmit();
    }
  }
}
