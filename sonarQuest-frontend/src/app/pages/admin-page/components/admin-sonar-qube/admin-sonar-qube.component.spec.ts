import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AdminSonarQubeComponent} from './admin-sonar-qube.component';
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
import {LoadingService} from "../../../../services/loading.service";
import {WorldServiceTestingModule} from "../../../../services/world.service.mock.module";
import {SonarQubeServiceTestingModule} from "../../../../services/sonar-qube.service.mock.module";

describe('AdminSonarQubeComponent', () => {
  let component: AdminSonarQubeComponent;
  let fixture: ComponentFixture<AdminSonarQubeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminSonarQubeComponent ],
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
        SonarQubeServiceTestingModule,
        UserServiceTestingModule,
        WorldServiceTestingModule
      ],
      providers : [
        LoadingService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminSonarQubeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
