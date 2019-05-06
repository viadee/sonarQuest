import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MyAvatarPageComponent} from './my-avatar-page.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../services/translate.service.mock.module";
import {
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatProgressBarModule,
  MatToolbarModule,
  MatTooltipModule
} from "@angular/material";
import {UserServiceTestingModule} from "../../services/user.service.mock.module";
import {ImageService} from "../../services/image.service";

describe('MyAvatarPageComponent', () => {
  let component: MyAvatarPageComponent;
  let fixture: ComponentFixture<MyAvatarPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyAvatarPageComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatDividerModule,
        MatToolbarModule,
        MatProgressBarModule,
        MatDialogModule,
        MatGridListModule,
        UserServiceTestingModule,
      ],
      providers: [
        ImageService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyAvatarPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
