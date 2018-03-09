import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdventurePageComponent } from './adventure-page.component';

describe('AdventurePageComponent', () => {
  let component: AdventurePageComponent;
  let fixture: ComponentFixture<AdventurePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdventurePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdventurePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
