import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RaidListPageComponent } from './raid-list-page.component';

describe('RaidsPageComponent', () => {
  let component: RaidListPageComponent;
  let fixture: ComponentFixture<RaidListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RaidListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RaidListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
