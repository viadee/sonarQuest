import { Subscription } from 'rxjs';
import { RaidService } from 'app/services/raid.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Raid } from 'app/Interfaces/Raid';
import { BaseMonster } from 'app/game-model/base-monster';
import { WorldService } from 'app/services/world.service';
import { World } from 'app/Interfaces/World';

@Component({
  selector: 'app-raids-page',
  templateUrl: './raid-list-page.component.html',
  styleUrls: ['./raid-list-page.component.css']
})
export class RaidListPageComponent implements OnInit, OnDestroy {
  private worldSub: Subscription;
  private raidSub: Subscription;

  raids: Raid[] = [];
  world: World;

  constructor(private raidService: RaidService, private worldService: WorldService) { }

  ngOnInit() {
    this.worldSub = this.worldService.currentWorld$.subscribe(world => {
      this.world = world;
    });
    this.loadRaids();
  }

  ngOnDestroy(): void {
    this.worldSub.unsubscribe();
    this.raidSub.unsubscribe();
  }

  loadRaids() {
    this.raidSub = this.raidService.findAllRaidsByWorld(this.world.id).subscribe(raids => {
      raids.forEach(raid => {
        raid.monster = new BaseMonster(raid.monsterName, raid.monsterImage,
        raid.raidProgress.totalAmount, raid.raidProgress.numberOfVariable, raid.raidProgress.calculatedProgress, raid.gold, raid.xp);
        this.raids.push(raid);
      });
    });
  }
}
