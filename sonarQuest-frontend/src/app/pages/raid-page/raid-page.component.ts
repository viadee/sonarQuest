import { Raid } from 'app/Interfaces/Raid';
import { RaidService } from 'app/services/raid.service';
import { BaseMonster } from './../../game-model/base-monster';
import { Monster } from './../../Interfaces/Monster';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { Subscription } from 'rxjs';
import { Task } from 'app/Interfaces/Task';

@Component({
  selector: 'app-raid-page',
  templateUrl: './raid-page.component.html',
  styleUrls: ['./raid-page.component.css']
})
export class RaidPageComponent implements OnInit, OnDestroy {

  public raid: Raid;
  public monster: Monster;
  public tasks: Task[] = [];
  private raidSubscription: Subscription;

  constructor(private route: ActivatedRoute, private router: Router, private raidService: RaidService) { }

  ngOnDestroy(): void {
    this.raidSubscription.unsubscribe();
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.raidSubscription = this.raidService.findRaidById(id).subscribe(resp => {
      this.monster = new BaseMonster(resp.monsterName, resp.monsterImage,
      resp.raidProgress.totalAmount, resp.raidProgress.numberOfVariable, resp.raidProgress.calculatedProgress);
      this.raid = resp;
      this.tasks = resp.tasks;
    });
  }
}
