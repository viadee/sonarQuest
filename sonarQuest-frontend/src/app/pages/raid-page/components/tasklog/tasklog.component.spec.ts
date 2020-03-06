import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TasklogComponent } from './tasklog.component';

describe('TasklogComponent', () => {
  let component: TasklogComponent;
  let fixture: ComponentFixture<TasklogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TasklogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TasklogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
