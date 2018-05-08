import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSonarCubeSelectBackgroundComponent } from './admin-sonar-cube-select-background.component';

describe('AdminSonarCubeSelectBackgroundComponent', () => {
  let component: AdminSonarCubeSelectBackgroundComponent;
  let fixture: ComponentFixture<AdminSonarCubeSelectBackgroundComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminSonarCubeSelectBackgroundComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminSonarCubeSelectBackgroundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
