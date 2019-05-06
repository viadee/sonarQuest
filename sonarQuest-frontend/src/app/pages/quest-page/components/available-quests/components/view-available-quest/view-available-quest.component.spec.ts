import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAvailableQuestComponent } from './view-available-quest.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatDialogModule, MatDialogRef,
  MatDividerModule,
  MatIconModule,
  MatListModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {ParticipationServiceTestingModule} from "../../../../../../services/participation.service.mock.module";

describe('ViewAvailableQuestComponent', () => {
  let component: ViewAvailableQuestComponent;
  let fixture: ComponentFixture<ViewAvailableQuestComponent>;

  const data = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAvailableQuestComponent ],
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
        MatListModule,
        ParticipationServiceTestingModule
      ],
      providers: [
        {provide: MAT_DIALOG_DATA, useValue: data},
        {provide: MatDialogRef, useValue: {}}
        ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAvailableQuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
