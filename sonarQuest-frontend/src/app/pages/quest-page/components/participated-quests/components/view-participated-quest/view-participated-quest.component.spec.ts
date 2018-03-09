import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewParticipatedQuestComponent } from './view-participated-quest.component';

describe('ViewParticipatedQuestComponent', () => {
  let component: ViewParticipatedQuestComponent;
  let fixture: ComponentFixture<ViewParticipatedQuestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewParticipatedQuestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewParticipatedQuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
