import { SolvedTaskHistoryDto } from '../../../../Interfaces/SolvedTaskHistoryDto';
import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RaidService } from 'app/services/raid.service';
import { Raid } from 'app/Interfaces/Raid';
import { SwiperConfigInterface } from 'ngx-swiper-wrapper';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-solved-task-history',
  templateUrl: './solved-task-history.component.html',
  styleUrls: ['./solved-task-history.component.css']
})
export class SolvedTaskHistoryComponent implements OnInit, OnDestroy {
  @Input()
  raid: Raid;

  historyList: SolvedTaskHistoryDto[];
  amountOfTasks = 0;
  slideIndex = 1;
  swiperConfig: SwiperConfigInterface = {
    a11y: true,
    direction: 'horizontal',
    slidesPerView: 6,
    spaceBetween: 30,
    centeredSlides: true,
    height: 20,
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
  };
  raidSubscription: Subscription;

  constructor(private route: ActivatedRoute, private router: Router, private raidService: RaidService) { }

  ngOnDestroy(): void {
    this.raidSubscription.unsubscribe();
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    const _this = this;
    this.raidSubscription = this.raidService.getSolvedTaskHistoryList(id).subscribe(resp => {
      _this.historyList = resp;
      _this.amountOfTasks = this.raid.raidProgress.totalAmount;
      _this.slideIndex = resp.length;
    });
  }

  calculateHeight(value: number) {
    const progress = (value / this.amountOfTasks) * 100;
    return progress + '%';
  }
}
