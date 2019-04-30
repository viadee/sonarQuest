import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterStandardTaskEditComponent} from './gamemaster-standard-task-edit.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {StandardTaskServiceTestingModule} from "../../../../../../../../services/standard-task.service.mock.module";

describe('GamemasterStandardTaskEditComponent', () => {
  let component: GamemasterStandardTaskEditComponent;
  let fixture: ComponentFixture<GamemasterStandardTaskEditComponent>;

  const data = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterStandardTaskEditComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        MatDialogModule,
        MatDividerModule,
        MatToolbarModule,
        StandardTaskServiceTestingModule
      ],
      providers: [
        {provide: MAT_DIALOG_DATA, useValue: data},
        {provide: MatDialogRef, useValue: {}}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterStandardTaskEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
