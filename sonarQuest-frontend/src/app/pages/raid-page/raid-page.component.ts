import { RaidLeaderboard } from 'app/Interfaces/RaidLeaderboard';
import { Raid } from 'app/Interfaces/Raid';
import { RaidService } from 'app/services/raid.service';
import { BaseMonster } from './../../game-model/base-monster';
import { Monster } from './../../Interfaces/Monster';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Task } from 'app/Interfaces/Task';
import { EventService } from 'app/services/event.service';
import { Quest } from 'app/Interfaces/Quest';

@Component({
  selector: 'app-raid-page',
  templateUrl: './raid-page.component.html',
  styleUrls: ['./raid-page.component.css']
})
export class RaidPageComponent implements OnInit, OnDestroy {

  public raid: Raid;
  public monster: Monster;
  public tasks: Task[] = [];
  public quests: Quest[] = [];
  public raidLeaderBoardList: RaidLeaderboard[] = [];
  private raidSubscription: Subscription;

  constructor(private route: ActivatedRoute, private router: Router, private raidService: RaidService,
    private eventService: EventService) { }

  ngOnDestroy(): void {
    this.raidSubscription.unsubscribe();
  }

  ngOnInit() {
    this.getRaid();
    this.subscribeTaskEvent();
  }

  getRaid() {
    const id = this.route.snapshot.paramMap.get('id');
    const _this = this;
    this.raidSubscription = this.raidService.findRaidById(id).subscribe(resp => {
      _this.monster = this.createBaseMonster(resp);
      _this.raid = resp;
      _this.raidLeaderBoardList = resp.raidLeaderboardList;
      _this.quests = resp.quests;
      this.sortRaidQuests();
    });
  }

  private sortRaidQuests() {
    this.quests.sort((quest1, quest2) => {
      if (quest1.questProgress > quest2.questProgress) {
          return -1;
      }
      if (quest1.questProgress < quest2.questProgress) {
          return 1;
      }
      return 0;
    });
  }

  private createBaseMonster(raid: Raid) {
    return new BaseMonster(raid.monsterName, raid.monsterImage,
      raid.raidProgress.totalAmount, raid.raidProgress.numberOfVariable, raid.raidProgress.calculatedProgress, raid.gold, raid.xp);
  }

  private subscribeTaskEvent() {
    this.eventService.taskEvents$.subscribe(updated => {
      this.getRaid();
    });
  }
}
