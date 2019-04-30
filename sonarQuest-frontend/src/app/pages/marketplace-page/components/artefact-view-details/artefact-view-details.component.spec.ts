import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ArtefactViewDetailsComponent} from "./artefact-view-details.component";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentDataTableModule} from "@covalent/core";

describe('ArtefactViewDetailsComponent', () => {
  let component: ArtefactViewDetailsComponent;
  let fixture: ComponentFixture<ArtefactViewDetailsComponent>;

  const data = {
    minLevel: {
      levelNumber: 1
    }

  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArtefactViewDetailsComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        MatDividerModule,
        MatToolbarModule,
        MatDialogModule,
        CovalentDataTableModule
      ],
      providers: [
        {provide: MAT_DIALOG_DATA, useValue: data},
        {provide: MatDialogRef, useValue: {}}
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtefactViewDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
