import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDeveloperCreateComponent } from './admin-developer-create.component';

describe('AdminDeveloperCreateComponent', () => {
  let component: AdminDeveloperCreateComponent;
  let fixture: ComponentFixture<AdminDeveloperCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminDeveloperCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDeveloperCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
