import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterRaidComponent } from './gamemaster-raid.component';

describe('GamemasterRaidComponent', () => {
  let component: GamemasterRaidComponent;
  let fixture: ComponentFixture<GamemasterRaidComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterRaidComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterRaidComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
