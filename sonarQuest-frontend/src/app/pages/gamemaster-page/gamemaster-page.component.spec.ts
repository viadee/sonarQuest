import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterPageComponent } from './gamemaster-page.component';

describe('GamemasterPageComponent', () => {
  let component: GamemasterPageComponent;
  let fixture: ComponentFixture<GamemasterPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
