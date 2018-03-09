import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterQuestCreateComponent } from './gamemaster-quest-create.component';

describe('GamemasterQuestCreateComponent', () => {
  let component: GamemasterQuestCreateComponent;
  let fixture: ComponentFixture<GamemasterQuestCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterQuestCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterQuestCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
