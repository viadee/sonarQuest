import { Quest } from './../../../../Interfaces/Quest';
import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { User } from 'app/Interfaces/User';
import { UserService } from 'app/services/user.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-questlog',
  templateUrl: './questlog.component.html',
  styleUrls: ['./questlog.component.css']
})
export class QuestlogComponent implements OnInit, OnDestroy {
  @Input()
  public questList: Quest[];

  user: User;
  userSubscription: Subscription;

  constructor(public userService: UserService) { }

  ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
  }

  ngOnInit() {
    this.userSubscription = this.userService.user$.subscribe(user => this.user = user);
  }
}
