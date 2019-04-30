import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterStandardTaskComponent} from './gamemaster-standard-task.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../services/translate.service.mock.module";
import {
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatIconModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {QuestServiceTestingModule} from "../../../../../../services/quest.service.mock.module";
import {AdventureServiceTestingModule} from "../../../../../../services/adventure.service.mock.module";
import {WorldServiceTestingModule} from "../../../../../../services/world.service.mock.module";
import {StandardTaskServiceTestingModule} from "../../../../../../services/standard-task.service.mock.module";
import {TaskServiceTestingModule} from "../../../../../../services/task.service.mock.module";
import {LoadingService} from "../../../../../../services/loading.service";

describe('GamemasterStandardTaskComponent', () => {
  let component: GamemasterStandardTaskComponent;
  let fixture: ComponentFixture<GamemasterStandardTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterStandardTaskComponent ],
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
        MatDividerModule,
        MatCardModule,
        CovalentDataTableModule,
        CovalentSearchModule,
        CovalentPagingModule,
        QuestServiceTestingModule,
        AdventureServiceTestingModule,
        WorldServiceTestingModule,
        StandardTaskServiceTestingModule,
        TaskServiceTestingModule
      ],
      providers: [
        LoadingService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterStandardTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
