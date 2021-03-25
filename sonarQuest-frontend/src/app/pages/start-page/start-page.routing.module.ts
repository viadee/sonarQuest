import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {StartPageComponent} from './start-page.component';


const routes: Routes = [
  {
    path: '',
    component: StartPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StartPageRoutingModule {
}
