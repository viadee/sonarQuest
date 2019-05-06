import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewParticipatedQuestComponent } from './view-participated-quest.component';
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
import {SonarCubeServiceTestingModule} from "../../../../../../services/sonar-cube.service.mock.module";
import {WorldServiceTestingModule} from "../../../../../../services/world.service.mock.module";
import {UserServiceTestingModule} from "../../../../../../services/user.service.mock.module";
import {TaskServiceTestingModule} from "../../../../../../services/task.service.mock.module";
import {QuestServiceTestingModule} from "../../../../../../services/quest.service.mock.module";

describe('ViewParticipatedQuestComponent', () => {
  let component: ViewParticipatedQuestComponent;
  let fixture: ComponentFixture<ViewParticipatedQuestComponent>;

  const data = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewParticipatedQuestComponent ],
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
        SonarCubeServiceTestingModule,
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
    fixture = TestBed.createComponent(ViewParticipatedQuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
