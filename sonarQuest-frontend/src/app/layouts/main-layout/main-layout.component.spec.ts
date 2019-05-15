import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MainLayoutComponent} from './main-layout.component';
import {MatIconModule, MatListModule, MatSelectModule, MatTooltipModule} from "@angular/material";
import {CovalentDataTableModule, CovalentLayoutModule, TdMediaService} from "@covalent/core";
import {RouterTestingModule} from "@angular/router/testing";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TranslateTestingModule} from "../../services/translate.service.mock.module";
import {FormsModule} from "@angular/forms";
import {AuthenticationTestingModule} from "../../authentication/authentication.service.mock.module";
import {UserServiceTestingModule} from "../../services/user.service.mock.module";
import {WorldServiceTestingModule} from "../../services/world.service.mock.module";
import {PermissionServiceTestingModule} from "../../services/permission.service.mock.module";
import {UiDesignServiceTestingModule} from "../../services/ui-design.service.mock.module";
import {EventServiceTestingModule} from "../../services/event.service.mock.module";

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
        AuthenticationTestingModule,
        UserServiceTestingModule,
        WorldServiceTestingModule,
        PermissionServiceTestingModule,
        UiDesignServiceTestingModule,
        EventServiceTestingModule
      ],
      providers: [
        TdMediaService
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
