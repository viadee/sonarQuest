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

@Component({
  selector: 'app-raid-page',
  templateUrl: './raid-page.component.html',
  styleUrls: ['./raid-page.component.css']
})
export class RaidPageComponent implements OnInit, OnDestroy {

  public raid: Raid;
  public monster: Monster;
  public tasks: Task[] = [];
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
    this.raidSubscription = this.raidService.findRaidById(id).subscribe(resp => {
      // Generate Monster from raiddto
      this.monster = new BaseMonster(resp.monsterName, resp.monsterImage,
      resp.raidProgress.totalAmount, resp.raidProgress.numberOfVariable, resp.raidProgress.calculatedProgress, resp.gold, resp.xp);
      this.raid = resp;
      this.raidLeaderBoardList = resp.raidLeaderboardList;
    });
  }

  private subscribeTaskEvent() {
    this.eventService.taskEvents$.subscribe(updated => {
      this.getRaid();
    });
  }
}
