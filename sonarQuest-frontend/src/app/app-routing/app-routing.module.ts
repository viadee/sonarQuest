import { AdminPageComponent } from './../pages/admin-page/admin-page.component';
import { MarketplacePageComponent } from '../pages/marketplace-page/marketplace-page.component';
import { QuestPageComponent } from './../pages/quest-page/quest-page.component';
import { AdventurePageComponent} from '../pages/adventure-page/adventure-page.component';
import { StartPageComponent } from './../pages/start-page/start-page.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MyAvatarPageComponent} from '../pages/my-avatar-page/my-avatar-page.component';
import {GamemasterPageComponent} from "../pages/gamemaster-page/gamemaster-page.component";


const appRoutes: Routes = [
    /* { path: 'start', component: StartPageComponent }, */
    { path: 'myAvatar', component: MyAvatarPageComponent },
    { path: 'adventures', component: AdventurePageComponent },
    { path: 'quests', component: QuestPageComponent },
    { path: 'marketplace', component: MarketplacePageComponent },
    { path: 'gamemaster', component: GamemasterPageComponent },
    { path: 'admin', component: AdminPageComponent },
    { path: '', redirectTo: '/myAvatar', pathMatch: 'full' },
];
@NgModule({
    imports: [
        RouterModule.forRoot(appRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class AppRoutingModule { }
