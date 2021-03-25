import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MyAvatarPageComponent} from './my-avatar-page.component';


const routes: Routes = [
  {
    path: '',
    component: MyAvatarPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyAvatarPageRoutingModule {
}
