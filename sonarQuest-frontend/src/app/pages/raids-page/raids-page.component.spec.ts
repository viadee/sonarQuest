import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RaidsPageComponent } from './raids-page.component';

describe('RaidsPageComponent', () => {
  let component: RaidsPageComponent;
  let fixture: ComponentFixture<RaidsPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RaidsPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RaidsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
