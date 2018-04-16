import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterIconSelectComponent } from './gamemaster-icon-select.component';

describe('GamemasterIconSelectComponent', () => {
  let component: GamemasterIconSelectComponent;
  let fixture: ComponentFixture<GamemasterIconSelectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterIconSelectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterIconSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
