import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterStandardTaskEditComponent } from './gamemaster-standard-task-edit.component';

describe('GamemasterStandardTaskEditComponent', () => {
  let component: GamemasterStandardTaskEditComponent;
  let fixture: ComponentFixture<GamemasterStandardTaskEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterStandardTaskEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterStandardTaskEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
