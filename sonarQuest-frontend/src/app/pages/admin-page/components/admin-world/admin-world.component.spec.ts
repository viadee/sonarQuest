import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminWorldComponent } from './admin-world.component';

describe('AdminWorldComponent', () => {
  let component: AdminWorldComponent;
  let fixture: ComponentFixture<AdminWorldComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminWorldComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminWorldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
