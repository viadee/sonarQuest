import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {EditWorldComponent} from './edit-world.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../../../../../services/translate.service.mock.module";
import {
  MAT_DIALOG_DATA,
  MatCheckboxModule,
  MatDialogModule,
  MatDialogRef,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatSelectModule,
  MatTooltipModule
} from "@angular/material";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {WizardServiceTestingModule} from "../../../../../../services/wizard.service.mock.module";
import {UserServiceTestingModule} from "../../../../../../services/user.service.mock.module";
import {AvatarClassServiceTestingModule} from "../../../../../../services/avatar-class.service.mock.module";
import {AvatarRaceServiceTestingModule} from "../../../../../../services/avatar-race.service.mock.module";
import {RoleServiceTestingModule} from "../../../../../../services/role.service.mock.module";
import {UserToWorldServiceTestingModule} from "../../../../../../services/user-to-world.service.mock.module";
import {ImageService} from "../../../../../../services/image.service";
import {LoadingService} from "../../../../../../services/loading.service";
import {WorldServiceTestingModule} from "../../../../../../services/world.service.mock.module";

describe('EditWorldComponent', () => {
  let component: EditWorldComponent;
  let fixture: ComponentFixture<EditWorldComponent>;

  const data = {
    id: 1,
    name: "Example World",
    project: "My Project",
    active: true,
    usequestcards: true,
    image: "PATH/TO/IMAGE",
    hasQuests: true
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditWorldComponent ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatSelectModule,
        MatDialogModule,
        MatInputModule,
        MatListModule,
        MatCheckboxModule,
        MatDividerModule,
        MatFormFieldModule,
        CovalentDataTableModule,
        CovalentSearchModule,
        CovalentPagingModule,
        WizardServiceTestingModule,
        UserServiceTestingModule,
        AvatarClassServiceTestingModule,
        AvatarRaceServiceTestingModule,
        RoleServiceTestingModule,
        UserToWorldServiceTestingModule,
        WorldServiceTestingModule
      ],
      providers: [
        {provide: MAT_DIALOG_DATA, useValue: data},
        {provide: MatDialogRef, useValue: {}},
        ImageService,
        LoadingService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditWorldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
