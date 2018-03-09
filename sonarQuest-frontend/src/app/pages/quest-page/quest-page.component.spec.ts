import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestPageComponent } from './quest-page.component';

describe('QuestPageComponent', () => {
  let component: QuestPageComponent;
  let fixture: ComponentFixture<QuestPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuestPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
