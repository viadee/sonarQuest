import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RaidHighscoreComponent } from './raid-highscore.component';

describe('RaidHighscoreComponent', () => {
  let component: RaidHighscoreComponent;
  let fixture: ComponentFixture<RaidHighscoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RaidHighscoreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RaidHighscoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
