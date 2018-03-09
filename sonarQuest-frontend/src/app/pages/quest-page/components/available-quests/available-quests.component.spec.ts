import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailableQuestsComponent } from './available-quests.component';

describe('AvailableQuestsComponent', () => {
  let component: AvailableQuestsComponent;
  let fixture: ComponentFixture<AvailableQuestsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AvailableQuestsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvailableQuestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
