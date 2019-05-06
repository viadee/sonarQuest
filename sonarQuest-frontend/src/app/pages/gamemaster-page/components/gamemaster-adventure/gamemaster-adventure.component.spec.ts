import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterAdventureComponent} from './gamemaster-adventure.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../services/translate.service.mock.module";
import {
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatIconModule,
  MatSelectModule,
  MatTooltipModule
} from "@angular/material";
import {QuestServiceTestingModule} from "../../../../services/quest.service.mock.module";
import {AdventureServiceTestingModule} from "../../../../services/adventure.service.mock.module";
import {WorldServiceTestingModule} from "../../../../services/world.service.mock.module";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {SweetAlert2Module} from "@sweetalert2/ngx-sweetalert2";
import {TaskServiceTestingModule} from "../../../../services/task.service.mock.module";

describe('GamemasterAdventureComponent', () => {
  let component: GamemasterAdventureComponent;
  let fixture: ComponentFixture<GamemasterAdventureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterAdventureComponent ],
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
        MatSelectModule,
        MatDividerModule,
        MatCardModule,
        CovalentPagingModule,
        CovalentDataTableModule,
        CovalentSearchModule,
        QuestServiceTestingModule,
        AdventureServiceTestingModule,
        WorldServiceTestingModule,
        SweetAlert2Module,
        TaskServiceTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterAdventureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
