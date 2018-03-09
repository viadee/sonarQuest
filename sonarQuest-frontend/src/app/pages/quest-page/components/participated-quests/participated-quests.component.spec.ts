import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipatedQuestsComponent } from './participated-quests.component';

describe('ParticipatedQuestsComponent', () => {
  let component: ParticipatedQuestsComponent;
  let fixture: ComponentFixture<ParticipatedQuestsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParticipatedQuestsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParticipatedQuestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
