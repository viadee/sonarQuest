import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSonarCubeComponent } from './admin-sonar-cube.component';

describe('AdminSonarCubeComponent', () => {
  let component: AdminSonarCubeComponent;
  let fixture: ComponentFixture<AdminSonarCubeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminSonarCubeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminSonarCubeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
