import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AdminDeveloperCreateComponent} from './admin-developer-create.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule, MatInputModule,
  MatListModule,
  MatSelectModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {WizardServiceTestingModule} from "../../../../../../services/wizard.service.mock.module";
import {UserServiceTestingModule} from "../../../../../../services/user.service.mock.module";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {AvatarClassServiceTestingModule} from "../../../../../../services/avatar-class.service.mock.module";
import {AvatarRaceServiceTestingModule} from "../../../../../../services/avatar-race.service.mock.module";
import {RoleServiceTestingModule} from "../../../../../../services/role.service.mock.module";

describe('AdminDeveloperCreateComponent', () => {
  let component: AdminDeveloperCreateComponent;
  let fixture: ComponentFixture<AdminDeveloperCreateComponent>;

  const users = [
    {
      username: "Exmaple",
      mail: "mister.example@provider.com",
      role: {
        name: "someRole"
      },
      aboutMe: "I'm Mister Example."
    }
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminDeveloperCreateComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatSelectModule,
        MatDialogModule,
        MatInputModule,
        MatListModule,
        MatDividerModule,
        MatFormFieldModule,
        CovalentDataTableModule,
        CovalentSearchModule,
        CovalentPagingModule,
        WizardServiceTestingModule,
        UserServiceTestingModule,
        HttpClientTestingModule,
        AvatarClassServiceTestingModule,
        AvatarRaceServiceTestingModule,
        RoleServiceTestingModule
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: users },
        { provide: MatDialogRef, useValue: {} }
      ]
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
