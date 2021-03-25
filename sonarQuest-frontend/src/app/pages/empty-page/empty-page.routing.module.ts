import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmptyPageComponent} from './empty-page.component';


const routes: Routes = [
  {
    path: '',
    component: EmptyPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmptyPageRoutingModule {
}
