import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../authentication/authentication.service";

@Component({
  selector: 'sq-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  username = '';
  password = '';

  constructor(private authService: AuthenticationService) {
  }

  ngOnInit() {
  }

  onSubmit() {
    this.authService.login(this.username, this.password);
  }

  onKeyDown(event) {
    if (event.key === "Enter" && this.username != '' && this.password != '') {
      this.onSubmit();
    }
  }
}
