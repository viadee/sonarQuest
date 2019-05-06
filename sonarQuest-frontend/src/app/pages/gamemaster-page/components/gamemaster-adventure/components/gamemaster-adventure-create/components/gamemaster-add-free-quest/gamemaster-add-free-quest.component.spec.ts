import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterAddFreeQuestComponent} from './gamemaster-add-free-quest.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
  MatIconModule,
  MatListModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {QuestServiceTestingModule} from "../../../../../../../../services/quest.service.mock.module";
import {QuestState} from "../../../../../../../../Interfaces/QuestState";

describe('GamemasterAddFreeQuestComponent', () => {
  let component: GamemasterAddFreeQuestComponent;
  let fixture: ComponentFixture<GamemasterAddFreeQuestComponent>;

  const data = [
    {},
    [
      {
        id: 1,
        title: "Quest Title",
        story: "a nice story",
        creatorName: "Mystery man",
        status: QuestState.OPEN,
        gold: 23,
        xp: 42,
        image: "PATH/TO/IMAGE",
        visible: true,
        startdate: "2019-01-01",
        enddate: "2019-12-31",
        world: {},
        adventure: {},
        tasks: [],
        participations: [],
        participants: []
      }
    ]
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [GamemasterAddFreeQuestComponent],
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
        MatListModule,
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
    fixture = TestBed.createComponent(GamemasterAddFreeQuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
