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
import {LoginPageComponent} from '../pages/login-page/login-page.component';
import {MainLayoutComponent} from '../layouts/main-layout/main-layout.component';


const appRoutes: Routes = [
  {path: '', component: MainLayoutComponent, canActivateChild: [AuthenticationGuard], children: [
      {
        path: '',
        redirectTo: RoutingUrls.myAvatar,
        pathMatch: 'full',
        loadChildren: () => import('../pages/my-avatar-page/my-avatar-page.routing.module')
          .then(m => m.MyAvatarPageRoutingModule)
      },
      {
        path: RoutingUrls.myAvatar,
        loadChildren: () => import('../pages/my-avatar-page/my-avatar-page.routing.module')
          .then(m => m.MyAvatarPageRoutingModule)
      },
      {
        path: RoutingUrls.start,
        loadChildren: () => import('../pages/start-page/start-page.routing.module')
          .then(m => m.StartPageRoutingModule)
      },
      {
        path: RoutingUrls.adventures,
        loadChildren: () => import('../pages/adventure-page/adventure-page.routing.module')
          .then(m => m.AdventurePageRoutingModule)
      },
      {
        path: RoutingUrls.quests,
        loadChildren: () => import('../pages/quest-page/quest-page.routing.module')
          .then(m => m.QuestPageRoutingModule)
      },
      {
        path: RoutingUrls.marketplace,
        loadChildren: () => import('../pages/marketplace-page/marketplace-page.routing.module')
          .then(m => m.MarketplacePageRoutingModule)
      },
      {
        path: RoutingUrls.gamemaster,
        loadChildren: () => import('../pages/gamemaster-page/gamemaster-page.routing.module')
          .then(m => m.GamemasterPageRoutingModule)
      },
      {
        path: RoutingUrls.admin,
        loadChildren: () => import('../pages/admin-page/admin-page.routing.module')
          .then(m => m.AdminPageRoutingModule)
      },
      {
        path: RoutingUrls.events,
        loadChildren: () => import('../pages/event-page/event-page.routing.module')
          .then(m => m.EventPageRoutingModule)
      },
    ]
  },
  {
    path: RoutingUrls.login,
    loadChildren: () => import('../pages/login-page/login-page.routing.module')
      .then(m => m.LoginPageRoutingModule)
  },
  {
    path: RoutingUrls.empty,
    loadChildren: () => import('../pages/empty-page/empty-page.routing.module')
      .then(m => m.EmptyPageRoutingModule)
  }
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
