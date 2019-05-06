import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AdminWorldComponent} from './admin-world.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../services/translate.service.mock.module";
import {
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule,
  MatListModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {WizardServiceTestingModule} from "../../../../services/wizard.service.mock.module";
import {UserServiceTestingModule} from "../../../../services/user.service.mock.module";
import {QuestServiceTestingModule} from "../../../../services/quest.service.mock.module";
import {AdventureServiceTestingModule} from "../../../../services/adventure.service.mock.module";
import {TaskServiceTestingModule} from "../../../../services/task.service.mock.module";
import {LoadingService} from "../../../../services/loading.service";
import {WorldServiceTestingModule} from "../../../../services/world.service.mock.module";

describe('AdminWorldComponent', () => {
  let component: AdminWorldComponent;
  let fixture: ComponentFixture<AdminWorldComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminWorldComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatCardModule,
        MatDialogModule,
        MatListModule,
        MatDividerModule,
        MatFormFieldModule,
        CovalentDataTableModule,
        CovalentSearchModule,
        CovalentPagingModule,
        WizardServiceTestingModule,
        UserServiceTestingModule,
        QuestServiceTestingModule,
        AdventureServiceTestingModule,
        TaskServiceTestingModule,
        WorldServiceTestingModule
      ],
      providers : [
        LoadingService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminWorldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
