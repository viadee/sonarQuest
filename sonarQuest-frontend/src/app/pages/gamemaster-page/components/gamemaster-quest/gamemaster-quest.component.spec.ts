import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterQuestComponent } from './gamemaster-quest.component';

describe('GamemasterQuestComponent', () => {
  let component: GamemasterQuestComponent;
  let fixture: ComponentFixture<GamemasterQuestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterQuestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterQuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
