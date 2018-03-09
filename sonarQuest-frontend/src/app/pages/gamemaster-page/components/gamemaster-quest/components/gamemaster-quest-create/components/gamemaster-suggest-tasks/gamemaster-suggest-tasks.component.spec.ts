import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterSuggestTasksComponent } from './gamemaster-suggest-tasks.component';

describe('GamemasterSuggestTasksComponent', () => {
  let component: GamemasterSuggestTasksComponent;
  let fixture: ComponentFixture<GamemasterSuggestTasksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterSuggestTasksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterSuggestTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
