import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RaidPageComponent } from './raid-page.component';

describe('RaidPageComponent', () => {
  let component: RaidPageComponent;
  let fixture: ComponentFixture<RaidPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RaidPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RaidPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
