import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterQuestCreateComponent} from './gamemaster-quest-create.component';
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
import {QuestServiceTestingModule} from "../../../../../../services/quest.service.mock.module";
import {TaskServiceTestingModule} from "../../../../../../services/task.service.mock.module";
import {WorldServiceTestingModule} from "../../../../../../services/world.service.mock.module";
import {UserServiceTestingModule} from "../../../../../../services/user.service.mock.module";

describe('GamemasterQuestCreateComponent', () => {
  let component: GamemasterQuestCreateComponent;
  let fixture: ComponentFixture<GamemasterQuestCreateComponent>;

  const data = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterQuestCreateComponent ],
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
        MatSelectModule,
        MatDividerModule,
        MatDialogModule,
        MatFormFieldModule,
        MatInputModule,
        MatListModule,
        MatCheckboxModule,
        QuestServiceTestingModule,
        TaskServiceTestingModule,
        WorldServiceTestingModule,
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
    fixture = TestBed.createComponent(GamemasterQuestCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
