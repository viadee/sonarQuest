import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterAdventureCreateComponent } from './gamemaster-adventure-create.component';

describe('GamemasterAdventureCreateComponent', () => {
  let component: GamemasterAdventureCreateComponent;
  let fixture: ComponentFixture<GamemasterAdventureCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterAdventureCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterAdventureCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
