import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDeveloperDeleteComponent } from './admin-developer-delete.component';

describe('AdminDeveloperDeleteComponent', () => {
  let component: AdminDeveloperDeleteComponent;
  let fixture: ComponentFixture<AdminDeveloperDeleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminDeveloperDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDeveloperDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
