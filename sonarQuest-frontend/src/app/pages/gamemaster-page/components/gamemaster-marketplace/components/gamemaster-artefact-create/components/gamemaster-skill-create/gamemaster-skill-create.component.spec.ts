import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterSkillCreateComponent } from './gamemaster-skill-create.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA, MatDialogModule,
  MatDialogRef, MatFormFieldModule,
  MatGridListModule,
  MatIconModule, MatInputModule, MatSelectModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {SkillServiceTestingModule} from "../../../../../../../../services/skill.service.mock.module";

describe('GamemasterSkillCreateComponent', () => {
  let component: GamemasterSkillCreateComponent;
  let fixture: ComponentFixture<GamemasterSkillCreateComponent>;

  const data = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterSkillCreateComponent ],
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
        MatDialogModule,
        MatInputModule,
        MatFormFieldModule,
        MatSelectModule,
        MatGridListModule,
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
    fixture = TestBed.createComponent(GamemasterSkillCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
