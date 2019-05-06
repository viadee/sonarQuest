// tslint:disable:max-line-length
import {UiDesignService} from './services/ui-design.service';
import {SkillService} from './services/skill.service';
import {AdminPageComponent} from './pages/admin-page/admin-page.component';
import {ViewParticipatedQuestComponent} from './pages/quest-page/components/participated-quests/components/view-participated-quest/view-participated-quest.component';
import {GamemasterQuestEditComponent} from './pages/gamemaster-page/components/gamemaster-quest/components/gamemaster-quest-edit/gamemaster-quest-edit.component';
import {QuestPageComponent} from './pages/quest-page/quest-page.component';
import {MarketplacePageComponent} from './pages/marketplace-page/marketplace-page.component';

import {StartPageComponent} from './pages/start-page/start-page.component';
import {
  CovalentDataTableModule,
  CovalentLayoutModule,
  CovalentPagingModule,
  CovalentSearchModule,
  TdMediaService,
} from '@covalent/core';
import {AppRoutingModule} from './app-routing/app-routing.module';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatDialogModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSidenavModule,
  MatSnackBarModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule
} from '@angular/material';
import {MyAvatarPageComponent} from './pages/my-avatar-page/my-avatar-page.component';
import {AdventurePageComponent} from './pages/adventure-page/adventure-page.component';
import {WorldService} from './services/world.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {GamemasterPageComponent} from './pages/gamemaster-page/gamemaster-page.component';
import {AdminWorldComponent} from './pages/admin-page/components/admin-world/admin-world.component';
import {GamemasterAdventureComponent} from './pages/gamemaster-page/components/gamemaster-adventure/gamemaster-adventure.component';
import {GamemasterQuestComponent} from './pages/gamemaster-page/components/gamemaster-quest/gamemaster-quest.component';
import {GamemasterTaskComponent} from './pages/gamemaster-page/components/gamemaster-task/gamemaster-task.component';
import {EditWorldComponent} from './pages/admin-page/components/admin-world/components/edit-world/edit-world.component';
import {AdventureService} from './services/adventure.service';
import {QuestService} from './services/quest.service';
import {TaskService} from './services/task.service';
import {ArtefactService} from './services/artefact.service';
import {GamemasterStandardTaskComponent} from './pages/gamemaster-page/components/gamemaster-task/components/gamemaster-standard-task/gamemaster-standard-task.component';
import {GamemasterSpecialTaskComponent} from './pages/gamemaster-page/components/gamemaster-task/components/gamemaster-special-task/gamemaster-special-task.component';
import {StandardTaskService} from './services/standard-task.service';
import {SpecialTaskService} from './services/special-task.service';
import {GamemasterQuestCreateComponent} from './pages/gamemaster-page/components/gamemaster-quest/components/gamemaster-quest-create/gamemaster-quest-create.component';
import {GamemasterAddFreeTaskComponent} from './pages/gamemaster-page/components/gamemaster-quest/components/gamemaster-quest-create/components/gamemaster-add-free-task/gamemaster-add-free-task.component';
import {GamemasterSuggestTasksComponent} from './pages/gamemaster-page/components/gamemaster-quest/components/gamemaster-quest-create/components/gamemaster-suggest-tasks/gamemaster-suggest-tasks.component';
import {GamemasterSpecialTaskCreateComponent} from './pages/gamemaster-page/components/gamemaster-task/components/gamemaster-special-task/components/gamemaster-special-task-create/gamemaster-special-task-create.component';
import {GamemasterSpecialTaskEditComponent} from './pages/gamemaster-page/components/gamemaster-task/components/gamemaster-special-task/components/gamemaster-special-task-edit/gamemaster-special-task-edit.component';
import {GamemasterStandardTaskEditComponent} from './pages/gamemaster-page/components/gamemaster-task/components/gamemaster-standard-task/components/gamemaster-standard-task-edit/gamemaster-standard-task-edit.component';
import {GamemasterAdventureCreateComponent} from './pages/gamemaster-page/components/gamemaster-adventure/components/gamemaster-adventure-create/gamemaster-adventure-create.component';
import {GamemasterAdventureEditComponent} from './pages/gamemaster-page/components/gamemaster-adventure/components/gamemaster-adventure-edit/gamemaster-adventure-edit.component';
import {GamemasterAddFreeQuestComponent} from './pages/gamemaster-page/components/gamemaster-adventure/components/gamemaster-adventure-create/components/gamemaster-add-free-quest/gamemaster-add-free-quest.component';
import {ParticipatedQuestsComponent} from './pages/quest-page/components/participated-quests/participated-quests.component';
import {AvailableQuestsComponent} from './pages/quest-page/components/available-quests/available-quests.component';
import {ViewAvailableQuestComponent} from './pages/quest-page/components/available-quests/components/view-available-quest/view-available-quest.component';
import {ParticipationService} from './services/participation.service';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {AdminDeveloperComponent} from './pages/admin-page/components/admin-developer/admin-developer.component';
import {AdminDeveloperCreateComponent} from './pages/admin-page/components/admin-developer/components/admin-developer-create/admin-developer-create.component';
import {AdminDeveloperEditComponent} from './pages/admin-page/components/admin-developer/components/admin-developer-edit/admin-developer-edit.component';
import {AdminDeveloperDeleteComponent} from './pages/admin-page/components/admin-developer/components/admin-developer-delete/admin-developer-delete.component';
import {GamemasterMarketplaceComponent} from './pages/gamemaster-page/components/gamemaster-marketplace/gamemaster-marketplace.component';
import {GamemasterArtefactCreateComponent} from './pages/gamemaster-page/components/gamemaster-marketplace/components/gamemaster-artefact-create/gamemaster-artefact-create.component';
import {GamemasterArtefactEditComponent} from './pages/gamemaster-page/components/gamemaster-marketplace/components/gamemaster-artefact-edit/gamemaster-artefact-edit.component';
import {GamemasterSkillCreateComponent} from './pages/gamemaster-page/components/gamemaster-marketplace/components/gamemaster-artefact-create/components/gamemaster-skill-create/gamemaster-skill-create.component';
import {AvatarEditComponent} from './pages/my-avatar-page/components/my-avatar-edit/my-avatar-edit.component';
import {GamemasterIconSelectComponent} from './pages/gamemaster-page/components/gamemaster-marketplace/components/gamemaster-artefact-create/components/gamemaster-icon-select/gamemaster-icon-select.component';
import {AdminSonarQubeComponent} from './pages/admin-page/components/admin-sonar-qube/admin-sonar-qube.component';
import {SonarQubeService} from './services/sonar-qube.service';
import {SelectBackgroundComponent} from './pages/admin-page/components/admin-world/components/edit-world/select-background/select-background.component';
import {AuthenticationService} from './authentication/authentication.service';
import {LocalStorageService} from './authentication/local-storage.service';
import {AuthenticationGuard} from './authentication/authentication.guard';
import {UserService} from './services/user.service';
import {AuthenticationInterceptor} from './authentication/authentication.interceptor';
import {ImageService} from './services/image.service';
import {EmptyPageComponent} from './pages/empty-page/empty-page.component';
import {AvatarClassService} from './services/avatar-class.service';
import {AvatarRaceService} from './services/avatar-race.service';
import {RoleService} from './services/role.service';
import {LoadingComponent} from './components/loading/loading.component';
import {LoadingService} from './services/loading.service';
import {UserToWorldService} from './services/user-to-world.service';
import {PermissionService} from './services/permission.service';
import {WizardService} from './services/wizard.service';
import {ArtefactViewDetailsComponent} from './pages/marketplace-page/components/artefact-view-details/artefact-view-details.component';
import {EventService} from './services/event.service'
import {EventPageComponent} from './pages/event-page/event-page.component';
import {WebsocketService} from './services/websocket.service';
import {LoginPageComponent} from './pages/login-page/login-page.component';
import {MainLayoutComponent} from './layouts/main-layout/main-layout.component'
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    StartPageComponent,
    AdventurePageComponent,
    QuestPageComponent,
    MarketplacePageComponent,
    AdminPageComponent,
    AdminDeveloperComponent,
    MyAvatarPageComponent,
    GamemasterPageComponent,
    AdminWorldComponent,
    GamemasterAdventureComponent,
    GamemasterQuestComponent,
    GamemasterTaskComponent,
    EditWorldComponent,
    GamemasterStandardTaskComponent,
    GamemasterSpecialTaskComponent,
    GamemasterQuestCreateComponent,
    GamemasterAddFreeTaskComponent,
    GamemasterSuggestTasksComponent,
    GamemasterSpecialTaskCreateComponent,
    GamemasterSpecialTaskEditComponent,
    GamemasterStandardTaskEditComponent,
    GamemasterAdventureCreateComponent,
    GamemasterAdventureEditComponent,
    GamemasterAddFreeQuestComponent,
    GamemasterQuestEditComponent,
    ParticipatedQuestsComponent,
    AvailableQuestsComponent,
    ViewAvailableQuestComponent,
    ViewParticipatedQuestComponent,
    AdminDeveloperComponent,
    AdminDeveloperCreateComponent,
    AdminDeveloperEditComponent,
    AdminDeveloperDeleteComponent,
    GamemasterMarketplaceComponent,
    GamemasterArtefactCreateComponent,
    GamemasterArtefactEditComponent,
    GamemasterSkillCreateComponent,
    AvatarEditComponent,
    ArtefactViewDetailsComponent,
    GamemasterIconSelectComponent,
    AdminSonarQubeComponent,
    SelectBackgroundComponent,
    EmptyPageComponent,
    LoadingComponent,
    EventPageComponent,
    LoginPageComponent,
    MainLayoutComponent,
  ],
  entryComponents: [
    EditWorldComponent,
    GamemasterQuestCreateComponent,
    GamemasterAddFreeTaskComponent,
    GamemasterSuggestTasksComponent,
    GamemasterSpecialTaskCreateComponent,
    GamemasterSpecialTaskEditComponent,
    GamemasterStandardTaskEditComponent,
    GamemasterAdventureCreateComponent,
    GamemasterAdventureEditComponent,
    GamemasterAddFreeQuestComponent,
    GamemasterQuestEditComponent,
    ViewAvailableQuestComponent,
    ViewParticipatedQuestComponent,
    AdminDeveloperCreateComponent,
    AdminDeveloperEditComponent,
    AdminDeveloperDeleteComponent,
    GamemasterArtefactCreateComponent,
    GamemasterArtefactEditComponent,
    GamemasterSkillCreateComponent,
    AvatarEditComponent,
    ArtefactViewDetailsComponent,
    GamemasterIconSelectComponent,
    SelectBackgroundComponent,
    EmptyPageComponent,
    LoadingComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    CovalentLayoutModule,
    MatListModule,
    MatIconModule,
    AppRoutingModule,
    MatToolbarModule,
    MatSidenavModule,
    MatCardModule,
    MatProgressBarModule,
    MatGridListModule,
    MatTooltipModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    MatTabsModule,
    CovalentDataTableModule,
    CovalentSearchModule,
    CovalentPagingModule,
    MatButtonModule,
    MatDialogModule,
    MatInputModule,
    MatCheckboxModule,
    MatSnackBarModule,
    SweetAlert2Module.forRoot()
  ],
  providers: [TdMediaService,
    WorldService,
    AdventureService,
    QuestService,
    TaskService,
    StandardTaskService,
    SpecialTaskService,
    ParticipationService,
    ArtefactService,
    SkillService,
    SonarQubeService,
    UiDesignService,
    AuthenticationService,
    LocalStorageService,
    AuthenticationGuard,
    UserService,
    ImageService,
    AvatarClassService,
    AvatarRaceService,
    RoleService,
    LoadingService,
    UserToWorldService,
    PermissionService,
    WizardService,
    EventService,
    WebsocketService,
    {provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
