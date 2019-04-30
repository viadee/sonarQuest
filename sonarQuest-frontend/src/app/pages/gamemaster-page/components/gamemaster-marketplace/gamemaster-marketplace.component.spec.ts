import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterMarketplaceComponent} from './gamemaster-marketplace.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../services/translate.service.mock.module";
import {
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatIconModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {SweetAlert2Module} from "@sweetalert2/ngx-sweetalert2";
import {ArtefactServiceTestingModule} from "../../../../services/artefact.service.mock.module";

describe('GamemasterMarketplaceComponent', () => {
  let component: GamemasterMarketplaceComponent;
  let fixture: ComponentFixture<GamemasterMarketplaceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterMarketplaceComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatToolbarModule,
        MatDividerModule,
        MatCardModule,
        MatDialogModule,
        CovalentDataTableModule,
        CovalentSearchModule,
        CovalentPagingModule,
        SweetAlert2Module,
        ArtefactServiceTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterMarketplaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
