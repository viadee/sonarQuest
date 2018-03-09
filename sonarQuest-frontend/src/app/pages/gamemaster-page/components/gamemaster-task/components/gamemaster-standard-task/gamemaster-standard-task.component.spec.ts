import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterStandardTaskComponent } from './gamemaster-standard-task.component';

describe('GamemasterStandardTaskComponent', () => {
  let component: GamemasterStandardTaskComponent;
  let fixture: ComponentFixture<GamemasterStandardTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterStandardTaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterStandardTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
