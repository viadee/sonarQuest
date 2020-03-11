import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SolvedTaskHistoryComponent } from './solved-task-history.component';

describe('SolvedTaskHistoryComponent', () => {
  let component: SolvedTaskHistoryComponent;
  let fixture: ComponentFixture<SolvedTaskHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SolvedTaskHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SolvedTaskHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
