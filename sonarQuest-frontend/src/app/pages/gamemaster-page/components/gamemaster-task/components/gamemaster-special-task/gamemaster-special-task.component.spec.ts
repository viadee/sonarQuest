import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterSpecialTaskComponent } from './gamemaster-special-task.component';

describe('GamemasterSpecialTaskComponent', () => {
  let component: GamemasterSpecialTaskComponent;
  let fixture: ComponentFixture<GamemasterSpecialTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterSpecialTaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterSpecialTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
