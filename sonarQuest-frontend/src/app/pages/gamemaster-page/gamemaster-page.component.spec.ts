import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GamemasterPageComponent} from './gamemaster-page.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateTestingModule} from "../../services/translate.service.mock.module";
import {
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatIconModule,
  MatTabsModule,
  MatTooltipModule
} from "@angular/material";
import {GamemasterAdventureComponent} from "./components/gamemaster-adventure/gamemaster-adventure.component";
import {GamemasterQuestComponent} from "./components/gamemaster-quest/gamemaster-quest.component";
import {GamemasterTaskComponent} from "./components/gamemaster-task/gamemaster-task.component";
import {GamemasterMarketplaceComponent} from "./components/gamemaster-marketplace/gamemaster-marketplace.component";
import {CovalentDataTableModule, CovalentPagingModule, CovalentSearchModule} from "@covalent/core";
import {TaskServiceTestingModule} from "../../services/task.service.mock.module";
import {SpecialTaskServiceTestingModule} from "../../services/special-task.service.mock.module";
import {StandardTaskServiceTestingModule} from "../../services/standard-task.service.mock.module";
import {QuestServiceTestingModule} from "../../services/quest.service.mock.module";
import {AdventureServiceTestingModule} from "../../services/adventure.service.mock.module";
import {WorldServiceTestingModule} from "../../services/world.service.mock.module";
import {SweetAlert2Module} from "@sweetalert2/ngx-sweetalert2";
import {GamemasterSpecialTaskComponent} from "./components/gamemaster-task/components/gamemaster-special-task/gamemaster-special-task.component";
import {GamemasterStandardTaskComponent} from "./components/gamemaster-task/components/gamemaster-standard-task/gamemaster-standard-task.component";
import {PermissionServiceTestingModule} from "../../services/permission.service.mock.module";
import {SkillServiceTestingModule} from "../../services/skill.service.mock.module";
import {LoadingService} from "../../services/loading.service";
import {ArtefactServiceTestingModule} from "../../services/artefact.service.mock.module";
import {UserServiceTestingModule} from "../../services/user.service.mock.module";

describe('GamemasterPageComponent', () => {
  let component: GamemasterPageComponent;
  let fixture: ComponentFixture<GamemasterPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        GamemasterPageComponent,
        GamemasterAdventureComponent,
        GamemasterQuestComponent,
        GamemasterTaskComponent,
        GamemasterMarketplaceComponent,
        GamemasterSpecialTaskComponent,
        GamemasterStandardTaskComponent,
        GamemasterPageComponent
      ],
      imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateTestingModule,
        MatTooltipModule,
        MatIconModule,
        MatDividerModule,
        MatCardModule,
        MatDialogModule,
        MatTabsModule,
        CovalentPagingModule,
        CovalentSearchModule,
        CovalentDataTableModule,
        TaskServiceTestingModule,
        SpecialTaskServiceTestingModule,
        StandardTaskServiceTestingModule,
        QuestServiceTestingModule,
        AdventureServiceTestingModule,
        WorldServiceTestingModule,
        PermissionServiceTestingModule,
        ArtefactServiceTestingModule,
        UserServiceTestingModule,
        SkillServiceTestingModule,
        SweetAlert2Module
      ],
      providers: [
        LoadingService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
