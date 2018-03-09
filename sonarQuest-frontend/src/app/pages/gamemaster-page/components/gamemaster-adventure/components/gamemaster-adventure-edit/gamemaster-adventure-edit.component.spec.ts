import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterAdventureEditComponent } from './gamemaster-adventure-edit.component';

describe('GamemasterAdventureEditComponent', () => {
  let component: GamemasterAdventureEditComponent;
  let fixture: ComponentFixture<GamemasterAdventureEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterAdventureEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterAdventureEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
