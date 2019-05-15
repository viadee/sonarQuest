import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterSkillEditComponent } from './gamemaster-skill-edit.component';
import {TranslateTestingModule} from "../../../../../../../../services/translate.service.mock.module";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {
  MAT_DIALOG_DATA,
  MatDialogModule, MatDialogRef,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule, MatInputModule, MatSelectModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentDataTableModule} from "@covalent/core";
import {ArtefactServiceTestingModule} from "../../../../../../../../services/artefact.service.mock.module";
import {SkillServiceTestingModule} from "../../../../../../../../services/skill.service.mock.module";

describe('GamemasterSkillEditComponent', () => {
  let component: GamemasterSkillEditComponent;
  let fixture: ComponentFixture<GamemasterSkillEditComponent>;

  const data = {
    minLevel: {
      levelNumber: 1
    }
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterSkillEditComponent ],
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
        MatSelectModule,
        MatDividerModule,
        MatToolbarModule,
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
    fixture = TestBed.createComponent(GamemasterSkillEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
