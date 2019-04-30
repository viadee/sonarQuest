import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AdminDeveloperComponent} from './admin-developer.component';
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
  MatListModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {WizardServiceTestingModule} from "../../../../services/wizard.service.mock.module";
import {UserServiceTestingModule} from "../../../../services/user.service.mock.module";
import {WorldService} from "../../../../services/world.service";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('AdminDeveloperComponent', () => {
  let component: AdminDeveloperComponent;
  let fixture: ComponentFixture<AdminDeveloperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminDeveloperComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatCardModule,
        MatDialogModule,
        MatListModule,
        MatDividerModule,
        MatFormFieldModule,
        CovalentDataTableModule,
        CovalentSearchModule,
        CovalentPagingModule,
        WizardServiceTestingModule,
        UserServiceTestingModule,
        HttpClientTestingModule
      ],
      providers : [
        WorldService
      ]
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
