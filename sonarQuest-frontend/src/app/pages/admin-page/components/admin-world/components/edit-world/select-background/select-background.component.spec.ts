import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SelectBackgroundComponent} from './select-background.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatDialogRef,
  MatGridListModule,
  MatIconModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {WorldServiceTestingModule} from "../../../../../../../services/world.service.mock.module";

describe('SelectBackgroundComponent', () => {
  let component: SelectBackgroundComponent;
  let fixture: ComponentFixture<SelectBackgroundComponent>;

  const data = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectBackgroundComponent ],
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
        MatGridListModule,
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
    fixture = TestBed.createComponent(SelectBackgroundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
