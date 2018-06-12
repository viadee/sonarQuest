import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../Interfaces/User';

@Component({
  selector: 'app-start-page',
  templateUrl: './start-page.component.html',
  styleUrls: ['./start-page.component.css']
})
export class StartPageComponent implements OnInit {

  public XPpercent = 0;
  protected user: User;

  constructor(public userService: UserService) {
  }

  ngOnInit() {
    this.user = this.userService.getUser();
    this.xpPercent();
  }

  public xpPercent(): void {
    console.log('Rufe XP für User auf ' + this.user);
    this.XPpercent = 100 / this.user.level.max * this.user.xp;
    this.XPpercent.toFixed(2);
  }

}
