import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MainLayoutComponent} from './main-layout.component';
import {MatIconModule, MatListModule, MatSelectModule} from "@angular/material";
import {CovalentDataTableModule, CovalentLayoutModule} from "@covalent/core";
import {RouterTestingModule} from "@angular/router/testing";
import {TranslateModule} from "@ngx-translate/core";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TranslateTestingModule} from "../../services/translate.service.mock.module";

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
        CovalentLayoutModule,
        CovalentDataTableModule,
        RouterTestingModule,
        MatIconModule,
        TranslateTestingModule
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
