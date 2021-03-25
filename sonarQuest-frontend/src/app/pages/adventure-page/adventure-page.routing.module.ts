import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdventurePageComponent} from './adventure-page.component';


const routes: Routes = [
  {
    path: '',
    component: AdventurePageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdventurePageRoutingModule {
}
