import { ArtefactServiceMock, ArtefactServiceTestingModule } from './../../../../services/artefact.service.mock.module';
import { ArtefactService } from './../../../../services/artefact.service';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewArtefactComponent} from "./view-artefact.component";
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
import { UserServiceTestingModule } from 'app/services/user.service.mock.module';

describe('ViewArtefactComponent', () => {
  let component: ViewArtefactComponent;
  let fixture: ComponentFixture<ViewArtefactComponent>;

  const data = {
    minLevel: {
      levelNumber: 1
    }

  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewArtefactComponent ],
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
        CovalentDataTableModule,
        ArtefactServiceTestingModule,
        UserServiceTestingModule
      ],
      providers: [
        {provide: MAT_DIALOG_DATA, useValue: data},
        {provide: MatDialogRef, useValue: {}}
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewArtefactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
