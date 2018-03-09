import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterAddFreeTaskComponent } from './gamemaster-add-free-task.component';

describe('GamemasterAddFreeTaskComponent', () => {
  let component: GamemasterAddFreeTaskComponent;
  let fixture: ComponentFixture<GamemasterAddFreeTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterAddFreeTaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterAddFreeTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
