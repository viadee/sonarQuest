import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailableQuestsComponent } from './available-quests.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatCardModule,
  MatDialogModule, MatDialogRef,
  MatDividerModule,
  MatIconModule,
  MatListModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {QuestServiceTestingModule} from "../../../../services/quest.service.mock.module";
import {WorldServiceTestingModule} from "../../../../services/world.service.mock.module";
import {ParticipationServiceTestingModule} from "../../../../services/participation.service.mock.module";

describe('AvailableQuestsComponent', () => {
  let component: AvailableQuestsComponent;
  let fixture: ComponentFixture<AvailableQuestsComponent>;

  const data = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AvailableQuestsComponent ],
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
        MatCardModule,
        CovalentSearchModule,
        CovalentDataTableModule,
        CovalentPagingModule,
        QuestServiceTestingModule,
        WorldServiceTestingModule,
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
    fixture = TestBed.createComponent(AvailableQuestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
