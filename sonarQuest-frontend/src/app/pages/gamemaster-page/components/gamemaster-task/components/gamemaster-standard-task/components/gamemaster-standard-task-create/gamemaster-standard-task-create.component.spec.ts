import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterStandardTaskCreateComponent } from './gamemaster-standard-task-create.component';

describe('GamemasterStandardTaskCreateComponent', () => {
  let component: GamemasterStandardTaskCreateComponent;
  let fixture: ComponentFixture<GamemasterStandardTaskCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterStandardTaskCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterStandardTaskCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
