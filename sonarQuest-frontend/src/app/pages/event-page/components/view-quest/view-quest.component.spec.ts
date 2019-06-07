import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewQuestComponent } from './view-quest.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatDialogModule, MatDialogRef,
  MatDividerModule,
  MatIconModule,
  MatListModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {ParticipationServiceTestingModule} from "../../../../services/participation.service.mock.module";
import {SonarQubeServiceTestingModule} from "../../../../services/sonar-qube.service.mock.module";
import {WorldServiceTestingModule} from "../../../../services/world.service.mock.module";
import {UserServiceTestingModule} from "../../../../services/user.service.mock.module";
import {TaskServiceTestingModule} from "../../../../services/task.service.mock.module";
import {QuestServiceTestingModule} from "../../../../services/quest.service.mock.module";

describe('ViewQuestComponent', () => {
  let component: ViewQuestComponent;
  let fixture: ComponentFixture<ViewQuestComponent>;

  const data = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewQuestComponent ],
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
        ParticipationServiceTestingModule,
        SonarQubeServiceTestingModule,
        WorldServiceTestingModule,
        UserServiceTestingModule,
        TaskServiceTestingModule,
        QuestServiceTestingModule
      ],
      providers: [
        {provide: MAT_DIALOG_DATA, useValue: data},
        {provide: MatDialogRef, useValue: {}}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewQuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
