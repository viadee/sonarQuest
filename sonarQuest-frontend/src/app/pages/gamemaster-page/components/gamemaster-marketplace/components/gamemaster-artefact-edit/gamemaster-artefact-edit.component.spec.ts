import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterArtefactEditComponent} from './gamemaster-artefact-edit.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatCheckboxModule,
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
import {ArtefactServiceTestingModule} from "../../../../../../services/artefact.service.mock.module";
import {SkillServiceTestingModule} from "../../../../../../services/skill.service.mock.module";

describe('GamemasterArtefactEditComponent', () => {
  let component: GamemasterArtefactEditComponent;
  let fixture: ComponentFixture<GamemasterArtefactEditComponent>;

  const data = {
    minLevel: {
      levelNumber: 1
    }
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterArtefactEditComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatDialogModule,
        MatDividerModule,
        MatToolbarModule,
        MatCheckboxModule,
        MatFormFieldModule,
        MatInputModule,
        CovalentDataTableModule,
        ArtefactServiceTestingModule,
        SkillServiceTestingModule
      ],
      providers: [
        {provide: MAT_DIALOG_DATA, useValue: data},
        {provide: MatDialogRef, useValue: {}}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterArtefactEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
