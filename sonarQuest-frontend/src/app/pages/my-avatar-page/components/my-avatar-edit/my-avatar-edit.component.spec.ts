import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AvatarEditComponent} from "./my-avatar-edit.component";
import {BrowserModule, DomSanitizer} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../services/translate.service.mock.module";
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
import {UserServiceTestingModule} from "../../../../services/user.service.mock.module";
import {ImageService} from "../../../../services/image.service";

describe('AvatarEditComponent', () => {
  let component: AvatarEditComponent;
  let fixture: ComponentFixture<AvatarEditComponent>;

  const data = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AvatarEditComponent ],
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
        MatDividerModule,
        MatToolbarModule,
        MatDialogModule,
        UserServiceTestingModule,
      ],
      providers: [
        {provide: MAT_DIALOG_DATA, useValue: data},
        {provide: MatDialogRef, useValue: {}},
        ImageService,
        DomSanitizer
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvatarEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
