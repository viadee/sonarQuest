import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPageComponent } from './admin-page.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../services/translate.service.mock.module";
import {
  MatCardModule, MatDividerModule,
  MatFormFieldModule,
  MatIconModule,
  MatListModule,
  MatTabsModule,
  MatTooltipModule
} from "@angular/material";
import {AdminWorldComponent} from "./components/admin-world/admin-world.component";
import {AdminDeveloperComponent} from "./components/admin-developer/admin-developer.component";
import {AdminSonarQubeComponent} from "./components/admin-sonar-qube/admin-sonar-qube.component";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {WizardServiceTestingModule} from "../../services/wizard.service.mock.module";
import {UserServiceTestingModule} from "../../services/user.service.mock.module";

describe('AdminPageComponent', () => {
  let component: AdminPageComponent;
  let fixture: ComponentFixture<AdminPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AdminPageComponent,
        AdminWorldComponent,
        AdminDeveloperComponent,
        AdminSonarQubeComponent
      ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatCardModule,
        MatListModule,
        MatTabsModule,
        MatIconModule,
        MatDividerModule,
        MatFormFieldModule,
        CovalentDataTableModule,
        CovalentSearchModule,
        CovalentPagingModule,
        WizardServiceTestingModule,
        UserServiceTestingModule
      ],
      providers: [

      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


});
