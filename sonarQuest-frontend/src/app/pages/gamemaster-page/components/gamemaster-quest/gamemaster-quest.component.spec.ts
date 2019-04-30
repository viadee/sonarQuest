import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterQuestComponent} from './gamemaster-quest.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../services/translate.service.mock.module";
import {
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {TaskServiceTestingModule} from "../../../../services/task.service.mock.module";
import {QuestServiceTestingModule} from "../../../../services/quest.service.mock.module";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {SweetAlert2Module} from "@sweetalert2/ngx-sweetalert2";
import {WorldServiceTestingModule} from "../../../../services/world.service.mock.module";

describe('GamemasterQuestComponent', () => {
  let component: GamemasterQuestComponent;
  let fixture: ComponentFixture<GamemasterQuestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterQuestComponent ],
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
        MatCardModule,
        CovalentPagingModule,
        CovalentSearchModule,
        CovalentDataTableModule,
        TaskServiceTestingModule,
        QuestServiceTestingModule,
        WorldServiceTestingModule,
        SweetAlert2Module
      ],
      providers: [

      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterQuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
