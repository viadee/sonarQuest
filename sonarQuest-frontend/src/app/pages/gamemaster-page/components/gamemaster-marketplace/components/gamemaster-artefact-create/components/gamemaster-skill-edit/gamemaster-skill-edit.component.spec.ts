import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterSkillEditComponent } from './gamemaster-skill-edit.component';

describe('GamemasterSkillEditComponent', () => {
  let component: GamemasterSkillEditComponent;
  let fixture: ComponentFixture<GamemasterSkillEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterSkillEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterSkillEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
