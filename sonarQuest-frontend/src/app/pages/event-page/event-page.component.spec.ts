import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {EventPageComponent} from './event-page.component';
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
  MatIconModule,
  MatInputModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentSearchModule} from "@covalent/core";
import {WorldServiceTestingModule} from "../../services/world.service.mock.module";
import {EventServiceTestingModule} from "../../services/event.service.mock.module";
import {WebsocketServiceTestingModule} from "../../services/websocket.service.mock.module";

describe('EventPageComponent', () => {
  let component: EventPageComponent;
  let fixture: ComponentFixture<EventPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventPageComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        CovalentSearchModule,
        MatDividerModule,
        MatCardModule,
        MatInputModule,
        MatDialogModule,
        MatFormFieldModule,
        WorldServiceTestingModule,
        EventServiceTestingModule,
        WebsocketServiceTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
