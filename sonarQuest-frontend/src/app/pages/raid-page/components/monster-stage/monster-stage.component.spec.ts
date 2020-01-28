import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MonsterStageComponent } from './monster-stage.component';

describe('MonsterStageComponent', () => {
  let component: MonsterStageComponent;
  let fixture: ComponentFixture<MonsterStageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MonsterStageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MonsterStageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
