import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterAddFreeQuestComponent } from './gamemaster-add-free-quest.component';

describe('GamemasterAddFreeQuestComponent', () => {
  let component: GamemasterAddFreeQuestComponent;
  let fixture: ComponentFixture<GamemasterAddFreeQuestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterAddFreeQuestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterAddFreeQuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
