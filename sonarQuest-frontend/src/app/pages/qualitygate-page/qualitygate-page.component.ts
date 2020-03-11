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
  conditionList: Condition[] = [];
  rewardHistoryList: QualityGateRaidRewardHistory[] = [];
  highScore: HighScore;
  actualScore: HighScore;
  slideIndex = 0;
  swiperConfig: SwiperConfigInterface = {
    a11y: true,
    direction: 'horizontal',
    slidesPerView: 3,
    spaceBetween: 30,
    centeredSlides: true,
    height: 20,
    pagination: {
      el: '.swiper-pagination',
      clickable: true
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
  };

  constructor(private raidService: QualityGateRaidService, private worldService: WorldService) {}

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
        this.conditionList = resp.conditions;
        this.highScore = new HighScoreModel(resp.scoreDay, resp.scorePoints);
      },
      err => console.error('Qualitygate Observer got an error: ' + err),
      () => { this.loadActualScore(); this.loadQualityGateHistory() }
    );
  }

  loadQualityGateHistory() {
    const _this = this;
    this.historySub = this.raidSub = this.raidService.getHistory(this.raid.id).subscribe(resp => {
      _this.rewardHistoryList = resp;
      _this.slideIndex = resp.length;
    });
  }

  loadActualScore() {
    const _this = this;
    this.highScoreSub = this.raidService.calculateActualScore(this.raid).subscribe(resp => {
      _this.actualScore = resp;
    });
  }
}
