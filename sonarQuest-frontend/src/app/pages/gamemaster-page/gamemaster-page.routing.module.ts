import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GamemasterPageComponent} from './gamemaster-page.component';


const routes: Routes = [
  {
    path: '',
    component: GamemasterPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GamemasterPageRoutingModule {
}
