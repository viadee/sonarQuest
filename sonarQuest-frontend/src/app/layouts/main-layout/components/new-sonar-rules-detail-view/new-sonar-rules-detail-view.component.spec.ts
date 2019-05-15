import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSonarRulesDetailViewComponent } from './new-sonar-rules-detail-view.component';

describe('NewSonarRulesDetailViewComponent', () => {
  let component: NewSonarRulesDetailViewComponent;
  let fixture: ComponentFixture<NewSonarRulesDetailViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewSonarRulesDetailViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewSonarRulesDetailViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
