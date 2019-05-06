import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterAddFreeTaskComponent } from './gamemaster-add-free-task.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatCardModule,
  MatDialogModule, MatDialogRef,
  MatDividerModule,
  MatIconModule, MatProgressSpinnerModule, MatTabsModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {AdventureServiceTestingModule} from "../../../../../../../../services/adventure.service.mock.module";
import {WorldServiceTestingModule} from "../../../../../../../../services/world.service.mock.module";
import {SonarCubeServiceTestingModule} from "../../../../../../../../services/sonar-cube.service.mock.module";
import {StandardTaskServiceTestingModule} from "../../../../../../../../services/standard-task.service.mock.module";
import {SpecialTaskServiceTestingModule} from "../../../../../../../../services/special-task.service.mock.module";

describe('GamemasterAddFreeTaskComponent', () => {
  let component: GamemasterAddFreeTaskComponent;
  let fixture: ComponentFixture<GamemasterAddFreeTaskComponent>;

  const data = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterAddFreeTaskComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatDialogModule,
        MatIconModule,
        MatToolbarModule,
        CovalentSearchModule,
        MatDividerModule,
        MatCardModule,
        MatTabsModule,
        MatProgressSpinnerModule,
        CovalentDataTableModule,
        CovalentPagingModule,
        AdventureServiceTestingModule,
        WorldServiceTestingModule,
        SonarCubeServiceTestingModule,
        StandardTaskServiceTestingModule,
        SpecialTaskServiceTestingModule
      ],
      providers: [
        {provide: MAT_DIALOG_DATA, useValue: data},
        {provide: MatDialogRef, useValue: {}}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterAddFreeTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
