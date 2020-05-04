import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RaidLeaderboardComponent } from './raid-leaderboard.component';

describe('RaidLeaderboardComponent', () => {
  let component: RaidLeaderboardComponent;
  let fixture: ComponentFixture<RaidLeaderboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RaidLeaderboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RaidLeaderboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
