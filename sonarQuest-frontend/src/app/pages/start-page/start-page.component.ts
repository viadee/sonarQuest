import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../Interfaces/User';
import { TranslateService } from '@ngx-translate/core';
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';

@Component({
  selector: 'app-start-page',
  templateUrl: './start-page.component.html',
  styleUrls: ['./start-page.component.css']
})
export class StartPageComponent implements OnInit {

  public XPpercent = 0;
  public user: User;


  constructor(public userService: UserService) {
  }

  ngOnInit() {
    this.userService.user$.subscribe(user => this.user = user);
    this.xpPercent();

  }


  public xpPercent(): void {
    console.log('Calling XP for user: ' + this.user);
    this.XPpercent = 100 / this.user.level.maxXp * this.user.xp;
    this.XPpercent.toFixed(2);
  }
}
