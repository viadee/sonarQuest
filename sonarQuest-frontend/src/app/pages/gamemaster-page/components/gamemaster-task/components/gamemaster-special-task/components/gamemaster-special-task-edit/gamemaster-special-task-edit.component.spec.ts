import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterSpecialTaskEditComponent } from './gamemaster-special-task-edit.component';

describe('GamemasterSpecialTaskEditComponent', () => {
  let component: GamemasterSpecialTaskEditComponent;
  let fixture: ComponentFixture<GamemasterSpecialTaskEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterSpecialTaskEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterSpecialTaskEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
