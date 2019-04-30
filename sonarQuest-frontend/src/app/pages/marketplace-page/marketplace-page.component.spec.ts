import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketplacePageComponent } from './marketplace-page.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../services/translate.service.mock.module";
import {MatCardModule, MatDialogModule, MatDividerModule, MatIconModule, MatTooltipModule} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {ArtefactServiceTestingModule} from "../../services/artefact.service.mock.module";
import {UserServiceTestingModule} from "../../services/user.service.mock.module";

describe('MarketplacePageComponent', () => {
  let component: MarketplacePageComponent;
  let fixture: ComponentFixture<MarketplacePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MarketplacePageComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatDividerModule,
        MatDialogModule,
        MatCardModule,
        CovalentDataTableModule,
        CovalentSearchModule,
        CovalentPagingModule,
        ArtefactServiceTestingModule,
        UserServiceTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MarketplacePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
