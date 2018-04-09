import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterSkillCreateComponent } from './gamemaster-skill-create.component';

describe('GamemasterSkillCreateComponent', () => {
  let component: GamemasterSkillCreateComponent;
  let fixture: ComponentFixture<GamemasterSkillCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterSkillCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterSkillCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
