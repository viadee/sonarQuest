import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterSpecialTaskCreateComponent } from './gamemaster-special-task-create.component';

describe('GamemasterSpecialTaskCreateComponent', () => {
  let component: GamemasterSpecialTaskCreateComponent;
  let fixture: ComponentFixture<GamemasterSpecialTaskCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterSpecialTaskCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterSpecialTaskCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
