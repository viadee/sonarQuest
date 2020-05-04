import { Quest } from './../../../../Interfaces/Quest';
import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { User } from 'app/Interfaces/User';
import { UserService } from 'app/services/user.service';
import { Subscription } from 'rxjs';
import { MatDialog } from '@angular/material';
import { QuestService } from 'app/services/quest.service';
import { ViewParticipatedQuestComponent } from 'app/pages/quest-page/components/participated-quests/components/view-participated-quest/view-participated-quest.component';

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

  constructor(public userService: UserService,
    private questService: QuestService,
    private dialog: MatDialog) { }

  ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
  }

  ngOnInit() {
    this.userSubscription = this.userService.user$.subscribe(user => this.user = user);
  }

  viewQuest(quest: Quest) {
    this.questService.getQuest(quest.id).then(loadedQuest => {
      this.dialog.open(ViewParticipatedQuestComponent, {
        panelClass: 'dialog-sexy',
        data: loadedQuest,
        width: '500px'
      }).afterClosed().subscribe(() => {
      });
    });
  }
}
