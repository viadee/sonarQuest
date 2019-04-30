import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDeveloperDeleteComponent } from './admin-developer-delete.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatDialogModule, MatDialogRef, MatDividerModule, MatFormFieldModule,
  MatIconModule,
  MatInputModule,
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

describe('AdminDeveloperDeleteComponent', () => {
  let component: AdminDeveloperDeleteComponent;
  let fixture: ComponentFixture<AdminDeveloperDeleteComponent>;

  const user = {
      username: "Exmaple",
      mail: "mister.example@provider.com",
      role: {
        name: "someRole"
      },
      aboutMe: "I'm Mister Example."
    };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminDeveloperDeleteComponent ],
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
        { provide: MAT_DIALOG_DATA, useValue: user },
        { provide: MatDialogRef, useValue: {} }
      ]
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
