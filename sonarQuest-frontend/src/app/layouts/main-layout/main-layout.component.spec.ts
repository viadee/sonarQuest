import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MainLayoutComponent} from './main-layout.component';
import {MatIconModule, MatListModule, MatSelectModule, MatTooltipModule} from "@angular/material";
import {CovalentDataTableModule, CovalentLayoutModule, TdMediaService} from "@covalent/core";
import {RouterTestingModule} from "@angular/router/testing";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TranslateTestingModule} from "../../services/translate.service.mock.module";
import {FormsModule} from "@angular/forms";
import {UiDesignService} from "../../services/ui-design.service";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {WorldService} from "../../services/world.service";
import {PermissionService} from "../../services/permission.service";
import {AuthenticationTestingModule} from "../../authentication/authentication.service.mock.module";
import {UserServiceTestingModule} from "../../services/user.service.mock.module";

describe('MainLayoutComponent', () => {
  let component: MainLayoutComponent;
  let fixture: ComponentFixture<MainLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MainLayoutComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        MatSelectModule,
        MatListModule,
        MatIconModule,
        MatTooltipModule,
        CovalentLayoutModule,
        CovalentDataTableModule,
        RouterTestingModule,
        FormsModule,
        TranslateTestingModule,
        HttpClientTestingModule,
        AuthenticationTestingModule,
        UserServiceTestingModule
      ],
      providers: [
        UiDesignService,
        TdMediaService,
        WorldService,
        PermissionService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
