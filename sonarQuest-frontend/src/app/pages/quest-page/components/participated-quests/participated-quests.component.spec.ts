import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ParticipatedQuestsComponent} from './participated-quests.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../services/translate.service.mock.module";
import {MatCardModule, MatDialogModule, MatDividerModule, MatIconModule, MatTooltipModule} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {QuestServiceTestingModule} from "../../../../services/quest.service.mock.module";
import {WorldServiceTestingModule} from "../../../../services/world.service.mock.module";
import {ParticipationServiceTestingModule} from "../../../../services/participation.service.mock.module";

describe('ParticipatedQuestsComponent', () => {
  let component: ParticipatedQuestsComponent;
  let fixture: ComponentFixture<ParticipatedQuestsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParticipatedQuestsComponent ],
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
        MatCardModule,
        CovalentSearchModule,
        CovalentDataTableModule,
        CovalentPagingModule,
        QuestServiceTestingModule,
        WorldServiceTestingModule,
        ParticipationServiceTestingModule
        ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParticipatedQuestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
