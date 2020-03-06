import { QualitygatePageComponent } from './../pages/qualitygate-page/qualitygate-page.component';
import { RaidsPageComponent } from './../pages/raids-page/raids-page.component';
import {EventPageComponent} from '../pages/event-page/event-page.component';
import {AdminPageComponent} from '../pages/admin-page/admin-page.component';
import {MarketplacePageComponent} from '../pages/marketplace-page/marketplace-page.component';
import {QuestPageComponent} from '../pages/quest-page/quest-page.component';
import {AdventurePageComponent} from '../pages/adventure-page/adventure-page.component';
import {StartPageComponent} from '../pages/start-page/start-page.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MyAvatarPageComponent} from '../pages/my-avatar-page/my-avatar-page.component';
import {GamemasterPageComponent} from '../pages/gamemaster-page/gamemaster-page.component';
import {AuthenticationGuard} from '../authentication/authentication.guard';
import {EmptyPageComponent} from '../pages/empty-page/empty-page.component';
import {RoutingUrls} from './routing-urls';
import {LoginPageComponent} from "../pages/login-page/login-page.component";
import {MainLayoutComponent} from "../layouts/main-layout/main-layout.component";
import { RaidPageComponent } from 'app/pages/raid-page/raid-page.component';


const appRoutes: Routes = [
  {path: '', component: MainLayoutComponent, canActivateChild: [AuthenticationGuard], children: [
      {path: '', redirectTo: RoutingUrls.myAvatar, pathMatch: 'full'},
      {path: RoutingUrls.start, component: StartPageComponent},
      {path: RoutingUrls.myAvatar, component: MyAvatarPageComponent},
      {path: RoutingUrls.adventures, component: AdventurePageComponent},
      {path: RoutingUrls.quests, component: QuestPageComponent},
      {path: RoutingUrls.marketplace, component: MarketplacePageComponent},
      {path: RoutingUrls.gamemaster, component: GamemasterPageComponent},
      {path: RoutingUrls.admin, component: AdminPageComponent},
      {path: RoutingUrls.events, component: EventPageComponent},
      {path: RoutingUrls.raids, component: RaidsPageComponent},
      {path: RoutingUrls.raid, component: RaidPageComponent},
      {path: RoutingUrls.qualitygate, component: QualitygatePageComponent}
    ]
  },
  {path: RoutingUrls.login, component: LoginPageComponent},
  {path: RoutingUrls.empty, component: EmptyPageComponent}
];

@NgModule({ 
  imports: [
    RouterModule.forRoot(appRoutes, { onSameUrlNavigation: 'reload' })
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
