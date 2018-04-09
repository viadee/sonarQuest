import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterMarketplaceComponent } from './gamemaster-marketplace.component';

describe('GamemasterMarketplaceComponent', () => {
  let component: GamemasterMarketplaceComponent;
  let fixture: ComponentFixture<GamemasterMarketplaceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterMarketplaceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterMarketplaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
