import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestlogComponent } from './questlog.component';

describe('QuestlogComponent', () => {
  let component: QuestlogComponent;
  let fixture: ComponentFixture<QuestlogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuestlogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestlogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
