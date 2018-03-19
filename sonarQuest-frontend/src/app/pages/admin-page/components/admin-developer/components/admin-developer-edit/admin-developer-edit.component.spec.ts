import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDeveloperEditComponent } from './admin-developer-edit.component';

describe('AdminDeveloperEditComponent', () => {
  let component: AdminDeveloperEditComponent;
  let fixture: ComponentFixture<AdminDeveloperEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminDeveloperEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDeveloperEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
