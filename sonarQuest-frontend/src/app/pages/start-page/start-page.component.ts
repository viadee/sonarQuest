import { Component, OnInit } from '@angular/core';
import {User} from '../../Interfaces/User';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-start-page',
  templateUrl: './start-page.component.html',
  styleUrls: ['./start-page.component.css']
})
export class StartPageComponent implements OnInit {

  public user: User;
  public XPpercent = 0;
  constructor(public userService: UserService) { }

  ngOnInit() {
    this.userService.avatar$.subscribe({
      next: user => {
        this.user = user;
        this.xpPercent();
      }
    })
  }

  public xpPercent(): void {
    this.XPpercent = 100 / this.user.level.max * this.user.xp;
    this.XPpercent.toFixed(2);
  }

}
