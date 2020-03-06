import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConditiontasklogComponent } from './conditiontasklog.component';

describe('ConditiontasklogComponent', () => {
  let component: ConditiontasklogComponent;
  let fixture: ComponentFixture<ConditiontasklogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConditiontasklogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConditiontasklogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
