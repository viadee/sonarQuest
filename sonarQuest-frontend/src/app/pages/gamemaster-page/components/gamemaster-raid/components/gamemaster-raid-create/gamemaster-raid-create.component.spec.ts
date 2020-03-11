import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterRaidCreateComponent } from './gamemaster-raid-create.component';

describe('GamemasterRaidCreateComponent', () => {
  let component: GamemasterRaidCreateComponent;
  let fixture: ComponentFixture<GamemasterRaidCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterRaidCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterRaidCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
