import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdventurePageComponent } from './adventure-page.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../services/translate.service.mock.module";
import {MatCardModule, MatDividerModule, MatIconModule, MatTooltipModule} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {AdventureServiceTestingModule} from "../../services/adventure.service.mock.module";
import {WorldServiceTestingModule} from "../../services/world.service.mock.module";

describe('AdventurePageComponent', () => {
  let component: AdventurePageComponent;
  let fixture: ComponentFixture<AdventurePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdventurePageComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        CovalentSearchModule,
        MatDividerModule,
        MatCardModule,
        CovalentDataTableModule,
        CovalentPagingModule,
        AdventureServiceTestingModule,
        WorldServiceTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdventurePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
