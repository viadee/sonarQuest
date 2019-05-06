import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {QuestPageComponent} from './quest-page.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../services/translate.service.mock.module";
import {
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatIconModule,
  MatListModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {QuestServiceTestingModule} from "../../services/quest.service.mock.module";
import {WorldServiceTestingModule} from "../../services/world.service.mock.module";
import {ParticipationServiceTestingModule} from "../../services/participation.service.mock.module";
import {AvailableQuestsComponent} from "./components/available-quests/available-quests.component";
import {ParticipatedQuestsComponent} from "./components/participated-quests/participated-quests.component";

describe('QuestPageComponent', () => {
  let component: QuestPageComponent;
  let fixture: ComponentFixture<QuestPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        QuestPageComponent,
        AvailableQuestsComponent,
        ParticipatedQuestsComponent
      ],
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
        ParticipationServiceTestingModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
