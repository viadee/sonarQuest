import { QualityGateRaidService } from './../../services/quality-gate-raid.service';
import { HighScoreModel } from './../../game-model/highScoreModel';
import { HighScore } from './../../Interfaces/HighScore';
import { Condition } from '../../Interfaces/Condition';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { WorldService } from 'app/services/world.service';
import { World } from 'app/Interfaces/World';
import { Subscription } from 'rxjs';
import { QualityGateRaidRewardHistory } from 'app/Interfaces/QualityGateRaidRewardHistory';
import { QualityGateRaid } from 'app/Interfaces/QualityGateRaid';
import { SwiperModule } from 'ngx-swiper-wrapper';
import { SWIPER_CONFIG } from 'ngx-swiper-wrapper';
import { SwiperConfigInterface } from 'ngx-swiper-wrapper';

@Component({
  selector: 'app-qualitygate-page',
  templateUrl: './qualitygate-page.component.html',
  styleUrls: ['./qualitygate-page.component.css']
})
export class QualitygatePageComponent implements OnInit, OnDestroy {
  private worldSub: Subscription;
  private raidSub: Subscription;
  private historySub: Subscription;
  private highScoreSub: Subscription;

  world: World;
  raid: QualityGateRaid;
  taskList: Condition[] = [];
  historyList: QualityGateRaidRewardHistory[] = [];
  highScore: HighScore;
  actualScore: HighScore;

  config: SwiperConfigInterface = {
    a11y: true,
    direction: 'horizontal',
    slidesPerView: 3,
    slideToClickedSlide: true,
    mousewheel: true,
    scrollbar: false,
    watchSlidesProgress: true,
    navigation: true,
    keyboard: true,
    pagination: false,
    centeredSlides: true,
    loop: true,
    roundLengths: true,
    slidesOffsetBefore: 100,
    slidesOffsetAfter: 100,
    spaceBetween: 50,
    breakpoints: {
        // when window width is >= 320px
        320: {
            slidesPerView: 1
        }
    }
};

  constructor(private raidService: QualityGateRaidService, private worldService: WorldService) { }

  ngOnInit() {
    this.worldSub = this.worldService.currentWorld$.subscribe(world => {
      this.world = world;
    });
    this.loadQualityGate();
  }

  ngOnDestroy(): void {
    this.worldSub.unsubscribe();
    this.raidSub.unsubscribe();
    this.historySub.unsubscribe();
    this.highScoreSub.unsubscribe();
  }

  loadQualityGate() {
    this.raidSub = this.raidService.findByWorld(this.world.id).subscribe(
      resp => {
        this.raid = resp;
        this.taskList = resp.conditions;
        this.highScore = new HighScoreModel(resp.scoreDay, resp.scorePoints);
      },
      err => console.error('Qualitygate Observer got an error: ' + err),
      () => { this.loadActualScore(); this.loadQualityGateHistory() }
    );
  }

  loadQualityGateHistory() {
    this.historySub = this.raidSub = this.raidService.getHistory(this.raid.id).subscribe(resp => {
      this.historyList = resp;
    });
  }

  loadActualScore() {
    this.highScoreSub = this.raidService.calculateActualScore(this.raid).subscribe(resp => {
      this.actualScore = resp;
    });
  }
}
