import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAvailableQuestComponent } from './view-available-quest.component';

describe('ViewAvailableQuestComponent', () => {
  let component: ViewAvailableQuestComponent;
  let fixture: ComponentFixture<ViewAvailableQuestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAvailableQuestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAvailableQuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
