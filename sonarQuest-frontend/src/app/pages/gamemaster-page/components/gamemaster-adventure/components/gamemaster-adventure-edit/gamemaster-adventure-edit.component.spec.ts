import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterAdventureEditComponent } from './gamemaster-adventure-edit.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatDialogModule, MatDialogRef, MatDividerModule,
  MatFormFieldModule,
  MatIconModule, MatInputModule, MatListModule,
  MatSelectModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {QuestServiceTestingModule} from "../../../../../../services/quest.service.mock.module";
import {AdventureServiceTestingModule} from "../../../../../../services/adventure.service.mock.module";
import {WorldServiceTestingModule} from "../../../../../../services/world.service.mock.module";

describe('GamemasterAdventureEditComponent', () => {
  let component: GamemasterAdventureEditComponent;
  let fixture: ComponentFixture<GamemasterAdventureEditComponent>;

  const data = {
    quests: []
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterAdventureEditComponent ],
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
        MatSelectModule,
        MatFormFieldModule,
        MatInputModule,
        MatDividerModule,
        MatListModule,
        QuestServiceTestingModule,
        AdventureServiceTestingModule,
        WorldServiceTestingModule
      ],
      providers: [
        {provide: MAT_DIALOG_DATA, useValue: data},
        {provide: MatDialogRef, useValue: {}}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterAdventureEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
