import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AdminSonarCubeComponent} from './admin-sonar-cube.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../services/translate.service.mock.module";
import {
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatSnackBarModule,
  MatTooltipModule
} from "@angular/material";
import {UserServiceTestingModule} from "../../../../services/user.service.mock.module";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {WorldService} from "../../../../services/world.service";
import {LoadingService} from "../../../../services/loading.service";
import {SonarCubeServiceTestingModule} from "../../../../services/sonar-cube.service.mock.module";

describe('AdminSonarCubeComponent', () => {
  let component: AdminSonarCubeComponent;
  let fixture: ComponentFixture<AdminSonarCubeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminSonarCubeComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatCardModule,
        MatInputModule,
        MatDialogModule,
        MatListModule,
        MatDividerModule,
        MatSnackBarModule,
        MatFormFieldModule,
        HttpClientTestingModule,
        SonarCubeServiceTestingModule,
        UserServiceTestingModule
      ],
      providers : [
        WorldService,
        LoadingService
      ]
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
