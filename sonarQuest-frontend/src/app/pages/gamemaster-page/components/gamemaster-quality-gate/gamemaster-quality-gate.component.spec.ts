import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterQualityGateComponent } from './gamemaster-quality-gate.component';

describe('GamemasterQualityGateComponent', () => {
  let component: GamemasterQualityGateComponent;
  let fixture: ComponentFixture<GamemasterQualityGateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterQualityGateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterQualityGateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
