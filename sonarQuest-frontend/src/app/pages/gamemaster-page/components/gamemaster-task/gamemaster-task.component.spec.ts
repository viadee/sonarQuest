import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterTaskComponent } from './gamemaster-task.component';

describe('GamemasterTaskComponent', () => {
  let component: GamemasterTaskComponent;
  let fixture: ComponentFixture<GamemasterTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterTaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
