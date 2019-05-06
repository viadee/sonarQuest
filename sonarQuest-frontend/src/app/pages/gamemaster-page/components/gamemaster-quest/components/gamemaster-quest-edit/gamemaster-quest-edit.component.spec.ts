import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterQuestEditComponent} from './gamemaster-quest-edit.component';
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
  MatListModule,
  MatSelectModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {TaskServiceTestingModule} from "../../../../../../services/task.service.mock.module";
import {QuestServiceTestingModule} from "../../../../../../services/quest.service.mock.module";
import {QuestState} from "../../../../../../Interfaces/QuestState";

describe('GamemasterQuestEditComponent', () => {
  let component: GamemasterQuestEditComponent;
  let fixture: ComponentFixture<GamemasterQuestEditComponent>;

  const data = {
    status: QuestState.OPEN,
    adventure: {},
    tasks: [],
    world: {}
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [GamemasterQuestEditComponent],
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
        MatFormFieldModule,
        MatDividerModule,
        MatInputModule,
        MatCheckboxModule,
        MatSelectModule,
        MatListModule,
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
    fixture = TestBed.createComponent(GamemasterQuestEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
