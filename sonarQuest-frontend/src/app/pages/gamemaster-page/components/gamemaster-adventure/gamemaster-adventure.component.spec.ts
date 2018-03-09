import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterAdventureComponent } from './gamemaster-adventure.component';

describe('GamemasterAdventureComponent', () => {
  let component: GamemasterAdventureComponent;
  let fixture: ComponentFixture<GamemasterAdventureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterAdventureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterAdventureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
