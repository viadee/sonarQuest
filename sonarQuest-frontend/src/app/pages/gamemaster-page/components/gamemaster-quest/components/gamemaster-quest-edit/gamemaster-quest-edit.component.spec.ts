import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterQuestEditComponent } from './gamemaster-quest-edit.component';

describe('GamemasterQuestEditComponent', () => {
  let component: GamemasterQuestEditComponent;
  let fixture: ComponentFixture<GamemasterQuestEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterQuestEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterQuestEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
