import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterWorldComponent } from './gamemaster-world.component';

describe('GamemasterWorldComponent', () => {
  let component: GamemasterWorldComponent;
  let fixture: ComponentFixture<GamemasterWorldComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterWorldComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterWorldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
