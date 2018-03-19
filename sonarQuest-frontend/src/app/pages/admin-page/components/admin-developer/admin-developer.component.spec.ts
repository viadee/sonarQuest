import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDeveloperComponent } from './admin-developer.component';

describe('AdminDeveloperComponent', () => {
  let component: AdminDeveloperComponent;
  let fixture: ComponentFixture<AdminDeveloperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminDeveloperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDeveloperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
