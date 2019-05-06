import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterSpecialTaskComponent} from './gamemaster-special-task.component';
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
import {SpecialTaskServiceTestingModule} from "../../../../../../services/special-task.service.mock.module";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {QuestServiceTestingModule} from "../../../../../../services/quest.service.mock.module";
import {AdventureServiceTestingModule} from "../../../../../../services/adventure.service.mock.module";
import {WorldServiceTestingModule} from "../../../../../../services/world.service.mock.module";

describe('GamemasterSpecialTaskComponent', () => {
  let component: GamemasterSpecialTaskComponent;
  let fixture: ComponentFixture<GamemasterSpecialTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterSpecialTaskComponent ],
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
        SpecialTaskServiceTestingModule
      ],
      providers: [

      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterSpecialTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
