import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterTaskComponent} from './gamemaster-task.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../services/translate.service.mock.module";
import {MatCardModule, MatDialogModule, MatDividerModule, MatIconModule, MatTooltipModule} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {TaskServiceTestingModule} from "../../../../services/task.service.mock.module";
import {QuestServiceTestingModule} from "../../../../services/quest.service.mock.module";
import {WorldServiceTestingModule} from "../../../../services/world.service.mock.module";
import {GamemasterSpecialTaskComponent} from "./components/gamemaster-special-task/gamemaster-special-task.component";
import {GamemasterStandardTaskComponent} from "./components/gamemaster-standard-task/gamemaster-standard-task.component";
import {SpecialTaskServiceTestingModule} from "../../../../services/special-task.service.mock.module";
import {StandardTaskServiceTestingModule} from "../../../../services/standard-task.service.mock.module";
import {AdventureServiceTestingModule} from "../../../../services/adventure.service.mock.module";
import {LoadingService} from "../../../../services/loading.service";

describe('GamemasterTaskComponent', () => {
  let component: GamemasterTaskComponent;
  let fixture: ComponentFixture<GamemasterTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        GamemasterTaskComponent,
        GamemasterSpecialTaskComponent,
        GamemasterStandardTaskComponent
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
        MatDividerModule,
        MatCardModule,
        MatDialogModule,
        CovalentPagingModule,
        CovalentSearchModule,
        CovalentDataTableModule,
        TaskServiceTestingModule,
        SpecialTaskServiceTestingModule,
        StandardTaskServiceTestingModule,
        QuestServiceTestingModule,
        AdventureServiceTestingModule,
        WorldServiceTestingModule
      ],
      providers: [
        LoadingService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
